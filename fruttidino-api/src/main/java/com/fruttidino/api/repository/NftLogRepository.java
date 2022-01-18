package com.fruttidino.api.repository;

import com.fruttidino.api.entity.nft.NftLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftLogRepository extends JpaRepository<NftLog, String> {
}