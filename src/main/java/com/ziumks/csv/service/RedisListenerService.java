package com.ziumks.csv.service;

import com.ziumks.csv.config.redis.RedisListenerProperties;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 레디스 리스너 메시지 처리 서비스 로직
 *
 * @author  김주현
 * @since   2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Service("redisListenerService")
public class RedisListenerService implements MessageListener {

    private final ScheduledService service;

    private final RedisListenerProperties redisListenerProps;

    /**
     * Redis 메시지 수신 시 호출되는 메서드
     *
     * @param message 수신된 Redis 메시지
     * @param pattern 수신된 메세지 패턴
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String channel = new String(message.getChannel());
        String bodyMsg = new String(message.getBody());

        // 따옴표 제거
        bodyMsg = bodyMsg.replaceAll("\"", "");

        log.info("Message Received Channel : \t{}", channel); //byte[]로 오기 때문에 스트링으로 형변환이 필요하다.
        log.info("Message Received Body : \t\t{}", bodyMsg);

        // 메시지와 메서드 매핑
        Map<String, Runnable> actionMap = new HashMap<>();
        actionMap.put(redisListenerProps.getMsgIde()[0], service::insertNodeInfo);

        // 메시지에 해당하는 메서드 실행
        Runnable action = actionMap.get(bodyMsg);
        if (action != null) {
            action.run();
        }

    }

}
