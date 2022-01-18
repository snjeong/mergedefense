package com.fruttidino.api.repository;

import com.fruttidino.api.entity.game.DinoGen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DinoGenRepository extends JpaRepository<DinoGen, UUID> {

    DinoGen findDinoGenByGenId(UUID dinoGenId);

    @Query(value = "SELECT * FROM game.dino_gen WHERE nft_id =:nft_id", nativeQuery = true)
    DinoGen findDinoGenByNftId(@Param("nft_id") int tokenId);

}