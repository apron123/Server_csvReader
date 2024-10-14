package com.ziumks.csv.service;

import com.ziumks.csv.config.api.CommonApiProperties;
import com.ziumks.csv.config.csv.NodeInfoProperties;
import com.ziumks.csv.config.database.DatabaseProperties;
import com.ziumks.csv.model.dto.common.BulkRequestDto;
import com.ziumks.csv.model.dto.common.BulkResponseDto;
import com.ziumks.csv.model.dto.common.SysMonitoringDto;
import com.ziumks.csv.model.entity.base.NodeInfo;
import com.ziumks.csv.model.mapper.SerDeMapper;
import com.ziumks.csv.repository.base.NodeInfoRepository;
import com.ziumks.csv.exception.HttpConnectionException;
import com.ziumks.csv.util.Utils;
import com.ziumks.csv.util.enums.SysStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ziumks.csv.config.database.DatabaseConstants.BaseDatabase.tx_manager;

/**
 * 광케이블(nodeinfo) csv데이터 저장 서비스 로직
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Service("nodeInfoService")
public class NodeInfoService {

    private final CommonService commonService;

    private final NodeInfoRepository nodeInfoRepository;

    private final DatabaseProperties databaseProps;

    private final CommonApiProperties commonApiProps;

    private final NodeInfoProperties nodeInfoProps;

    /**
     * nodeinfo CSV 파일을 저장하는 메서드
     *
     * @throws  HttpConnectionException  HTTP 연결 예외가 발생할 경우
     */
    @Transactional(transactionManager = tx_manager)
    public void insert() throws HttpConnectionException {

        SysMonitoringDto sysMonitoringDto = SysMonitoringDto.builder()
                .schemaName(databaseProps.getBase().getSchema())
                .tableName(Utils.getTableName(NodeInfo.class))
                .build();

        // csv 파일 디레토리
        String targetPath = nodeInfoProps.getPath().getTarget();
        log.info("file path : " + targetPath);
        try {
            String[] csvFileNameList = new File(targetPath).list((f, name) -> name.endsWith(".csv"));
            // 파일 리스트가 null 또는 길이가 0인 경우 리턴
            if (csvFileNameList == null || csvFileNameList.length == 0) {
                log.info("FAIL, No CSV files found in the directory.");
                return;
            }
            sysMonitoringDto = commonService.insert(commonService.readCsv(targetPath, csvFileNameList).stream()
                            .map(NodeInfo::new)
                            .collect(Collectors.toList()), sysMonitoringDto, nodeInfoRepository);
            // csv 파일 이동
            commonService.moveCsv(targetPath, nodeInfoProps.getPath().getBase(), csvFileNameList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            Utils.sendUpdateSys(commonApiProps.getBaDda().getUrl(), sysMonitoringDto);
        }

    }

}
