package com.fruttidino.api.entity.game;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity
@Table(name = "game.user_dino")
public class UserDino {
    @Id
    @Column(name = "gen_id")
    private UUID genId;
    @Column(name = "nft_id")
    private Integer nftId;
    @Column(name = "uid")
    private String uid;
    @Column(name = "market_type")
    private Integer marketType;
    @Column(name = "owner")
    private String owner;
    @Column(name = "dino_name")
    private String dinoName;
    @Column(name = "breed_count")
    private Integer breedCount;

}
