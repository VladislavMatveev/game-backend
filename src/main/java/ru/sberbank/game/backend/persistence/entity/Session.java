package ru.sberbank.game.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberbank.game.backend.utils.enums.GameStatus;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
@Getter
@NoArgsConstructor
public class Session {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean firstMove;

    @Setter
    private GameStatus status;
    @Setter
    private String winner;
    @NotNull
    private String uid;
    @Builder
    private Session(boolean firstMove, String uid, GameStatus status) {
        this.uid = uid;
        this.firstMove = firstMove;
        this.status = status;
    }
}
