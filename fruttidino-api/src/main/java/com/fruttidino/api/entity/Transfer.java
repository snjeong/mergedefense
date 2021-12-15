package com.fruttidino.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transfer {
    private Integer logIndex;
    private Integer blockNumber;
    private String transactionHash;
    private String from;
    private String to;
    private Integer tokenId;
    private String type;
}
