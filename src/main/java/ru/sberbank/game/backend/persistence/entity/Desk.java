package ru.sberbank.game.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "desks")
@Getter
@NoArgsConstructor
public class Desk {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private long id;

    private long sessionId;

    /* Ячейки поля
        f1 f2 f3
        f4 f5 f6
        f7 f9 f9
    */
    @Setter
    private String f1="1",f2="2",f3="3",f4="4",f5="5",f6="6",f7="7",f8="8",f9="9";
    @Builder
    private Desk(long sessionId) {
        this.sessionId = sessionId;
    }
}
