package com.codingapi.sdk.okx.websocket.properties;

import lombok.Getter;

public class PropertyContext {

    public static PropertyContext getInstance() {
        return instance;
    }

    private final static PropertyContext instance = new PropertyContext();

    private PropertyContext(){

    }

    @Getter
    private boolean showMessage;

    void setShowMessage(boolean showMessage){
        this.showMessage = showMessage;
    }
}
