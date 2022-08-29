package ru.sberbank.game.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.game.backend.controller.dto.CancelRequestDto;
import ru.sberbank.game.backend.controller.dto.MoveRequestDto;
import ru.sberbank.game.backend.controller.dto.ResponseDto;
import ru.sberbank.game.backend.service.GameService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MoveController {

    private final GameService gameService;

    @PostMapping("${application.endpoint.move}")
    public ResponseEntity<ResponseDto> move(@Valid @RequestBody MoveRequestDto request) {

        ResponseDto responseDto = gameService.makeMove(
                request.getUid(),
                request.getMove());

       return new ResponseEntity<>(
               responseDto,
               responseDto.getHttpStatus()
       );

    }

    @PostMapping("${application.endpoint.cancel}")
    public ResponseEntity<ResponseDto> cancel(@Valid @RequestBody CancelRequestDto request) {
        ResponseDto responseDto = gameService.cancelMove(
                request.getUid()
        );

        return new ResponseEntity<>(
                responseDto,
                responseDto.getHttpStatus()
        );
    }

}