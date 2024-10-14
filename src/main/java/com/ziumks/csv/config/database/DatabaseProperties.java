package com.ziumks.csv.config.database;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 데이터베이스별 시스템 프로퍼티 값 주입
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@ConfigurationProperties(prefix = "db")
@Configuration
public class DatabaseProperties {

    private Base base;

    private Target target;

    @Getter
    @Setter
    public static class Base{

        @NotBlank(message = "driver for BaseDB not found.")
        private String driver;

        @NotBlank(message = "dialect for BaseDB not found.")
        private String dialect;

        @NotBlank(message = "url for BaseDB not found.")
        private String url;

        @NotBlank(message = "username for BaseDB not found.")
        private String username;

        @NotBlank(message = "password for BaseDB not found.")
        private String password;

        private String schema;

        private String showSql = "false";

        private String ddlAuto = "none";

        private String maxPoolSize = "5";

    }

    @Getter
    @Setter
    public static class Target {

        private String driver;
        private String dialect;
        private String url;
        private String username;
        private String password;
        private String schema;
        private String showSql;
        private String ddlAuto;
        private String maxPoolSize;

    }

}
