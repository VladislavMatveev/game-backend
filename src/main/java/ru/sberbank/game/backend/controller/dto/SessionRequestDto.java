package ru.sberbank.game.backend.controller.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class SessionRequestDto {
    @Pattern(regexp = "^(true|false)$", message = "Введите true или false")
    private String firstMove;
}
