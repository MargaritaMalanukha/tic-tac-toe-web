package com.socket.app.networkClient;

public class Protocol {
    // Server-to-client signals
    public static final String SIGNAL_WAIT = "WaitYourTurn";
    public static final String SIGNAL_MOVE = "MakeMove";
    public static final String SIGNAL_END = "EndGame";

    // Client-to-server signals
    public static final String SIGNAL_CLIENT_MOVE = "CLIENT_MOVE";
}
