package ru.sberbank.game.backend.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.game.backend.persistence.entity.Desk;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    Desk findBySessionId(long id);
}
