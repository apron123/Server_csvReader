package com.ziumks.csv.repository.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Query Dsl Override 매서드 작성
 * QEntity 서치 에러시, mvn clean compile 할 것
 *
 * @author 이상민
 * @since  2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
public class NodeInfoRepositoryCustomImpl implements NodeInfoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
