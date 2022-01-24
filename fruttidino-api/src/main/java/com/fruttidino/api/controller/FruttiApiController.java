package com.fruttidino.api.controller;

import com.fruttidino.api.common.exception.ApplicationException;
import com.fruttidino.api.common.exception.ErrorCode;
import com.fruttidino.api.entity.game.UserDino;
import com.fruttidino.api.entity.nft.binance.BinanceNftMetaJson;
import com.fruttidino.api.repository.DinoMetaRepository;
import com.fruttidino.api.entity.nft.NftMeta;
import com.fruttidino.api.entity.nft.opensea.NftMetaJson;
import com.fruttidino.api.service.DinoNftMetaService;
import com.fruttidino.api.service.DinoNftQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.fruttidino.api.common.util.Utils.*;

@Slf4j
@RestController
public class FruttiApiController {

    private final DinoMetaRepository dinoMetaRepository;
    private final DinoNftMetaService dinoNftMetaService;
    private final DinoNftQueueService dinoService;

    @Autowired
    public FruttiApiController(DinoMetaRepository dinoMetaRepository, DinoNftMetaService dinoNftMetaService, DinoNftQueueService dinoService) {
        this.dinoMetaRepository = dinoMetaRepository;
        this.dinoNftMetaService = dinoNftMetaService;
        this.dinoService = dinoService;
    }

    StringBuilder sb = new StringBuilder();


    @GetMapping("/dinos")
    public List<NftMeta> listAllDinoNftMeta() {
        List<NftMeta> list = new ArrayList<>();

        Iterable<NftMeta> iterable = dinoMetaRepository.findAllByDinoNftMetas();
        for (NftMeta nftMeta : iterable) {
            list.add(nftMeta);
        }
        return  list;
    }

    @GetMapping("/dino/{tokenId}")
    public BinanceNftMetaJson DinoNftMeta(@PathVariable("tokenId") Integer tokenId) throws Exception {
        BinanceNftMetaJson nftMetaJsonData;

        nftMetaJsonData = dinoNftMetaService.getBinanceNftMetaInfo(tokenId);
        if (nftMetaJsonData == null)
            throwApplicationException(log, sb.toString(), ErrorCode.NOT_EXIST_CHARACTER);

        return nftMetaJsonData;
    }

    @GetMapping("/dino/nft/{tokenId}")
    public String DinoNftTest(@PathVariable("tokenId") String tokenId){

        UUID genDinoId = UUID.fromString("3b9ed7eb-0e22-4864-b073-cba7f35dc3a1");
        dinoService.addNftMintInfo(genDinoId, UUID.randomUUID().toString(), null);

        log.info("add nft info = {}", genDinoId.toString());

        return "Success";
    }

    @GetMapping("/dino/add/{genId}")
    public String AddUserDinoTest(@PathVariable("genId") String genId){

        UUID genDinoId = UUID.fromString("3b9ed7eb-0e22-4864-b073-cba7f35dc3a1");
        UserDino userDino = new UserDino();
        userDino.setOwner("address");
        userDino.setGenId(genDinoId);
        userDino.setNftId(10000);
        dinoService.addNftOwnerTransfer(1, UUID.randomUUID().toString(), null);

        log.info("add nft info = {}", genDinoId.toString());

        return "Success";
    }
}
