package ru.sberbank.game.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import ru.sberbank.game.backend.utils.enums.GameStatus;

@Data
@Accessors(chain = true)
public class ResponseDto {
    private String uid;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private String winner = "";
    private String message = "";
    private String desk;
    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    public ResponseDto build() {
        return new ResponseDto()
                .setUid(this.uid)
                .setMessage(this.message)
                .setStatus(this.status)
                .setWinner(this.winner)
                .setDesk(this.desk)
                .setHttpStatus(this.httpStatus);
    }
}
