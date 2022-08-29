package ru.sberbank.game.backend.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(String uid){
        super("Session " + uid + " does not exist.");
    }
}

