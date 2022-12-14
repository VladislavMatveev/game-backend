package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.exception.MoveNotFoundException;
import ru.sberbank.game.backend.persistence.MoveRepository;
import ru.sberbank.game.backend.persistence.entity.Move;

import java.sql.Timestamp;
import java.util.Date;

@Service
@AllArgsConstructor
public class MoveService {

    private final MoveRepository moveRepository;


    public void makeHumanMove(long sessionId, String move, String sign) {
        makeMove(sessionId,
                move,
                "Human",
                sign);
    }

    public void makeMachineMove(long sessionId, String move, String sign) {
        makeMove(sessionId,
                move,
                "Machine",
                sign);
    }

    private void makeMove(long sessionId, String move, String moveBy, String moveSign) {
        moveRepository.save(Move.builder()
                .sessionId(sessionId)
                .move(move)
                .moveDate(new Timestamp(new Date().getTime()))
                .moveBy(moveBy)
                .moveSign(moveSign)
                .build()
        );
    }

    public Move getLastMove(long sessionId) {
        return moveRepository.findTopBySessionIdOrderByIdDesc(sessionId)
                .orElseThrow(MoveNotFoundException::new);
    }

    public Boolean noHumanMoves(long sessionId) {
        return moveRepository.findTopBySessionIdAndMoveBy(sessionId, "Human")
                .isEmpty();
    }

    public String cancelMove(long sessionId) {
        Move move = getLastMove(sessionId);
        moveRepository.delete(move);

        return move.getMove();
    }
}

