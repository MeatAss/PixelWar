package com.PixelWar.repository;

import com.PixelWar.domain.Lobby;
import com.PixelWar.domain.SimpleIdMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {

    List<Lobby> findAllByidLobby(Long idLobby);

    Long countByidLobby(Long idLobby);

    @Transactional
    Long deleteByconnection(String connection);

    @Query("select new com.PixelWar.domain.SimpleIdMessage(COUNT(l.idLobby), l.idLobby) from Lobby l group by l.idLobby")
    List<SimpleIdMessage> findIdLobbyCount();
}
