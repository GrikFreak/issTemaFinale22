package it.unibo.Sprint3web;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Iterator;

public interface IWsHandler {
   void sendToAll(String message);
}
