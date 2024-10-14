package com.ziumks.csv.config.redis;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 레디스 시스템 프로퍼티 값 주입
 *
 * @author  이상민
 * @since   2024.06.04 16:30
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@ConfigurationProperties(prefix = "spring.redis")
@Configuration
public class RedisListenerProperties {

    // 레디스 호스트
    @NotBlank(message = "host for Redis not found.")
    private String host;

    // 레디스 포트
    @NotBlank(message = "port for Redis not found.")
    private int port;

    // 레디스 구독할 채널
    @NotBlank(message = "Channel for Redis not found.")
    private String channel;

    // 레디스 메세지 이벤트 식별 id
    @NotBlank(message = "Msg Ide for Redis not found.")
    private String[] msgIde;

}
