package com.fruttidino.api.entity.nft;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "game.nft_log")
public class NftLog {
    @Id
    @Column(name = "log_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logSeq;
    @Column(name = "log_index")
    private Integer logIndex;
    @Column(name = "block_number")
    private Integer blockNumber;
    @Column(name = "transaction_hash")
    private String transactionHash;
    @Column(name = "from_addr")
    private String from;
    @Column(name = "to_addr")
    private String to;
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "dino_id")
    private String dinoId;
    @Column(name = "msg_type")
    private String type;
}
