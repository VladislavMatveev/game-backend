package ru.sberbank.game.backend.controller.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorMessage {
    private String field;
    private String message;
}
