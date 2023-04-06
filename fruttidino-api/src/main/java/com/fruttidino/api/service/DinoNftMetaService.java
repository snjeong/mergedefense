package com.fruttidino.api.service;

import com.fruttidino.api.common.util.PlatformLogBuilder;
import com.fruttidino.api.entity.nft.NftMeta;
import com.fruttidino.api.entity.nft.binance.BinanceNftMetaJson;
import com.fruttidino.api.entity.nft.opensea.NftMetaAttributes;
import com.fruttidino.api.entity.nft.opensea.NftMetaJson;
import com.fruttidino.api.entity.type.*;
import com.fruttidino.api.repository.DinoGenRepository;
import com.fruttidino.api.repository.DinoMetaRepository;
import com.fruttidino.api.repository.NftLogRepository;
import com.fruttidino.api.repository.UserDinoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        nftMetaJson.setName(nftMeta.getDinoType() + " #" + nftMeta.getNft_id());
        nftMetaJson.setImage(nftMeta.getIpfsOrigin());  // ipfs uri 전송
        nftMetaJson.setDescription(nftMeta.getNft_dsc());

        return nftMetaJson;
    }

    //@Profile("prod")
    //@Cacheable(value = "NftMetaInfo", key = "#tokenId", cacheManager = "nftMetaCacheManager")
    public NftMetaJson getNftMetaInfo(Integer tokenId) {
        /* TODO : 디버깅을 위한 네이티브쿼리
        String value = "select di.gen_id, di.attribute, di.role, di.talent, di.grade, di.name_string as dino_name, di.pure_part_count, di.nft_id, di.gen_seq, di.dino_type, di.body, di.head, di.eyes, di.mouth, di.limited_part_count, di.ipfs_origin, di.ipfs_cdn, di.nft_dsc, \n" +
                "\t(select distinct tag_title_string from game.limited_dino ld where ld.idx = di.tag_idx) as limited_tag,\n" +
                " \t(select distinct limited_name from game.dino_part dp where dp.head = di.head ) as limited_part_set,\n" +
                "\t(select distinct limited_name from game.dino_part dp where dp.head = di.head) as head_title,\n" +
                "\t(select distinct head_name_string from game.dino_part dp where dp.head = di.head ) as head_name,\n" +
                "\t(select distinct limited_name from game.dino_part dp where dp.eyes = di.eyes) as eyes_title,\n" +
                "\t(select distinct eyes_name_string from game.dino_part dp where dp.eyes = di.eyes ) as eyes_name,\n" +
                "\t(select distinct limited_name from game.dino_part dp where dp.mouth = di.mouth) as mouth_title,\n" +
                "\t(select distinct mouth_name_string from game.dino_part dp where dp.mouth = di.mouth ) as mouth_name,\n" +
                "\t(select distinct limited_name from game.dino_part dp where dp.tail = di.tail) as tail_title,\n" +
                "\t(select distinct tail_name_string from game.dino_part dp where dp.tail = di.tail ) as tail_name,\n" +
                "\t(select limited_name from game.dino_part dp where dp.wing = di.wing limit 1) as wing_title,\n" +
                "\t(select wing_name_string from game.dino_part dp where dp.wing = di.wing limit 1) as wing_name,\n" +
                "\t(select distinct limited_name from game.dino_part dp where dp.back = di.back) as back_title,\n" +
                "\t(select distinct back_name_string from game.dino_part dp where dp.back = di.back ) as back_name,\n" +
                "\tdi.wing_slot\n" +
                "from (\n" +
                "\tselect * from game.dino_gen dg join game.dino_basic db on dg.dino_type = db.dino_type\n" +
                "\twhere dg.nft_id = :tokenId\n" +
                ") di\n";
        */

        nftMeta = dinoMetaRepository.findDinoNftMetaByTokenId(tokenId);
        if (nftMeta == null) {
            log.info(PlatformLogBuilder.create().setFuncNm("getNftMetaInfo").setFuncNm("findDinoNftMetaByTokenId").setSubStepNm("IN").setObj(tokenId).toString());
            return null;
        }

        NftMetaJson nftMetaJson = new NftMetaJson();
        nftMetaJson.setName(nftMeta.getDino_name() + " #" + nftMeta.getNft_id());
        nftMetaJson.setDescription(nftMeta.getNft_dsc());
        nftMetaJson.setImage(String.format("%s%s.png", NftMetaJson.NFT_ORIGIN_IMAGE_URI, nftMeta.getNft_id()));
        nftMetaJson.setImageIpfs(String.format("%s%s.png", NftMetaJson.NFT_ORIGIN_IMAGE_URI, nftMeta.getNft_id()));

        NftMetaAttributes dinoAttrLimitedTag = new NftMetaAttributes();
        dinoAttrLimitedTag.setTrait_type("Limited Tag");
        dinoAttrLimitedTag.setValue(nftMeta.getLimitedTag() == null ? "-" : nftMeta.getLimitedTag());
        nftMetaJson.getAttributes().add(dinoAttrLimitedTag);

        NftMetaAttributes dinoAttrPartSet = new NftMetaAttributes();
        dinoAttrPartSet.setTrait_type("Limited Parts");
        String limitedPartSet = "";
        if(nftMeta.getLimitedPartCount() > 0) {
            limitedPartSet = String.format("%d", nftMeta.getLimitedPartCount());
        }
        else {
            limitedPartSet = nftMeta.getLimitedPartSet().equals("none") ? "-" :
                    String.format("%s(%d)", nftMeta.getLimitedPartSet(), nftMeta.getLimitedPartCount());
        }
        dinoAttrPartSet.setValue(limitedPartSet);
        nftMetaJson.getAttributes().add(dinoAttrPartSet);

        NftMetaAttributes dinoAttrGrade = new NftMetaAttributes();
        Optional<DinoGrade> dinoGrade = Optional.of(DinoGrade.getDinoGradeByValue(nftMeta.getGrade()).orElse(DinoGrade.None));
        dinoAttrGrade.setTrait_type("Grade");
        dinoAttrGrade.setValue(dinoGrade.get().getDinoGradeName());
        nftMetaJson.getAttributes().add(dinoAttrGrade);

        NftMetaAttributes dinoAttrPureness = new NftMetaAttributes();
        dinoAttrPureness.setTrait_type("Purity");
        dinoAttrPureness.setValue(nftMeta.getPurePartCount() == 0 ? "-" : Integer.toString(nftMeta.getPurePartCount()));
        nftMetaJson.getAttributes().add(dinoAttrPureness);

        Optional<DinoType> dinoType = Optional.of(DinoType.getDinoTypeByValue(nftMeta.getDinoType()).orElse(DinoType.None));
        NftMetaAttributes dinoAttrClass = new NftMetaAttributes();
        dinoAttrClass.setTrait_type("Class");
        dinoAttrClass.setValue(dinoType.get().getDinoName());
        nftMetaJson.getAttributes().add(dinoAttrClass);

        Optional<DinoRole> dinoRole = Optional.of(DinoRole.getDinoRoleByValue(nftMeta.getRole()).orElse(DinoRole.None));
        NftMetaAttributes dinoAttrRole = new NftMetaAttributes();
        dinoAttrRole.setTrait_type("Role");
        dinoAttrRole.setValue(dinoRole.get().getDinoRoleName());
        nftMetaJson.getAttributes().add(dinoAttrRole);

        Optional<DinoTalent> dinoTalent = Optional.of(DinoTalent.getDinoTalentByValue(nftMeta.getTalent()).orElse(DinoTalent.None));
        NftMetaAttributes dinoAttrTalent = new NftMetaAttributes();
        dinoAttrTalent.setTrait_type("Talent");
        dinoAttrTalent.setValue(dinoTalent.get().getDinoTalentName());
        nftMetaJson.getAttributes().add(dinoAttrTalent);

        Optional<DinoAttribute> dinoAttribute = Optional.of(DinoAttribute.getDinoTypeByValue(nftMeta.getAttribute()).orElse(DinoAttribute.None));
        NftMetaAttributes dinoAttrAttribute = new NftMetaAttributes();
        dinoAttrAttribute.setTrait_type("Attribute");
        dinoAttrAttribute.setValue(dinoAttribute.get().getDinoAttributeName());
        nftMetaJson.getAttributes().add(dinoAttrAttribute);

        NftMetaAttributes dinoAttrHead = new NftMetaAttributes();
        String strHeadTitle = nftMeta.getHeadTitle().equals("none") ? "Head" : String.format("Head(%s)", nftMeta.getHeadTitle());
        dinoAttrHead.setTrait_type(strHeadTitle);
        dinoAttrHead.setValue(nftMeta.getHeadName());
        nftMetaJson.getAttributes().add(dinoAttrHead);

        NftMetaAttributes dinoAttrEyes = new NftMetaAttributes();
        String strEyeTitle = nftMeta.getEyesTitle().equals("none") ? "Eyes" : String.format("Eyes(%s)", nftMeta.getEyesTitle());
        dinoAttrEyes.setTrait_type(strEyeTitle);
        dinoAttrEyes.setValue(nftMeta.getEyesName());
        nftMetaJson.getAttributes().add(dinoAttrEyes);

        NftMetaAttributes dinoAttrMouth = new NftMetaAttributes();
        String strMouthTitle = nftMeta.getMouthTitle().equals("none") ? "Mouth" : String.format("Mouth(%s)", nftMeta.getMouthTitle());
        dinoAttrMouth.setTrait_type(strMouthTitle);
        dinoAttrMouth.setValue(nftMeta.getMouthName());
        nftMetaJson.getAttributes().add(dinoAttrMouth);

        NftMetaAttributes dinoAttrTail = new NftMetaAttributes();
        String strTailTitle = nftMeta.getTailTitle().equals("none") ? "Tail" : String.format("Tail(%s)", nftMeta.getTailTitle());
        dinoAttrTail.setTrait_type(strTailTitle);
        dinoAttrTail.setValue(nftMeta.getTailName());
        nftMetaJson.getAttributes().add(dinoAttrTail);

        NftMetaAttributes dinoAttrBack = new NftMetaAttributes();
        String strBackTitle = nftMeta.getBackTitle().equals("none") ? "Back" : String.format("Back(%s)", nftMeta.getBackTitle());
        dinoAttrBack.setTrait_type(strBackTitle);
        dinoAttrBack.setValue(nftMeta.getBackName());
        nftMetaJson.getAttributes().add(dinoAttrBack);

        NftMetaAttributes dinoAttrWing = new NftMetaAttributes();
        String strWingTitle = nftMeta.getWingTitle() == null || nftMeta.getWingTitle().equals("none") ? "Wing" : String.format("Wing(%s)", nftMeta.getWingTitle());
        dinoAttrWing.setTrait_type(strWingTitle);
        dinoAttrWing.setValue(nftMeta.getWingName() == null || nftMeta.getWingName().equals("none") ? "-" : nftMeta.getWingName());
        nftMetaJson.getAttributes().add(dinoAttrWing);

        NftMetaAttributes dinoAttrWingSlot = new NftMetaAttributes();
        dinoAttrWingSlot.setTrait_type("Wing Slot");
        dinoAttrWingSlot.setValue(nftMeta.getWingSlot() > 0 ? "Available" : "Unavailable");
        nftMetaJson.getAttributes().add(dinoAttrWingSlot);

        return nftMetaJson;
    }


}
