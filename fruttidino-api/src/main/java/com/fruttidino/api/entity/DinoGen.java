package com.fruttidino.api.entity;



import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "game.dino_gen")
public class DinoGen {
    @Id
    @GeneratedValue
    @Column(name = "gen_id")
    private UUID genId;
    @Column(name = "nft_id")
    private Integer nftId;
    @Column(name = "ipfs_cdn")
    private String ipfsCdn;
    @Column(name = "ipfs_origin")
    private String ipfsOrigin;
    @Column(name = "sale_round")
    private Integer saleRound;
    @Column(name = "gen_seq")
    private Integer genSeq;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "dino_type")
    private Integer dinoType;
    @Column(name = "grade")
    private Integer grade;
    @Column(name = "talent")
    private Integer talent;
    @Column(name = "wing_slot")
    private Integer wingSlot;
    @Column(name = "body")
    private String body;
    @Column(name = "head")
    private String head;
    @Column(name = "eyes")
    private String eyes;
    @Column(name = "mouth")
    private String mouth;
    @Column(name = "back")
    private String back;
    @Column(name = "tail")
    private String tail;
    @Column(name = "wing")
    private String wing;
    @Column(name = "pure_part_count")
    private Integer purePartCount;
    @Column(name = "limited_part_type")
    private Integer limitedPartType;
    @Column(name = "limited_part_count")
    private Integer limitedPartCount;

}
