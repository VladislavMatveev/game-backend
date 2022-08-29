package ru.sberbank.game.backend.controller.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DeskRequestDto {
    @Pattern(regexp = "[a-z|0-9]{8}-[a-z|0-9]{4}-[a-z|0-9]{4}-[a-z|0-9]{4}-[a-z|0-9]{12}", message = "Некорректный uid")
    private String uid;
}

