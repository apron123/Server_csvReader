package com.ziumks.csv.config.database;

/**
 * 데이터베이스별 엔티티매니저 설정 변수에 주입
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
public class DatabaseConstants {

    public class BaseDatabase{
        public static final String entity_manager_factory = "baseEntityManagerFactory";
        public static final String tx_manager = "baseTransactionManager";
        public static final String package_domain = "com.ziumks.csv.model.entity.base";
        public static final String package_repository = "com.ziumks.csv.repository.base";
    }

}
