package ru.sberbank.game.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sberbank.game.backend.exception.SessionNotFoundException;
import ru.sberbank.game.backend.persistence.SessionRepository;
import ru.sberbank.game.backend.persistence.entity.Session;

import static java.util.UUID.randomUUID;

@Service
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public Session newSession(boolean firstMove) {
        return sessionRepository.save(Session.builder()
                        .firstMove(firstMove)
                        .uid(String.valueOf(randomUUID()))
                        .build()
                );
    }

    public Session getSession(String uid) {
        return sessionRepository.findByUid(uid)
                .orElseThrow(() -> new SessionNotFoundException(uid));
    }
}
