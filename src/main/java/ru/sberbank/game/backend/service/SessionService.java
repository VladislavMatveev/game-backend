package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.exception.SessionNotFoundException;
import ru.sberbank.game.backend.persistence.SessionRepository;
import ru.sberbank.game.backend.persistence.entity.Session;
import ru.sberbank.game.backend.utils.enums.GameStatus;

import static java.util.UUID.randomUUID;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public Session newSession(boolean firstMove) {
        return sessionRepository.save(Session.builder()
                        .firstMove(firstMove)
                        .uid(String.valueOf(randomUUID()))
                        .status(GameStatus.IN_PROGRESS)
                        .build()
                );
    }

    public Session getSession(String uid) {
        return sessionRepository.findByUid(uid)
                .orElseThrow(() -> new SessionNotFoundException(uid));
    }

    public void setStatus(Session session, GameStatus status, String winner) {
        if (session.getStatus() != status) {
            session.setStatus(status);
            session.setWinner(winner);

            sessionRepository.save(session);
        }
    }
}
