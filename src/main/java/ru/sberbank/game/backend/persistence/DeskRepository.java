package ru.sberbank.game.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.game.backend.persistence.entity.Desk;

import java.util.Optional;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    Optional<Desk> findBySessionId(long id);
}
