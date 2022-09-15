package ru.sberbank.game.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.sberbank.game.backend.utils.enums.GameStatus;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto {
    private String uid;
    @Builder.Default
    private GameStatus status = GameStatus.IN_PROGRESS;
    private String winner;
    private String message;
    private String desk;
    @JsonIgnore
    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.OK;

}
