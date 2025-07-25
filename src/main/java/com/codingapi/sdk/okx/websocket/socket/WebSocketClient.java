package com.codingapi.sdk.okx.websocket.socket;

import com.codingapi.sdk.okx.websocket.properties.OkxSocketProtocolProperties;
import com.codingapi.sdk.okx.websocket.socket.handler.MyWebSocketClientHandler;
import com.codingapi.sdk.okx.websocket.socket.handler.TextWebSocketFrameDecoder;
import com.codingapi.sdk.okx.websocket.socket.handler.TextWebSocketFrameEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketClient {

    private final OkxSocketProtocolProperties socketProtocolProperties;

    private EventLoopGroup group;
    private boolean shutdownSignal;
    private final URI uri;
    private final boolean isPrivateChannel;

    public WebSocketClient(String url,boolean isPrivateChannel,
                           OkxSocketProtocolProperties socketProtocolProperties) throws URISyntaxException {
        this.uri = new URI(url);
        this.isPrivateChannel = isPrivateChannel;
        this.socketProtocolProperties = socketProtocolProperties;
    }

    public void connect() {
        try {
            shutdownSignal = false;
            group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (uri.getScheme().startsWith("wss")) {
                                SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                                pipeline.addLast(sslCtx.newHandler(ch.alloc(), uri.getHost(), uri.getPort()));
                            }

                            HttpHeaders httpHeaders = new DefaultHttpHeaders();
                            pipeline.addLast(
                                    new IdleStateHandler(socketProtocolProperties.getReaderIdleTime(),
                                            socketProtocolProperties.getWriterIdleTime(), 0, TimeUnit.SECONDS),
                                    new HttpClientCodec(),
                                    new HttpObjectAggregator(Integer.MAX_VALUE),
                                    new WebSocketClientProtocolHandler(uri, WebSocketVersion.V13,
                                            null, true, httpHeaders, Integer.MAX_VALUE),
                                    new TextWebSocketFrameDecoder(),
                                    new TextWebSocketFrameEncoder(),
                                    new MyWebSocketClientHandler(isPrivateChannel));
                        }
                    });
            ChannelFuture f = b.connect(uri.getHost(), uri.getPort()).sync();
            f.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                // system shutdown server
                log.info("listener close future");
                if (shutdownSignal) {
                    return;
                }
                log.info("retry connect socket server...");
                connect();

            }).sync();
        } catch (Exception e) {
            log.error("connect socket server error =>", e);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            log.info("retry connect socket server...");
            restart();
        }
    }

    public void close() {
        shutdownSignal = true;
        group.shutdownGracefully();
        log.info("socket server shutdown ");
    }

    private void restart(){
        log.info("socket server restart ");
        close();
        connect();
    }

}
