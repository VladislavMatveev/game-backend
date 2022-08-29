package ru.sberbank.game.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "desks")
@Data
@Accessors(chain = true)
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
    private String f1="1",f2="2",f3="3",f4="4",f5="5",f6="6",f7="7",f8="8",f9="9";

}
