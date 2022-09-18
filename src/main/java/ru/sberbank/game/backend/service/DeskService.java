package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.controller.dto.DeskDto;
import ru.sberbank.game.backend.persistence.DeskRepository;
import ru.sberbank.game.backend.persistence.entity.Desk;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class DeskService {

    private DeskRepository deskRepository;

    public Desk getDesk(long sessionId) {
        return deskRepository.findBySessionId(sessionId)
                .orElse(Desk.builder()
                        .sessionId(sessionId)
                        .build());
    }

    public DeskDto getDeskDto(long sessionId) {
        Desk desk = getDesk(sessionId);
        return DeskDto.builder()
                .deskEntity(desk)
                .build();
    }

    public String drawField(DeskDto desk) {
        return Arrays.deepToString(desk.getDeskArr());
    }

    public String getFieldValue(String[][] desk, int fieldNum) {
        int row = (fieldNum - 1) / 3;
        int col = (fieldNum - 1) % 3;

        return desk[row][col];
    }
    public void setFieldValue(int fieldNum, String sign, String[][] desk) {
        int row = (fieldNum - 1) / 3;
        int col = (fieldNum - 1) % 3;

        desk[row][col] = sign;
    }

    public void save(DeskDto deskDto) {
        Desk desk = getDesk(deskDto.getSessionId());
        deskDto.fillDeskEntity(desk);
        deskRepository.save(desk);
    }
}
