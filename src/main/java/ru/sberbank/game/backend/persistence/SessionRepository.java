package ru.sberbank.game.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.game.backend.persistence.entity.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUid(String uid);
}
