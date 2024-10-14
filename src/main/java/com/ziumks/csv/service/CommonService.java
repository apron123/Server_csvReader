package com.ziumks.csv.service;

import com.ziumks.csv.config.api.CommonApiProperties;
import com.ziumks.csv.config.database.DatabaseProperties;
import com.ziumks.csv.model.dto.common.BulkRequestDto;
import com.ziumks.csv.model.dto.common.BulkResponseDto;
import com.ziumks.csv.model.dto.common.SysMonitoringDto;
import com.ziumks.csv.model.mapper.SerDeMapper;
import com.ziumks.csv.util.Utils;
import com.ziumks.csv.util.enums.SysStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("commonService")
public class CommonService {

    private final CommonApiProperties commonApiProps;

    private final SerDeMapper serDeMapper;

    /**
     * 데이터 저장하는 메서드
     *
     * @param   entityList        엔티티 리스트
     * @param   sysMonitoringDto  체계 수집 상태 DTO
     * @param   jpaRepository     JPA 레파지토리
     */
    public <T> SysMonitoringDto insert(List<T> entityList, SysMonitoringDto sysMonitoringDto, JpaRepository jpaRepository) {

        try {
            if (!entityList.isEmpty()) {
                // 크롤러 상태 업데이트
                sysMonitoringDto.updateStatus(SysStatus.CRAWLER);
                // 성공한 엔티티만 bulk insert
                List<T> savedList = jpaRepository.saveAll(entityList);
                if (!savedList.isEmpty()) {
                    // 세이브 상태 업데이트
                    sysMonitoringDto.updateStatus(SysStatus.SAVE);
                    log.info("Insert success : " + savedList.size());
                    // 벌크 인설트
                    BulkResponseDto bulkResponseDto = Utils.sendInsertBulk(
                            commonApiProps.getBulk().getUrl(),
                            BulkRequestDto.builder()
                                    .dataList(savedList.stream()
                                            .map(serDeMapper::toMap)
                                            .collect(Collectors.toList()))
                                    .indexName(sysMonitoringDto.getSchemaName() + "_" + sysMonitoringDto.getTableName())
                                    .docId(Utils.getIdName(savedList.get(0).getClass())) // 사용된 엔티티의 클래스에서 ID 이름을 가져옵니다
                                    .tableName(sysMonitoringDto.getTableName())
                                    .build()
                    );
                    if (bulkResponseDto.getResponseCode() == 200) {
                        // 엘라스틱 상태 업데이트
                        sysMonitoringDto.updateStatus(SysStatus.ELASTIC);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return sysMonitoringDto;
        }

        return sysMonitoringDto;

    }

    /**
     * csv 파일 읽고 행 가져 와서 리스트로 파싱
     *
     * @param   targetPath  csv 파일이 있는 디렉토리
     * @param   csvFileNameList  읽을 csv 파일명 리스트
     */
    public List<List<String>> readCsv(String targetPath, String[] csvFileNameList) {

        log.info("csvFileNameList length : " + csvFileNameList.length);
        return Arrays.stream(csvFileNameList)
                .map(csvFileName -> {
                    try {
                        log.info("readCSV Start: " + csvFileName);
                        File csv = new File(targetPath + csvFileName);
                        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(csv.toPath()), StandardCharsets.UTF_8));
                        List<List<String>> lines = br.lines()
                                .skip(1) // 첫번째 줄 제외
                                .map(line -> Arrays.asList(line.split(",")))
                                .collect(Collectors.toList());
                        br.close();
                        log.info("csvList=\n" + lines);
                        return lines;
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * csv 파일 리스트 targetPath -> basePath로 이동
     *
     * @param   targetPath  csv 파일이 있는 디렉토리
     * @param   basePath  csv 파일이 이동 할 디렉토리
     * @param   csvFileNameList  이동할 csv 파일명 리스트
     */
    public void moveCsv(String targetPath, String basePath, String[] csvFileNameList) {

        Arrays.stream(csvFileNameList).forEach(csvFileName -> {
            // 파일 옮기는 부분
            log.info("=========== move csv ==========");
            log.info("path before: " + targetPath + csvFileName);
            log.info("path after: " + basePath + csvFileName);
            try {
                // target 폴더에서 base 폴더로 csv를 옮김
                Files.move(Paths.get(targetPath + csvFileName), Paths.get(basePath + csvFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error("Error occurred while moving file: " + e.getMessage());
            }
        });

    }

}
