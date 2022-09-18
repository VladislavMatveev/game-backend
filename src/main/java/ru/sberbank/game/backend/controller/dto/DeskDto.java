package ru.sberbank.game.backend.controller.dto;

import lombok.Builder;
import lombok.Getter;
import ru.sberbank.game.backend.persistence.entity.Desk;

@Getter
public class DeskDto {

    private final long sessionId;
    private final String[][] deskArr;

    @Builder
    private DeskDto(Desk deskEntity) {

        this.sessionId = deskEntity.getSessionId();

        String[][] deskArr = new String[3][3];
        deskArr[0][0] = deskEntity.getF1();
        deskArr[0][1] = deskEntity.getF2();
        deskArr[0][2] = deskEntity.getF3();
        deskArr[1][0] = deskEntity.getF4();
        deskArr[1][1] = deskEntity.getF5();
        deskArr[1][2] = deskEntity.getF6();
        deskArr[2][0] = deskEntity.getF7();
        deskArr[2][1] = deskEntity.getF8();
        deskArr[2][2] = deskEntity.getF9();

        this.deskArr = deskArr;
    }

    public void fillDeskEntity(Desk deskEntity) {
        deskEntity.setF1(deskArr[0][0]);
        deskEntity.setF2(deskArr[0][1]);
        deskEntity.setF3(deskArr[0][2]);
        deskEntity.setF4(deskArr[1][0]);
        deskEntity.setF5(deskArr[1][1]);
        deskEntity.setF6(deskArr[1][2]);
        deskEntity.setF7(deskArr[2][0]);
        deskEntity.setF8(deskArr[2][1]);
        deskEntity.setF9(deskArr[2][2]);
    }

}
