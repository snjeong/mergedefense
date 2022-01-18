package com.fruttidino.api.entity.nft;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "game.nft_log")
public class NftLog {
    @Id
    @Column(name = "msg_id")
    private String msgId;
    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "log_index")
    private Integer logIndex;
    @Column(name = "block_number")
    private Integer blockNumber;
    @Column(name = "transaction_hash")
    private String transactionHash;
    @Column(name = "from_addr")
    private String fromAddr;
    @Column(name = "to_addr")
    private String toAddr;
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "dino_id")
    private String dinoId;

}
