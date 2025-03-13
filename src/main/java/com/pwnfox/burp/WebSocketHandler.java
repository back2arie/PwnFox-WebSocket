package com.pwnfox.burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.proxy.websocket.ProxyWebSocketCreation;
import burp.api.montoya.proxy.websocket.ProxyWebSocketCreationHandler;

public class WebSocketHandler implements ProxyWebSocketCreationHandler {
    private final MontoyaApi api;

    public WebSocketHandler(MontoyaApi api) {
        this.api = api;
    }

    @Override
    public void handleWebSocketCreation(ProxyWebSocketCreation creation) {
        // Register the proxy message handler for the WebSocket
        creation.proxyWebSocket().registerProxyMessageHandler(new ProxyWebSocketMessageHandler(api));
    }
}
