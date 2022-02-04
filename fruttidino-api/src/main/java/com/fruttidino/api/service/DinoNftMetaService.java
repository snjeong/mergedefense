package com.fruttidino.api.service;

import com.fruttidino.api.common.exception.ApplicationException;
import com.fruttidino.api.common.exception.ErrorCode;
import com.fruttidino.api.common.util.PlatformLogBuilder;
import com.fruttidino.api.entity.nft.NftMeta;
import com.fruttidino.api.entity.nft.binance.BinanceNftMetaJson;
import com.fruttidino.api.entity.nft.opensea.NftMetaAttributes;
import com.fruttidino.api.entity.nft.opensea.NftMetaJson;
import com.fruttidino.api.repository.DinoGenRepository;
import com.fruttidino.api.repository.DinoMetaRepository;
import com.fruttidino.api.repository.NftLogRepository;
import com.fruttidino.api.repository.UserDinoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fruttidino.api.common.util.Utils.throwApplicationException;

@Slf4j
@Service
public class DinoNftMetaService {

    private final DinoMetaRepository dinoMetaRepository;
    private final DinoGenRepository dinoGenRepository;
    private final UserDinoRepository userDinoRepository;
    private final NftLogRepository nftLogRepository;

    @Autowired
    public DinoNftMetaService(DinoMetaRepository dinoMetaRepository, DinoGenRepository dinoGenRepository, UserDinoRepository userDinoRepository, NftLogRepository nftLogRepository) {
        this.dinoMetaRepository = dinoMetaRepository;
        this.dinoGenRepository = dinoGenRepository;
        this.userDinoRepository = userDinoRepository;
        this.nftLogRepository = nftLogRepository;
    }

    StringBuilder sb = new StringBuilder();
    NftMeta nftMeta;

    public BinanceNftMetaJson getBinanceNftMetaInfo(Integer tokenId) {
        NftMeta nftMeta;

        nftMeta = dinoMetaRepository.findDinoNftMetaByTokenId(tokenId);
        if (nftMeta == null) {
            log.info(PlatformLogBuilder.create().setFuncNm("getBinanceNftMetaInfo").setFuncNm("findDinoNftMetaByTokenId").setSubStepNm("IN").setObj(tokenId).toString());
            return null;
        }

        BinanceNftMetaJson nftMetaJson = new BinanceNftMetaJson();
        nftMetaJson.setName(nftMeta.getDino_type() + " #" + nftMeta.getNft_id());
        nftMetaJson.setImage(nftMeta.getIpfsOrigin());  // ipfs uri 전송
        nftMetaJson.setDescription(nftMeta.getNft_dsc());

        return nftMetaJson;
    }

