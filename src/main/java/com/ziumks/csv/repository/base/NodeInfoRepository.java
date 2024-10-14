package com.ziumks.csv.repository.base;

import com.ziumks.csv.model.entity.base.NodeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Entity, JpaRepository 상속받아 간단하게 구현.
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
@Repository("nodeInfoRepository")
public interface NodeInfoRepository extends JpaRepository<NodeInfo, Long>, NodeInfoRepositoryCustom {}
