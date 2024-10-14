package com.ziumks.csv.config.csv;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * plt 시스템 프로퍼티 값 주입
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
@ConfigurationProperties(prefix = "csv.plt")
@Configuration
public class PltProperties {

    private Path path;

    // plt csv path 설정
    @Getter
    @Setter
    public static class Path {

        private String base;

        private String target;

    }

}
