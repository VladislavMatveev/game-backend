package ru.sberbank.game.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.game.backend.controller.dto.DeskRequestDto;
import ru.sberbank.game.backend.controller.dto.ResponseDto;
import ru.sberbank.game.backend.service.GameService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DeskController {
    private final GameService gameService;

    @PostMapping("${application.endpoint.desk}")
    public ResponseEntity<ResponseDto> status(@Valid @RequestBody DeskRequestDto request) {

        ResponseDto responseDto = gameService.getStatus(request.getUid());

        return new ResponseEntity<>(
                responseDto,
                responseDto.getHttpStatus());

    }
}