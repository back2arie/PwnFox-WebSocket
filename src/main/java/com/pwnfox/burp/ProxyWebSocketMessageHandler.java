package com.pwnfox.burp;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.proxy.websocket.*;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.proxy.ProxyWebSocketMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyWebSocketMessageHandler implements ProxyMessageHandler {
    private final MontoyaApi api;

    public ProxyWebSocketMessageHandler(MontoyaApi api) {
        this.api = api;
    }

    @Override
    public BinaryMessageToBeSentAction handleBinaryMessageToBeSent(InterceptedBinaryMessage message) {
        handleMessage(message);
        return BinaryMessageToBeSentAction.continueWith(message);
    }

    private void handleMessage(InterceptedTextMessage message) {
        List<ProxyWebSocketMessage> webSocketHistory = api.proxy().webSocketHistory(
            msg -> msg.annotations().highlightColor() == HighlightColor.NONE
        );

        for (ProxyWebSocketMessage msg : webSocketHistory) {
            String color = msg.upgradeRequest().headerValue("X-PwnFox-Color");
            if (color != null) {
                msg.annotations().setHighlightColor(getHighlightColor(color));
            }
        }
    }

    private void handleMessage(InterceptedBinaryMessage message) {
        List<ProxyWebSocketMessage> webSocketHistory = api.proxy().webSocketHistory(
            msg -> msg.annotations().highlightColor() == HighlightColor.NONE
        );

        for (ProxyWebSocketMessage msg : webSocketHistory) {
            String color = msg.upgradeRequest().headerValue("X-PwnFox-Color");
            if (color != null) {
                msg.annotations().setHighlightColor(getHighlightColor(color));
            }
        }
    }

    @Override
    public TextMessageReceivedAction handleTextMessageReceived(InterceptedTextMessage message) {
        handleMessage(message);
        return TextMessageReceivedAction.continueWith(message);
    }

    @Override
    public TextMessageToBeSentAction handleTextMessageToBeSent(InterceptedTextMessage message) {
        handleMessage(message);
        return TextMessageToBeSentAction.continueWith(message);
    }

    @Override
    public BinaryMessageReceivedAction handleBinaryMessageReceived(InterceptedBinaryMessage message) {
        handleMessage(message);
        return BinaryMessageReceivedAction.continueWith(message);
    }

    private HighlightColor getHighlightColor(String color) {
        Map<String, HighlightColor> colorMap = new HashMap<>();
        colorMap.put("BLUE", HighlightColor.BLUE);
        colorMap.put("CYAN", HighlightColor.CYAN);
        colorMap.put("GREEN", HighlightColor.GREEN);
        colorMap.put("YELLOW", HighlightColor.YELLOW);
        colorMap.put("ORANGE", HighlightColor.ORANGE);
        colorMap.put("RED", HighlightColor.RED);
        colorMap.put("PINK", HighlightColor.PINK);
        colorMap.put("MAGENTA", HighlightColor.MAGENTA);
        return colorMap.getOrDefault(color.toUpperCase(), HighlightColor.NONE);
    }
}
