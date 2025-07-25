package com.codingapi.sdk.okx.websocket.protocol.command;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.sdk.okx.websocket.properties.OkxSocketConfigProperties;
import com.codingapi.sdk.okx.websocket.sign.OkxSigner;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

//https://www.okx.com/docs-v5/zh/#websocket-api-login
@Getter
@ToString
public class LoginCommand implements ICommand {
    private final String apiKey;
    private final String passphrase;
    private final String timestamp;
    private final String sign;


    public LoginCommand(OkxSocketConfigProperties okxConfig) {
        this.apiKey = okxConfig.getApikey();
        this.passphrase = okxConfig.getPassphrase();
        OkxSigner signer = new OkxSigner(okxConfig.getSecretKey());
        OkxSigner.Sign sign = signer.webSocketSign("GET", "/users/self/verify");
        this.timestamp = sign.getTimestamp();
        this.sign = sign.getSign();
    }


    public String toCmd() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("op", "login");
        List<LoginCommand> commands = new ArrayList<>();
        commands.add(this);
        jsonObject.put("args", commands);
        return jsonObject.toJSONString();
    }
}
