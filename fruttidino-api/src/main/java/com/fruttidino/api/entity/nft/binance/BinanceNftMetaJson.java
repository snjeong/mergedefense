package com.fruttidino.api.entity.nft.binance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BinanceNftMetaJson {

    private String name;
    private String description;
    private String image;
    private BinanceNftMetaAttributes extendInfo = new BinanceNftMetaAttributes();

}
