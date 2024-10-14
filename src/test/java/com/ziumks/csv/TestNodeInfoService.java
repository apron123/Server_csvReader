package com.ziumks.csv;

//import com.ziumks.csv.config.api.CommonApiProperties;
//import com.ziumks.csv.config.csv.NodeInfoProperties;
//import com.ziumks.csv.config.database.DatabaseProperties;
//import com.ziumks.csv.model.dto.common.BulkResponseDto;
//import com.ziumks.csv.model.entity.base.NodeInfo;
//import com.ziumks.csv.repository.base.NodeInfoRepository;
//import com.ziumks.csv.service.CommonService;
//import com.ziumks.csv.service.NodeInfoService;
//import com.ziumks.csv.util.Utils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("광케이블 체계 데이터 수집 테스트")
//class TestNodeInfoService {
//
//    @InjectMocks
//    private NodeInfoService nodeInfoService;
//
//    @Mock
//    private CommonService commonService;
//
//    @Mock
//    private NodeInfoRepository nodeInfoRepository;
//
//    @Mock
//    private DatabaseProperties databaseProps;
//
//    @Mock
//    private CommonApiProperties commonApiProps;
//
//    @Mock
//    private NodeInfoProperties nodeInfoProps;
//
//    private final MockedStatic<Utils> mCommonMethod = mockStatic(Utils.class);
//
//    @BeforeEach
//    public void init() {
//
//        // Mocking database props
//        DatabaseProperties.Base base = new DatabaseProperties.Base();
//        base.setSchema("morkingSchema");
//        lenient().when(databaseProps.getBase()).thenReturn(base);
//
//        // Mocking common props
//        CommonApiProperties.Bulk bulk = new CommonApiProperties.Bulk();
//        bulk.setUrl("morkingBulkUrl");
//        lenient().when(commonApiProps.getBulk()).thenReturn(bulk);
//
//        CommonApiProperties.BaDda system = new CommonApiProperties.BaDda();
//        bulk.setUrl("morkingSysUrl");
//        lenient().when(commonApiProps.getBaDda()).thenReturn(system);
//
//        // Mocking nodeInfo props
//        NodeInfoProperties.Path path = new NodeInfoProperties.Path();
//        path.setBase("/Users/zium/Desktop/work/17F/source/Sys_csvReader/src/main/resources/opc_backup/");
//        path.setTarget("/Users/zium/Desktop/work/17F/source/Sys_csvReader/src/main/resources/opc/");
//        lenient().when(nodeInfoProps.getPath()).thenReturn(path);
//
//        // Moking sendUpdateSys
//        mCommonMethod.when(()->Utils.sendUpdateSys(anyString(), any())).thenReturn(null);
//
//        // Morking sendInsertBulk
//        BulkResponseDto bulkResponseDto = BulkResponseDto.builder()
//                .responseCode(200)
//                .build();
//        mCommonMethod.when(()->Utils.sendInsertBulk(anyString(), any())).thenReturn(bulkResponseDto);
//
//    }
//
//    @Test
//    @DisplayName("광케이블 체계 데이터 수집 로직 테스트")
//    void testSetNodeInfo() {
//
//        // Given
//        List<NodeInfo> nodeInfoList = Arrays.asList(
//                NodeInfo.builder().build(),
//                NodeInfo.builder().build()
//        );
//
//        // When
//        //when(nodeInfoRepository.saveAll(anyList())).thenReturn(nodeInfoList);
//
//        // Then
//        assertDoesNotThrow(() -> nodeInfoService.insert());
//
//    }
//
//    @Test
//    @DisplayName("csv 파일을 String으로 파싱 테스트")
//    void testReadCsv() {
//
//        // Given
//        String targetPath = nodeInfoProps.getPath().getTarget();
//        File nodeInfoCsv = new File(targetPath);
//        String[] csvFileNameList = nodeInfoCsv.list((f, name) -> name.endsWith(".csv"));
//
//        // Then
//        assertDoesNotThrow(() -> commonService.readCsv(targetPath, csvFileNameList));
//
//    }
//
//    @Test
//    @DisplayName("csv 파일 디렉토리 이동 테스트")
//    void testMoveCsv() {
//
//        // Given
//        String targetPath = nodeInfoProps.getPath().getTarget();
//        String basePath = nodeInfoProps.getPath().getBase();
//        File nodeInfoCsv = new File(targetPath);
//        String[] csvFileNameList = nodeInfoCsv.list((f, name) -> name.endsWith(".csv"));
//
//        // Then
//        assertDoesNotThrow(() -> commonService.moveCsv(targetPath, basePath, csvFileNameList));
//
//    }
//
//    @Test
//    @DisplayName("csv 파일 위치 초기화")
//    void testReset() {
//
//        // Given
//        String targetPath = nodeInfoProps.getPath().getTarget();
//        String basePath = nodeInfoProps.getPath().getBase();
//        File nodeInfoCsv = new File(basePath);
//        String[] csvFileNameList = nodeInfoCsv.list((f, name) -> name.endsWith(".csv"));
//
//        // Then
//        assertDoesNotThrow(() -> commonService.moveCsv(basePath, targetPath, csvFileNameList));
//
//    }
//
//
//}
