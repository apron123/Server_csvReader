package com.ziumks.csv.service;

import com.ziumks.csv.exception.HttpConnectionException;
import com.ziumks.csv.model.entity.base.NodeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ziumks.csv.config.database.DatabaseConstants.BaseDatabase.tx_manager;

/**
 * 스케쥴러 서비스 로직 - Start Point
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Service("scheduledService")
public class ScheduledService {

    private final NodeInfoService nodeInfoService;

    /**
     *  광케이블 csv 데이터 주기적으로 가져오는 스케줄러
     */
    @Async
    public void insertNodeInfo() {

        log.info("=========== Insert NodeInfo start ============ ");
        log.info("thread name : {}",Thread.currentThread().getName());
        try {
            nodeInfoService.insert();
        } catch (HttpConnectionException e) {
            log.error(e.getMessage());
        } finally {
            log.info("=========== Insert NodeInfo end ============ ");
        }

    }

}
