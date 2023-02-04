package com.example.applicationa.utils;

public enum IntentAction {
    MESSAGE_SEND("application.challenge.MESSAGE_SEND"),
    MESSAGE("application.challenge.MESSAGE"),
    MESSAGE_RESULT("application.challenge.MESSAGE_RESULT"),
    RESULT("application.challenge.RESULT");

    private final String action;

    IntentAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}