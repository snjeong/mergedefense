package com.fruttidino.api.controller;

import com.fruttidino.api.entity.game.UserDino;
import com.fruttidino.api.repository.DinoMetaRepository;
import com.fruttidino.api.entity.nft.DinoNftMeta;
import com.fruttidino.api.entity.nft.DinoNftMetaAttribute;
import com.fruttidino.api.entity.nft.DinoNftMetaJson;
import com.fruttidino.api.service.DinoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class FruttiApiController {

    private final DinoMetaRepository dinoMetaRepository;
    private final DinoService dinoService;

    @Autowired
    public FruttiApiController(DinoMetaRepository dinoMetaRepository, DinoService dinoService) {
        this.dinoMetaRepository = dinoMetaRepository;
        this.dinoService = dinoService;
    }

    @GetMapping("/dinos")
    public List<DinoNftMeta> listAllDinoNftMeta() {
        List<DinoNftMeta> list = new ArrayList<>();

        Iterable<DinoNftMeta> iterable = dinoMetaRepository.findAllByDinoNftMetas();
        for (DinoNftMeta dinoNftMeta : iterable) {
            list.add(dinoNftMeta);
        }
        return  list;
    }

    @GetMapping("/dino/{tokenId}")
    public DinoNftMetaJson DinoNftMeta(@PathVariable("tokenId") String tokenId) {
        DinoNftMeta dinoNftMeta;

        dinoNftMeta = dinoMetaRepository.findDinoNftMetaByTokenId(tokenId);

        DinoNftMetaJson dinoNftMetaJson = new DinoNftMetaJson();
        dinoNftMetaJson.setName(dinoNftMeta.getDino_type() + " #" + dinoNftMeta.getNft_id());
        dinoNftMetaJson.setImage(dinoNftMeta.getIpfsCdn());

        DinoNftMetaAttribute dinoAttrLimitedTag = new DinoNftMetaAttribute();
        dinoAttrLimitedTag.setTrait_type("Limited Tag");
        dinoAttrLimitedTag.setValue(dinoNftMeta.getLimitedTag() == null ? "-" : dinoNftMeta.getLimitedTag());
        dinoNftMetaJson.getAttributes().add(dinoAttrLimitedTag);

        DinoNftMetaAttribute dinoAttrPartSet = new DinoNftMetaAttribute();
        dinoAttrPartSet.setTrait_type("Limited Parts Set");
        String limitedPartSet = dinoNftMeta.getLimitedPartSet().equals("none") ? "-" :
                String.format("%s(%d)", dinoNftMeta.getLimitedPartSet(), dinoNftMeta.getLimitedPartCount());
        dinoAttrPartSet.setValue(limitedPartSet);
        dinoNftMetaJson.getAttributes().add(dinoAttrPartSet);

        DinoNftMetaAttribute dinoAttrGrade = new DinoNftMetaAttribute();
        dinoAttrGrade.setTrait_type("Grade");
        dinoAttrGrade.setValue(dinoNftMeta.getGrade());
        dinoNftMetaJson.getAttributes().add(dinoAttrGrade);

        DinoNftMetaAttribute dinoAttrPureness = new DinoNftMetaAttribute();
        dinoAttrPureness.setTrait_type("Pureness");
        dinoAttrPureness.setValue(Integer.toString(dinoNftMeta.getPurePartCount()));
        dinoNftMetaJson.getAttributes().add(dinoAttrPureness);

        DinoNftMetaAttribute dinoAttrClass = new DinoNftMetaAttribute();
        dinoAttrClass.setTrait_type("Class");
        dinoAttrClass.setValue(dinoNftMeta.getDino_type());
        dinoNftMetaJson.getAttributes().add(dinoAttrClass);

        DinoNftMetaAttribute dinoAttrRole = new DinoNftMetaAttribute();
        dinoAttrRole.setTrait_type("Role");
        dinoAttrRole.setValue(dinoNftMeta.getRole());
        dinoNftMetaJson.getAttributes().add(dinoAttrRole);

        DinoNftMetaAttribute dinoAttrTalent = new DinoNftMetaAttribute();
        dinoAttrTalent.setTrait_type("Talent");
        dinoAttrTalent.setValue(dinoNftMeta.getTalent());
        dinoNftMetaJson.getAttributes().add(dinoAttrTalent);

        DinoNftMetaAttribute dinoAttrAttribute = new DinoNftMetaAttribute();
        dinoAttrAttribute.setTrait_type("Attribute");
        dinoAttrAttribute.setValue(dinoNftMeta.getAttribute());
        dinoNftMetaJson.getAttributes().add(dinoAttrAttribute);

        DinoNftMetaAttribute dinoAttrHead = new DinoNftMetaAttribute();
        dinoAttrHead.setTrait_type(dinoNftMeta.getHeadTitle());
        dinoAttrHead.setValue(dinoNftMeta.getHeadName());
        dinoNftMetaJson.getAttributes().add(dinoAttrHead);

        DinoNftMetaAttribute dinoAttrEyes = new DinoNftMetaAttribute();
        dinoAttrEyes.setTrait_type(dinoNftMeta.getEyesTitle());
        dinoAttrEyes.setValue(dinoNftMeta.getEyesName());
        dinoNftMetaJson.getAttributes().add(dinoAttrEyes);

        DinoNftMetaAttribute dinoAttrMouth = new DinoNftMetaAttribute();
        dinoAttrMouth.setTrait_type(dinoNftMeta.getMouthTitle());
        dinoAttrMouth.setValue(dinoNftMeta.getMouthName());
        dinoNftMetaJson.getAttributes().add(dinoAttrMouth);

        DinoNftMetaAttribute dinoAttrTail = new DinoNftMetaAttribute();
        dinoAttrTail.setTrait_type(dinoNftMeta.getTailTitle());
        dinoAttrTail.setValue(dinoNftMeta.getTailName());
        dinoNftMetaJson.getAttributes().add(dinoAttrTail);

        DinoNftMetaAttribute dinoAttrWing = new DinoNftMetaAttribute();
        dinoAttrWing.setTrait_type(dinoNftMeta.getWingTitle() == null ? "Wing" : dinoNftMeta.getWingTitle());
        dinoAttrWing.setValue(dinoNftMeta.getWingName() == null ? "-" : dinoNftMeta.getWingName());
        dinoNftMetaJson.getAttributes().add(dinoAttrWing);

        DinoNftMetaAttribute dinoAttrWingSlot = new DinoNftMetaAttribute();
        dinoAttrWingSlot.setTrait_type("Wing Slot");
        dinoAttrWingSlot.setValue(dinoNftMeta.getWingSlot() > 0 ? "Available" : "Unavailable");
        dinoNftMetaJson.getAttributes().add(dinoAttrWingSlot);

        return dinoNftMetaJson;
    }

    @GetMapping("/dino/nft/{tokenId}")
    public String DinoNftTest(@PathVariable("tokenId") String tokenId){

        UUID genDinoId = UUID.fromString("3b9ed7eb-0e22-4864-b073-cba7f35dc3a1");
        dinoService.addNftInfo(genDinoId, null);

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
        dinoService.addUserTransfer(1, null);

        log.info("add nft info = {}", genDinoId.toString());

        return "Success";
    }
}
