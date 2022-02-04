package com.fruttidino.api.entity.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class NftMeta {
    @Id
    @GeneratedValue
    @Column(name = "gen_id")
    private UUID genId;
    @Column(name = "nft_id")
    private String nft_id;
    @Column(name = "ipfs_cdn")
    private String ipfsCdn;
    @Column(name = "ipfs_origin")
    private String ipfsOrigin;
    @Column(name = "dino_name")
    private String dino_name;
    @Column(name = "dino_type")
    private int dino_type;
    @Column(name = "nft_dsc")
    private String nft_dsc;
    @Column(name = "role")
    private String role;
    @Column(name = "grade")
    private String grade;
    @Column(name = "talent")
    private String talent;
    @Column(name = "attribute")
    private String attribute;
    @Column(name = "wing_slot")
    private int wingSlot;
    @Column(name = "head_title")
    private String headTitle;
    @Column(name = "head_name")
    private String headName;
    @Column(name = "eyes_title")
    private String eyesTitle;
    @Column(name = "eyes_name")
    private String eyesName;
    @Column(name = "mouth_title")
    private String mouthTitle;
    @Column(name = "mouth_name")
    private String mouthName;
    @Column(name = "tail_title")
    private String tailTitle;
    @Column(name = "tail_name")
    private String tailName;
    @Column(name = "wing_title")
    private String wingTitle;
    @Column(name = "wing_name")
    private String wingName;
    @Column(name = "pure_part_count")
    private int purePartCount;
    @Column(name = "limited_part_set")
    private String limitedPartSet;
    @Column(name = "limited_part_count")
    private int limitedPartCount;
    @Column(name = "limited_tag")
    private String limitedTag;
}
