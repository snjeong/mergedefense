package com.fruttidino.api.repository;

import com.fruttidino.api.entity.DinoGen;
import com.fruttidino.api.entity.game.UserDino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDinoRepository  extends JpaRepository<UserDino, UUID> {

    //UserDino findByTokenId(int tokenId);
}
