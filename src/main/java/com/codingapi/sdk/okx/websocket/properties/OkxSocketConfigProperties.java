package com.codingapi.sdk.okx.websocket.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OkxSocketConfigProperties {

    /**
     * private websocket url
     */
    private String socketPrivateUrl;

    /**
     * public websocket url
     */
    private String socketPublicUrl;
    /**
     * appkey
     */
    private String apikey;
    /**
     * secretkey
     */
    private String secretKey;

    /**
     * appkey 密码
     */
    private String passphrase;

    /**
     * 是否模拟账户
     * true 模拟环境
     * false 正式环境
     */
    private boolean mock;

}
