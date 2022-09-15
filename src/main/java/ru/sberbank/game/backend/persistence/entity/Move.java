package ru.sberbank.game.backend.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "moves")
@Getter
@NoArgsConstructor
public class Move {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long sessionId;
    private String move;
    private String moveBy;
    private Timestamp moveDate;
    private String moveSign;
    @Builder
    private Move (long sessionId, String move, String moveBy, Timestamp moveDate, String moveSign) {
        this.sessionId = sessionId;
        this.move = move;
        this.moveBy = moveBy;
        this.moveDate = moveDate;
        this.moveSign = moveSign;
    }
}
