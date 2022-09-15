package ru.sberbank.game.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @NotNull
    private String uid;
    @Builder
    private Session(boolean firstMove, String uid) {
        this.uid = uid;
        this.firstMove = firstMove;
    }
}
