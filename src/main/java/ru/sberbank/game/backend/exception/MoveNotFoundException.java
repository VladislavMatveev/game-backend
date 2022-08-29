package ru.sberbank.game.backend.exception;

public class MoveNotFoundException extends RuntimeException {
    public MoveNotFoundException(){
        super("No moves to cancel");
    }
}

