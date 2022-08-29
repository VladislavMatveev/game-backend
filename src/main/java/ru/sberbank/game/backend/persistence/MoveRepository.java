package ru.sberbank.game.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.game.backend.persistence.entity.Move;

import java.util.Optional;

public interface MoveRepository extends JpaRepository<Move, Long> {
    Optional<Move> findTopBySessionIdOrderByIdDesc(long id);
}
