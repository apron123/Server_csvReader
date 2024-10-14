package com.ziumks.csv.config.database;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * QueryDsl JPAQueryFactory 설정
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@RequiredArgsConstructor
@Configuration
public class QueryDslConfig {

    @PersistenceContext(unitName = DatabaseConstants.BaseDatabase.entity_manager_factory)
    private EntityManager baseEntityManager;

    /**
     * 기본 데이터베이스를 위한 JPAQueryFactory 빈 생성
     *
     * @return JPAQueryFactory for the base database
     */
    @Primary
    @Bean
    public JPAQueryFactory baseJpaQueryFactory() {
        return new JPAQueryFactory(baseEntityManager);
    }

}

