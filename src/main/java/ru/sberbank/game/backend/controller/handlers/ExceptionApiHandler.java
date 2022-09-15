package ru.sberbank.game.backend.controller.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sberbank.game.backend.controller.dto.ErrorMessage;
import ru.sberbank.game.backend.exception.GameEndException;
import ru.sberbank.game.backend.exception.IllegalMoveException;
import ru.sberbank.game.backend.exception.MoveNotFoundException;
import ru.sberbank.game.backend.exception.SessionNotFoundException;

@ControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> notValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorMessage().setField(exception.getFieldError().getField())
                        .setMessage(exception.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> notReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage().setMessage(exception.getMessage()));
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorMessage> sessionNotFoundException(SessionNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorMessage()
                        .setMessage(exception.getMessage()),
                HttpStatus.NOT_FOUND
                );
    }

    @ExceptionHandler(MoveNotFoundException.class)
    public ResponseEntity<ErrorMessage> moveNotFoundException(MoveNotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorMessage()
                        .setMessage(exception.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<ErrorMessage> illegalMoveException(IllegalMoveException exception) {
        return new ResponseEntity<>(
                new ErrorMessage()
                        .setMessage(exception.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<ErrorMessage> gameEndException(GameEndException exception) {
        return new ResponseEntity<>(
                new ErrorMessage()
                        .setMessage(exception.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }
}
