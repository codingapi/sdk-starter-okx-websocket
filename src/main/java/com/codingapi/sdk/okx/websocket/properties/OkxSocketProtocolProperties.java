package com.codingapi.sdk.okx.websocket.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;

@Setter
@Getter
public class OkxSocketProtocolProperties implements InitializingBean {

    /**
     * 最大读取等待时间
     */
    private int readerIdleTime = 30;

    /**
     * 最大写入等待时间
     */
    private int writerIdleTime = 20;

    /**
     * 打印日志
     */
    private boolean showMessage;


    @Override
    public void afterPropertiesSet() throws Exception {
        PropertyContext.getInstance().setShowMessage(showMessage);
    }
}
