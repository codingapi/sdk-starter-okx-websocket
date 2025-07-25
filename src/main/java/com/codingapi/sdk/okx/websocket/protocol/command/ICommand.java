package com.codingapi.sdk.okx.websocket.protocol.command;

import com.codingapi.springboot.framework.serializable.JsonSerializable;

public interface ICommand extends JsonSerializable {

    String toCmd();

}
