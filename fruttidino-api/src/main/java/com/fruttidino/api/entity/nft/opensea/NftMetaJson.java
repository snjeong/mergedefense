package com.fruttidino.api.entity.nft.opensea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fruttidino.api.entity.nft.binance.BinanceNftMetaAttributes;
import com.fruttidino.api.entity.nft.opensea.NftMetaAttributes;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NftMetaJson {

    private String name;
    private String description;
    private String image;
    private String imageIpfs;
    private BinanceNftMetaAttributes extendInfo = new BinanceNftMetaAttributes();
    private List<NftMetaAttributes> attributes = new ArrayList<>();
}
