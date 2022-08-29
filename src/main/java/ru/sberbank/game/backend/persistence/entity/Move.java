package ru.sberbank.game.backend.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "moves")
@Data
@Accessors(chain = true)
public class Move {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long sessionId;
    private String move;
    private String moveBy;
    private Timestamp moveDate;
    private String moveSign;

}
