package ru.sberbank.game.backend.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class MoveRequestDto {

    @Pattern(regexp = "[a-z|0-9]{8}-[a-z|0-9]{4}-[a-z|0-9]{4}-[a-z|0-9]{4}-[a-z|0-9]{12}", message = "Wrong uid format")
    private String uid;

    @Min(value = 0, message = "Value must be 0-9")
    @Max(value = 9, message = "Value must be 0-9")
    private int move;
}
