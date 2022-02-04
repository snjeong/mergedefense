package com.fruttidino.api.repository;

import com.fruttidino.api.entity.nft.NftMeta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DinoMetaRepository extends CrudRepository<NftMeta, Long> {

    @Query(value = "select di.gen_id, di.nft_id, di.gen_seq, di.body, di.head, di.eyes, di.mouth, di.limited_part_count, di.ipfs_uri,\n" +
            "\t(select tag_title_string from game.limited_dino ld where ld.idx = di.tag) as limited_tag,\n" +
            " \t(select limited_name from game.dino_part dp where dp.head = di.head ) as limited_part_set,\n" +
            " \t(case di.grade\n" +
            "\t \twhen 1 then 'normal'\n" +
            "\t \twhen 2 then 'rare'\n" +
            "\t \twhen 3 then 'legend' \t\n" +
            " \tend) as grade,\n" +
            " \tdi.pure_part_count as pure_part_count,\n" +
            "\t(case di.dino_type\n" +
            "\t  \twhen 1 then 'orange'\n" +
            "\t\twhen 2 then 'waterMelon'\n" +
            "\t\twhen 3 then 'durian'\n" +
            "\t\twhen 4 then 'coconut'\n" +
            "\t\twhen 5 then 'blueBerry'\n" +
            "\t\twhen 6 then 'melon'\n" +
            "\t\twhen 7 then 'pineApple'\n" +
            "\t\twhen 8 then 'banana'\n" +
            "\t\twhen 9 then 'rambutan'\n" +
            "\t\twhen 10 then 'dragonFruit'\n" +
            "\tend) as dino_type,\n" +
            "\tdi.role,\n" +
            "\t(case di.talent\n" +
            "\t\twhen 1 then 'Carnivore'\n" +
            "\t\twhen 2 then 'Herbivore'\n" +
            "\t\twhen 3 then 'Omnivore'\n" +
            "\tend) as talent,\n" +
            "\t(case di.attribute\n" +
            "\t\twhen 1 then 'day'\n" +
            "\t\twhen 2 then 'nightn'\n" +
            "\t\twhen 3 then 'dawn'\n" +
            "\t\twhen 4 then 'eclipse'\n" +
            "\tend) as attribute ,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Head' else 'Head' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.head = di.head\n" +
            "\t) as head_title,\n" +
            "\t(select head_name_string from game.dino_part dp where dp.head = di.head ) as head_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Eyes' else 'Eyes' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.eyes = di.eyes\n" +
            "\t) as eyes_title,\n" +
            "\t(select eyes_name_string from game.dino_part dp where dp.eyes = di.eyes ) as eyes_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Mouth' else 'Mouth' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.mouth = di.mouth\n" +
            "\t) as mouth_title,\n" +
            "\t(select mouth_name_string from game.dino_part dp where dp.mouth = di.mouth ) as mouth_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Tail' else 'Tail' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.tail = di.tail\n" +
            "\t) as tail_title,\n" +
            "\t(select tail_name_string from game.dino_part dp where dp.tail = di.tail ) as tail_name,\n" +
            "\t( select  \n" +
            "\t\tcase coalesce(limited_name, 'none') when 'none' then 'Wing' else 'Wing' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.wing = di.wing\n" +
            "\t) as wing_title,\n" +
            "\t(select wing_name_string from game.dino_part dp where dp.wing = di.wing ) as wing_name,\n" +
            "\tdi.wing_slot\n" +
            "from (\n" +
            "\tselect * from game.dino_gen dg join game.dino_basic db on dg.dino_type = db.dino_type\n" +
            ") di", nativeQuery = true)
    List<NftMeta> findAllByDinoNftMetas();

    @Query(value = "select di.gen_id, di.nft_id, di.gen_seq, di.dino_type, di.body, di.head, di.eyes, di.mouth, di.limited_part_count, di.ipfs_origin, di.ipfs_cdn, di.nft_dsc, \n" +
            "\t(select tag_title_string from game.limited_dino ld where ld.idx = di.tag_idx) as limited_tag,\n" +
            " \t(select limited_name from game.dino_part dp where dp.head = di.head ) as limited_part_set,\n" +
            " \t(case di.grade\n" +
            "\t \twhen 1 then 'normal'\n" +
            "\t \twhen 2 then 'rare'\n" +
            "\t \twhen 3 then 'legend' \t\n" +
            " \tend) as grade,\n" +
            " \tdi.pure_part_count as pure_part_count,\n" +
            "\tdi.name_string as dino_name,\n" +
            "\tdi.role,\n" +
            "\t(case di.talent\n" +
            "\t\twhen 1 then 'Carnivore'\n" +
            "\t\twhen 2 then 'Herbivore'\n" +
            "\t\twhen 3 then 'Omnivore'\n" +
            "\tend) as talent,\n" +
            "\t(case di.attribute\n" +
            "\t\twhen 1 then 'day'\n" +
            "\t\twhen 2 then 'nightn'\n" +
            "\t\twhen 3 then 'dawn'\n" +
            "\t\twhen 4 then 'eclipse'\n" +
            "\tend) as attribute ,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Head' else 'Head' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.head = di.head\n" +
            "\t) as head_title,\n" +
            "\t(select head_name_string from game.dino_part dp where dp.head = di.head ) as head_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Eyes' else 'Eyes' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.eyes = di.eyes\n" +
            "\t) as eyes_title,\n" +
            "\t(select eyes_name_string from game.dino_part dp where dp.eyes = di.eyes ) as eyes_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Mouth' else 'Mouth' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.mouth = di.mouth\n" +
            "\t) as mouth_title,\n" +
            "\t(select mouth_name_string from game.dino_part dp where dp.mouth = di.mouth ) as mouth_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Tail' else 'Tail' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.tail = di.tail\n" +
            "\t) as tail_title,\n" +
            "\t(select tail_name_string from game.dino_part dp where dp.tail = di.tail ) as tail_name,\n" +
            "\t( select \n" +
            "\t\tcase limited_name when 'none' then 'Wing' else 'Wing' || '(' || limited_name || ')' end\n" +
            "\t  from game.dino_part dp where dp.wing = di.wing\n" +
            "\t) as wing_title,\n" +
            "\t(select wing_name_string from game.dino_part dp where dp.wing = di.wing ) as wing_name,\n" +
            "\tdi.wing_slot\n" +
            "from (\n" +
            "\tselect * from game.dino_gen dg join game.dino_basic db on dg.dino_type = db.dino_type\n" +
            "\twhere dg.nft_id = :tokenId\n" +
            ") di", nativeQuery = true)
    NftMeta findDinoNftMetaByTokenId(@Param("tokenId") Integer tokenId);

}
