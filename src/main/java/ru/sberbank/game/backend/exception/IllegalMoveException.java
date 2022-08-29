package ru.sberbank.game.backend.exception;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String move){
        super("Move " + move + " is illegal!");
    }
}

