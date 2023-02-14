package com.fruttidino.api.controller;

import com.fruttidino.api.common.exception.ApplicationException;
import com.fruttidino.api.common.exception.ErrorCode;
import com.fruttidino.api.entity.game.UserDino;
import com.fruttidino.api.entity.nft.Ipfs;
import com.fruttidino.api.entity.nft.Mint;
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

    @GetMapping("/dino/{tokenId}")
    public NftMetaJson DinoNftMeta(@PathVariable("tokenId") Integer tokenId) throws Exception {
        NftMetaJson nftMetaJsonData;

        nftMetaJsonData = dinoNftMetaService.getNftMetaInfo(tokenId);
        if (nftMetaJsonData == null)
            throwApplicationException(log, sb.toString(), ErrorCode.NOT_EXIST_CHARACTER);

        return nftMetaJsonData;
    }

    @GetMapping("/nft-market/{marketId}/dino/{tokenId}")
    public NftMetaJson DinoNftMeta(@PathVariable("marketId") Integer marketId, @PathVariable("tokenId") Integer tokenId) throws Exception {
        NftMetaJson nftMetaJsonData;

        // TODO: 마켓코드별 데이터 처리
        nftMetaJsonData = dinoNftMetaService.getNftMetaInfo(tokenId);
        if (nftMetaJsonData == null)
            throwApplicationException(log, sb.toString(), ErrorCode.NOT_EXIST_CHARACTER);

        return nftMetaJsonData;
    }

    @GetMapping("/dino/nft/{tokenId}")
    public String DinoNftTest(@PathVariable("tokenId") String tokenId){

        UUID genDinoId = UUID.fromString("024b1320-4e5f-4d6f-94ca-fedc924d1791");
        Mint mint = new Mint();
        mint.setBlockNumber(0);
        Ipfs ipfs = new Ipfs();
        ipfs.setCdn("http://cdn");
        ipfs.setOrigin("http://origin");
        mint.setTokenId(1112);
        mint.setDinoId(genDinoId.toString());
        mint.setTransactionHash("0x77e0b0aad2aca1ce36e75cd2d84b53554bfb23179de5e155e5d764de2d4b6ed0");
        mint.setTo("0x00000");
        mint.setIpfs(ipfs);
        mint.setType("Mint");
        mint.setLogIndex(1);
        dinoService.addNftMintInfo(genDinoId, UUID.randomUUID().toString(), mint);

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
