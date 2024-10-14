package com.ziumks.csv.config.csv;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * nodeinfo 시스템 프로퍼티 값 주입
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
@ConfigurationProperties(prefix = "csv.node-info")
@Configuration
public class NodeInfoProperties {

    private Path path;

    // nodeinfo csv path 설정
    @Getter
    @Setter
    public static class Path {

        @NotBlank(message = "base path for NodeInfo not found.")
        private String base;

        @NotBlank(message = "target path for NodeInfo not found.")
        private String target;

    }

}
