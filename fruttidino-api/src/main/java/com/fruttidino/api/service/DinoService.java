package com.fruttidino.api.service;

import com.fruttidino.api.entity.game.DinoGen;
import com.fruttidino.api.entity.nft.Mint;
import com.fruttidino.api.entity.nft.Transfer;
import com.fruttidino.api.entity.game.UserDino;
import com.fruttidino.api.entity.nft.NftLog;
import com.fruttidino.api.repository.DinoGenRepository;
import com.fruttidino.api.repository.NftLogRepository;
import com.fruttidino.api.repository.UserDinoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class DinoService {

    private final DinoGenRepository dinoGenRepository;
    private final UserDinoRepository userDinoRepository;
    private final NftLogRepository nftLogRepository;

    @Autowired
    public DinoService(DinoGenRepository dinoGenRepository, UserDinoRepository userDinoRepository, NftLogRepository nftLogRepository) {
        this.dinoGenRepository = dinoGenRepository;
        this.userDinoRepository = userDinoRepository;
        this.nftLogRepository = nftLogRepository;
    }

    @Transactional
    public void addNftMintInfo(UUID dinoId, String msgId, Mint mint) {
        try {
            NftLog nftLog = new NftLog();
            nftLog.setMsgId(msgId);
            nftLog.setLogIndex(mint.getLogIndex());
            nftLog.setBlockNumber(mint.getBlockNumber());
            nftLog.setTransactionHash(mint.getTransactionHash());
            nftLog.setFromAddr("0x0000000000000000000000000000000000000000");
            nftLog.setToAddr(mint.getTo());
            nftLog.setTokenId(mint.getTokenId());
            nftLog.setDinoId(mint.getDinoId());
            nftLog.setMsgType(mint.getType());
            nftLogRepository.save(nftLog);

            log.info("[addNftInfo] : dinoId = {}", dinoId);
            Optional<DinoGen> dinoGenOptional = dinoGenRepository.findById(dinoId);
            if (!dinoGenOptional.isPresent()) {
                log.info("[addNftInfo][error] : not exist, dinoId = {}", dinoId);
                return;
            }

            DinoGen dinoGen = dinoGenOptional.get();

            // merge dino_gen
            dinoGen.setNftId(mint.getTokenId());
            dinoGen.setIpfsCdn(mint.getIpfs().getCdn());
            dinoGen.setIpfsOrigin(mint.getIpfs().getOrigin());
            dinoGenRepository.save(dinoGen);

            // merge user_dino
            UserDino userDino = new UserDino();
            userDino.setGenId(dinoGen.getGenId());
            userDino.setNftId(dinoGen.getNftId());
            userDino.setOwner(mint.getTo());

            userDinoRepository.save(userDino);
        } catch (Exception ex) {
            log.error("[addNftInfo][execption] : {}", ex.getMessage());

        }
        log.info("[addNftInfo][success] : dinoId = {}", dinoId);
    }

    public void addUserDino(int paramDinoId, Transfer paramUserDino) {
        log.debug("{}", paramUserDino.toString());
    }

    @Transactional
    public void addNftOwnerTransfer(int paramTokenId, String msgId, Transfer transfer) {
        log.debug("[addUserTransfer] : {}", transfer.toString());
        try {
            NftLog nftLog = new NftLog();
            nftLog.setMsgId(msgId);
            nftLog.setLogIndex(transfer.getLogIndex());
            nftLog.setBlockNumber(transfer.getBlockNumber());
            nftLog.setTransactionHash(transfer.getTransactionHash());
            nftLog.setFromAddr(transfer.getFrom() == null ? "" : transfer.getFrom());
            nftLog.setToAddr(transfer.getTo() == null ? "" : transfer.getTo());
            nftLog.setTokenId(transfer.getTokenId());
            nftLog.setDinoId("");
            nftLog.setMsgType(transfer.getType());
            nftLogRepository.save(nftLog);

            Optional<DinoGen> dinoGenOptional = Optional.ofNullable(dinoGenRepository.findDinoGenByNftId(paramTokenId));
            if (!dinoGenOptional.isPresent()) {
                log.info("[addNftInfo][error] : not exist, tokenId = {}", paramTokenId);
                return;
            }

            DinoGen dinoGen = dinoGenOptional.get();
            UserDino userDino = new UserDino();
            userDino.setGenId(dinoGen.getGenId());
            userDino.setNftId(dinoGen.getNftId());
            userDino.setOwner(transfer.getTo());

            userDinoRepository.save(userDino);
            log.info("[addUserTransfer][success] : dinoId = {}", nftLog);
        }
        catch (Exception ex) {
            log.error("[addUserTransfer][execption] : {}", ex.getMessage());
        }

    }


}
