package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.controller.dto.DeskDto;
import ru.sberbank.game.backend.controller.dto.ResponseDto;
import ru.sberbank.game.backend.exception.IllegalMoveException;
import ru.sberbank.game.backend.persistence.entity.Session;
import ru.sberbank.game.backend.utils.enums.GameStatus;

import java.util.Random;

// Основной сервис с логикой
@Service
@AllArgsConstructor
public class GameService {

    private final String SIGN_X = "X";
    private final String SIGN_O = "O";

    private final SessionService sessionService;
    private final MoveService moveService;
    private final DeskService deskService;

    // Обработка endpoint

    public ResponseDto newGame(String firstMove) {
        Session session = sessionService.newSession(
                Boolean.parseBoolean(firstMove)
        );

        DeskDto desk = deskService.getDeskDto(session.getId());

        if (!session.isFirstMove()) {
            makeMachineMove(
                    desk,
                    SIGN_X);
        }

        deskService.save(desk);

        return getCommonResponse(desk, session.getUid())
                .build();
    }
    public ResponseDto makeMove(String uid, int move) {

        Session session = sessionService.getSession(uid);
        DeskDto desk = deskService.getDeskDto(session.getId());

        if (checkWin(SIGN_X, desk.getDeskArr()) || checkWin(SIGN_O, desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .message("Game end!")
                    .status(GameStatus.FINISHED)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .build();
        }

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

        if (isTableFull(desk.getDeskArr())) {
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

        if (checkWin(human_sign, desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .message("You win!")
                    .status(GameStatus.FINISHED)
                    .winner("Human")
                    .build();
        }

        if (isTableFull(desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Draw!")
                    .status(GameStatus.FINISHED)
                    .build();
        }

        // ход машины
        makeMachineMove(
                desk,
                machine_sign
                );

        if (checkWin(machine_sign, desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("You lose!")
                    .status(GameStatus.FINISHED)
                    .winner("Machine")
                    .build();
        }

        if (isTableFull(desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("Draw!")
                    .status(GameStatus.FINISHED)
                    .build();
        }

        return getCommonResponse(desk, uid).build();
    }
    private void checkMoveIsLegal(DeskDto desk, int move) {

        String currentValue = deskService.getFieldValue(desk.getDeskArr(), move);

        if (currentValue.equals(SIGN_O) || currentValue.equals(SIGN_X)) {
            throw new IllegalMoveException(Integer.toString(move));
        }
    }
    public ResponseDto getStatus(String uid) {

        Session session = sessionService.getSession(uid);
        DeskDto desk = deskService.getDeskDto(session.getId());

        if (checkWin(SIGN_X, desk.getDeskArr()) || checkWin(SIGN_O, desk.getDeskArr())) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .status(GameStatus.FINISHED)
                    .build();
        }

        return ResponseDto.builder()
                .uid(uid)
                .desk(deskService.drawField(desk))
                .build();
    }
    public ResponseDto cancelMove(String uid) {

        Session session = sessionService.getSession(uid);
        DeskDto desk = deskService.getDeskDto(session.getId());
        String lastMoveBy = moveService.getLastMove(session.getId()).getMoveBy();

        if (moveService.noHumanMoves(session.getId())) {
            return getCommonResponse(desk, uid)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }

        cancelLastMove(desk);

        // Если последний ход был Машины, то еще раз отменяем ход - человека
        if (lastMoveBy.equals("Machine")) {
            cancelLastMove(desk);
        }

        return getCommonResponse(desk, uid).build();
    }

    // Прочее
    private void makeHumanMove(DeskDto desk, long sessionId, String move, String sign) {
        moveService.makeHumanMove(
                sessionId,
                move,
                sign
        );

        deskService.setFieldValue(
                Integer.parseInt(move),
                sign,
                desk.getDeskArr()
                );

        deskService.save(desk);
    }
    private void makeMachineMove(DeskDto desk, String sign) {

        String move = newMachineMove(desk);

        moveService.makeMachineMove(
                desk.getSessionId(),
                move,
                sign
        );

        deskService.setFieldValue(
                Integer.parseInt(move),
                sign,
                desk.getDeskArr());

        deskService.save(desk);
    }
    private void cancelLastMove(DeskDto desk) {

        String move = moveService.cancelMove(desk.getSessionId());

        deskService.setFieldValue(
                Integer.parseInt(move),
                move,
                desk.getDeskArr());

        deskService.save(desk);
    }

    private ResponseDto.ResponseDtoBuilder getCommonResponse(DeskDto desk, String uid) {
        return ResponseDto.builder()
                .uid(uid)
                .desk(deskService.drawField(desk));
    }

    // Логика

    private String newMachineMove(DeskDto desk) {

        Random random = new Random();
        int num = random.nextInt(9) + 1;

        while (true) {
            String fieldValue = deskService.getFieldValue(desk.getDeskArr(), num);
            if (fieldValue.equals(SIGN_X) || fieldValue.equals(SIGN_O)) {
                num = random.nextInt(9) + 1;
                continue;
            }
            break;
        }
        return String.valueOf(num);
    }

    public boolean checkWin(String sign, String[][] deskArr) {
        // check rows & columns
        for (int i = 0; i < 3; i++) {
            if (deskArr[i][0].equals(sign) && deskArr[i][1].equals(sign) && deskArr[i][2].equals(sign)
                    || deskArr[0][i].equals(sign) && deskArr[1][i].equals(sign) && deskArr[2][i].equals(sign))
                return true;
        }
        // check diagonals
        if (deskArr[0][0].equals(sign) && deskArr[1][1].equals(sign) && deskArr[2][2].equals(sign)
                || deskArr[2][0].equals(sign) && deskArr[1][1].equals(sign) && deskArr[0][2].equals(sign))
            return true;

        return false;
    }
    public boolean isTableFull(String[][] deskArr) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (!deskArr[row][col].equals(SIGN_X) && !deskArr[row][col].equals(SIGN_O))
                    return false;
        return true;
    }
}
