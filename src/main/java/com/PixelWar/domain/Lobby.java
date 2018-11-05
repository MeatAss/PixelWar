package com.PixelWar.domain;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "lobbies")
public class Lobby {
    public static final int maxCountConnections = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idLobby;
    private String connection;

    public Lobby() {
    }

    public Lobby(Long idLobby, String connection) {
        this.idLobby = idLobby;
        this.connection = connection;
    }

    public Lobby(Long id, Long idLobby, String connection) {
        this.id = id;
        this.idLobby = idLobby;
        this.connection = connection;
    }

    public Long getIdLobby()
    {
        return idLobby;
    }

    public String getConnection() {
        return connection;
    }
}
