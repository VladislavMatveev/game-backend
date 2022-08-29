package ru.sberbank.game.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.game.backend.controller.dto.ResponseDto;
import ru.sberbank.game.backend.controller.dto.SessionRequestDto;
import ru.sberbank.game.backend.service.GameService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final GameService gameService;

    @PostMapping("${application.endpoint.new}")
    public ResponseEntity<ResponseDto> newGame(@Valid @RequestBody SessionRequestDto sessionRequestDto) {

        ResponseDto responseDto = gameService.newGame(
                sessionRequestDto.getFirstMove()
        );

        return new ResponseEntity<>(
                responseDto,
                responseDto.getHttpStatus());
    }

}
