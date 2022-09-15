package ru.sberbank.game.backend.exception;

public class GameEndException extends RuntimeException {
    public GameEndException(String message){
        super(message);
    }
}