    public NftMetaJson getNftMetaInfo(Integer tokenId) {
        nftMeta = dinoMetaRepository.findDinoNftMetaByTokenId(tokenId);
        if (nftMeta == null) {
            log.info(PlatformLogBuilder.create().setFuncNm("getNftMetaInfo").setFuncNm("findDinoNftMetaByTokenId").setSubStepNm("IN").setObj(tokenId).toString());
            return null;
        }

        NftMetaJson nftMetaJson = new NftMetaJson();
        nftMetaJson.setName(nftMeta.getDino_name() + " #" + nftMeta.getNft_id());
        nftMetaJson.setDescription(nftMeta.getNft_dsc());
        nftMetaJson.setImage(nftMeta.getIpfsCdn());
        nftMetaJson.setImageIpfs(nftMeta.getIpfsOrigin());

        NftMetaAttributes dinoAttrLimitedTag = new NftMetaAttributes();
        dinoAttrLimitedTag.setTrait_type("Limited Tag");
        dinoAttrLimitedTag.setValue(nftMeta.getLimitedTag() == null ? "-" : nftMeta.getLimitedTag());
        nftMetaJson.getAttributes().add(dinoAttrLimitedTag);

        NftMetaAttributes dinoAttrPartSet = new NftMetaAttributes();
        dinoAttrPartSet.setTrait_type("Limited Parts Set");
        String limitedPartSet = nftMeta.getLimitedPartSet().equals("none") ? "-" :
                String.format("%s(%d)", nftMeta.getLimitedPartSet(), nftMeta.getLimitedPartCount());
        dinoAttrPartSet.setValue(limitedPartSet);
        nftMetaJson.getAttributes().add(dinoAttrPartSet);

        NftMetaAttributes dinoAttrGrade = new NftMetaAttributes();
        dinoAttrGrade.setTrait_type("Grade");
        dinoAttrGrade.setValue(nftMeta.getGrade());
        nftMetaJson.getAttributes().add(dinoAttrGrade);

        NftMetaAttributes dinoAttrPureness = new NftMetaAttributes();
        dinoAttrPureness.setTrait_type("Pureness");
        dinoAttrPureness.setValue(Integer.toString(nftMeta.getPurePartCount()));
        nftMetaJson.getAttributes().add(dinoAttrPureness);

        NftMetaAttributes dinoAttrClass = new NftMetaAttributes();
        dinoAttrClass.setTrait_type("Class");
        dinoAttrClass.setValue(nftMeta.getDino_name());
        nftMetaJson.getAttributes().add(dinoAttrClass);

        NftMetaAttributes dinoAttrRole = new NftMetaAttributes();
        dinoAttrRole.setTrait_type("Role");
        dinoAttrRole.setValue(nftMeta.getRole());
        nftMetaJson.getAttributes().add(dinoAttrRole);

        NftMetaAttributes dinoAttrTalent = new NftMetaAttributes();
        dinoAttrTalent.setTrait_type("Talent");
        dinoAttrTalent.setValue(nftMeta.getTalent());
        nftMetaJson.getAttributes().add(dinoAttrTalent);

        NftMetaAttributes dinoAttrAttribute = new NftMetaAttributes();
        dinoAttrAttribute.setTrait_type("Attribute");
        dinoAttrAttribute.setValue(nftMeta.getAttribute());
        nftMetaJson.getAttributes().add(dinoAttrAttribute);

        NftMetaAttributes dinoAttrHead = new NftMetaAttributes();
        dinoAttrHead.setTrait_type(nftMeta.getHeadTitle());
        dinoAttrHead.setValue(nftMeta.getHeadName());
        nftMetaJson.getAttributes().add(dinoAttrHead);

        NftMetaAttributes dinoAttrEyes = new NftMetaAttributes();
        dinoAttrEyes.setTrait_type(nftMeta.getEyesTitle());
        dinoAttrEyes.setValue(nftMeta.getEyesName());
        nftMetaJson.getAttributes().add(dinoAttrEyes);

        NftMetaAttributes dinoAttrMouth = new NftMetaAttributes();
        dinoAttrMouth.setTrait_type(nftMeta.getMouthTitle());
        dinoAttrMouth.setValue(nftMeta.getMouthName());
        nftMetaJson.getAttributes().add(dinoAttrMouth);

        NftMetaAttributes dinoAttrTail = new NftMetaAttributes();
        dinoAttrTail.setTrait_type(nftMeta.getTailTitle());
        dinoAttrTail.setValue(nftMeta.getTailName());
        nftMetaJson.getAttributes().add(dinoAttrTail);

        NftMetaAttributes dinoAttrWing = new NftMetaAttributes();
        dinoAttrWing.setTrait_type(nftMeta.getWingTitle() == null ? "Wing" : nftMeta.getWingTitle());
        dinoAttrWing.setValue(nftMeta.getWingName() == null ? "-" : nftMeta.getWingName());
        nftMetaJson.getAttributes().add(dinoAttrWing);

        NftMetaAttributes dinoAttrWingSlot = new NftMetaAttributes();
        dinoAttrWingSlot.setTrait_type("Wing Slot");
        dinoAttrWingSlot.setValue(nftMeta.getWingSlot() > 0 ? "Available" : "Unavailable");
        nftMetaJson.getAttributes().add(dinoAttrWingSlot);

        return nftMetaJson;
    }


}
