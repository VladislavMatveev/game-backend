package ru.sberbank.game.backend.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "sessions")
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Session {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean firstMove;

    @NotNull
    private String uid;

}
