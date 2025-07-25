package com.codingapi.sdk.okx.websocket.sign;

import com.codingapi.springboot.framework.crypto.HmacSHA256;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Getter
public class OkxSigner {

    private final String secretKey;

    public OkxSigner(String secretKey) {
        this.secretKey = secretKey;
    }

    @SneakyThrows
    public Sign webSocketSign(String method, String url) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String content = timestamp+method+url;
        String signVal =  Base64.getEncoder().encodeToString(HmacSHA256.sha256(content.getBytes(StandardCharsets.UTF_8),
                secretKey.getBytes(StandardCharsets.UTF_8)));
        return new Sign(timestamp, signVal);
    }

    @Setter
    @Getter
    public static class Sign {
        private String timestamp;
        private String sign;

        public Sign(String timestamp, String sign) {
            this.timestamp = timestamp;
            this.sign = sign;
        }
    }


}
