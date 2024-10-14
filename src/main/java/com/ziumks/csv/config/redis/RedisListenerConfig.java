package com.ziumks.csv.config.redis;

import com.ziumks.csv.service.RedisListenerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

/**
 * 레디스 리스너 연결 관련 설정
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
@EnableRedisRepositories
@Configuration
public class RedisListenerConfig {

    private final RedisListenerService redisListenerService;

    private final RedisListenerProperties redisListenerProps;

    /**
     * Redis 연결 팩토리를 생성하여 반환합니다.
     *
     * @return LettuceConnectionFactory 객체
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        log.info("#################################################");
        log.info("## Redis Connection - Redis Listener Config ");
        log.info("Redis Host : {}", redisListenerProps.getHost());
        log.info("Redis Port : {}", redisListenerProps.getPort());
        log.info("Redis Channel : {}", redisListenerProps.getChannel());
        log.info("Redis msg ide : {}", Arrays.toString(redisListenerProps.getMsgIde()));
        log.info("#################################################");

        return new LettuceConnectionFactory(redisListenerProps.getHost(), redisListenerProps.getPort());
    }

    /**
     * Redis 메시지를 수신하는 MessageListenerAdapter 빈을 생성합니다.
     *
     * @return MessageListenerAdapter 객체
     */
    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(redisListenerService);
    }

    /**
     * RedisMessageListenerContainer 빈을 생성하여 Redis 연결 팩토리 및 메시지 리스너 어댑터를 설정합니다.
     *
     * @param connectionFactory RedisConnectionFactory 객체
     * @param listenerAdapter MessageListenerAdapter 객체
     * @return RedisMessageListenerContainer 객체
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(redisListenerProps.getChannel()));

        return container;

    }

}

