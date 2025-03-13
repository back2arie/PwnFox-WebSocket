package com.pwnfox.burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class BurpExtender implements BurpExtension {
    private MontoyaApi api;

    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        api.extension().setName("PwnFox WebSocket");
        
        // Register WebSocket Handlers
        api.proxy().registerWebSocketCreationHandler(new WebSocketHandler(api));
    }
}