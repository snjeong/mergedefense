package com.fruttidino.api.entity.nft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DinoNftMetaJson {

    private String name;
    private String description;
    private String image;
    private List<DinoNftMetaAttribute> attributes = new ArrayList<>();

}
