package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.controller.dto.ResponseDto;
import ru.sberbank.game.backend.exception.IllegalMoveException;
import ru.sberbank.game.backend.persistence.entity.Desk;
import ru.sberbank.game.backend.persistence.entity.Session;
import ru.sberbank.game.backend.utils.enums.GameStatus;

import java.util.Random;

// Основной сервис с логикой
@Service
@AllArgsConstructor
public class GameService {

    final String SIGN_X = "X";
    final String SIGN_O = "O";

    private final SessionService sessionService;
    private final MoveService moveService;
    private final DeskService deskService;

    // Обработка endpoint

    public ResponseDto newGame(String firstMove) {
        Session session = sessionService.newSession(
                Boolean.parseBoolean(firstMove)
        );

        Desk desk = Desk.builder()
                .sessionId(session.getId())
                .build();

        if (!session.isFirstMove()) {
            makeMachineMove(
                    desk,
                    session.getId(),
                    SIGN_X);
        }

        deskService.save(desk);

        return getCommonResponse(desk, session.getUid())
                .build();
    }
    public ResponseDto makeMove(String uid, int move) {

        Session session = sessionService.getSession(uid);
        Desk desk = deskService.getDesk(session.getId());

        checkMoveIsLegal(desk, move);

        String human_sign;
        String machine_sign;

        if (session.isFirstMove()) {
            human_sign = SIGN_X;
            machine_sign = SIGN_O;
        } else {
            human_sign = SIGN_O;
            machine_sign = SIGN_X;
        }

        if (isTableFull(desk)) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .status(GameStatus.FINISHED)
                    .build();
        }

        // ход человека
        makeHumanMove(
                desk,
                session.getId(),
                String.valueOf(move),
                human_sign
        );

        if (checkWin(human_sign, desk)) {
            return getCommonResponse(desk, uid)
                    .message("You win!")
                    .status(GameStatus.FINISHED)
                    .winner("Human")
                    .build();
        }

        if (isTableFull(desk)) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Draw!")
                    .status(GameStatus.FINISHED)
                    .build();
        }

        // ход машины
        makeMachineMove(
                desk,
                session.getId(),
                machine_sign
        );

        if (checkWin(machine_sign, desk)) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("You lose!")
                    .status(GameStatus.FINISHED)
                    .winner("Machine")
                    .build();
        }

        if (isTableFull(desk)) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Draw!")
                    .status(GameStatus.FINISHED)
                    .build();
        }

        return getCommonResponse(desk, uid).build();
    }

    private void checkMoveIsLegal(Desk desk, int move) {

        String currentValue = deskService.getFieldValue(desk, move);

        if (currentValue.equals(SIGN_O) || currentValue.equals(SIGN_X)) {
            throw new IllegalMoveException(Integer.toString(move));
        }

    }

    public ResponseDto getStatus(String uid) {

        Session session = sessionService.getSession(uid);
        Desk desk = deskService.getDesk(session.getId());

        if (checkWin(SIGN_X, desk) || checkWin(SIGN_O, desk)) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .status(GameStatus.FINISHED)
                    .build();
        }

        return getCommonResponse(desk, uid).build();
    }
    public ResponseDto cancelMove(String uid) {

        Session session = sessionService.getSession(uid);
        Desk desk = deskService.getDesk(session.getId());
        String lastMoveBy = moveService.getLastMove(session.getId()).getMoveBy();

        cancelLastMove(desk);

        // Если последний ход был Машины, то еще раз отменяем ход - человека
        if (lastMoveBy.equals("Machine")) {
            cancelLastMove(desk);
        }

        return getCommonResponse(desk, uid).build();
    }

    // Прочее

    private void makeHumanMove(Desk desk, long sessionId, String move, String sign) {
        moveService.makeHumanMove(
                sessionId,
                move,
                sign
        );

        deskService.setFieldValue(
                desk,
                Integer.parseInt(move),
                sign
        );
    }
    private void makeMachineMove(Desk desk, long sessionId, String sign) {

        String move = newMachineMove(desk);

        moveService.makeMachineMove(
                sessionId,
                move,
                sign
        );

        deskService.setFieldValue(
                desk,
                Integer.parseInt(move),
                sign);
    }
    private void cancelLastMove(Desk desk) {

        String move = moveService.cancelMove(desk.getSessionId());

        deskService.setFieldValue(
                desk,
                Integer.parseInt(move),
                move);
    }

    private ResponseDto.ResponseDtoBuilder getCommonResponse(Desk desk, String uid) {
        return ResponseDto.builder()
                .uid(uid)
                .desk(deskService.drawField(desk));
    }

    // Логика

    private String newMachineMove(Desk desk) {

        Random random = new Random();
        int num = random.nextInt(9) + 1;

        while (true) {
            String fieldValue = deskService.getFieldValue(desk, num);
            if (fieldValue.equals(SIGN_X) || fieldValue.equals(SIGN_O)) {
                num = random.nextInt(9) + 1;
                continue;
            }
            break;
        }
        return String.valueOf(num);
    }
    private boolean checkWin(String sign, Desk desk) {

        if (desk.getF1().equals(sign) && desk.getF2().equals(sign) && desk.getF3().equals(sign)
                || desk.getF4().equals(sign) && desk.getF5().equals(sign) && desk.getF6().equals(sign)
                || desk.getF7().equals(sign) && desk.getF8().equals(sign) && desk.getF9().equals(sign))
            return true;

        if (desk.getF1().equals(sign) && desk.getF4().equals(sign) && desk.getF7().equals(sign)
                || desk.getF2().equals(sign) && desk.getF5().equals(sign) && desk.getF8().equals(sign)
                || desk.getF3().equals(sign) && desk.getF6().equals(sign) && desk.getF9().equals(sign))
            return true;

        if ((desk.getF1().equals(sign) && desk.getF5().equals(sign) && desk.getF9().equals(sign))
                || (desk.getF3().equals(sign) && desk.getF5().equals(sign) && desk.getF7().equals(sign)))
            return true;

        return false;
    }

    private boolean isTableFull(Desk desk) {
        return (desk.getF1().equals(SIGN_O) || desk.getF1().equals(SIGN_X))
                && (desk.getF2().equals(SIGN_O) || desk.getF2().equals(SIGN_X))
                && (desk.getF3().equals(SIGN_O) || desk.getF3().equals(SIGN_X))
                && (desk.getF4().equals(SIGN_O) || desk.getF4().equals(SIGN_X))
                && (desk.getF5().equals(SIGN_O) || desk.getF5().equals(SIGN_X))
                && (desk.getF6().equals(SIGN_O) || desk.getF6().equals(SIGN_X))
                && (desk.getF7().equals(SIGN_O) || desk.getF7().equals(SIGN_X))
                && (desk.getF8().equals(SIGN_O) || desk.getF8().equals(SIGN_X))
                && (desk.getF9().equals(SIGN_O) || desk.getF9().equals(SIGN_X));
    }
}
