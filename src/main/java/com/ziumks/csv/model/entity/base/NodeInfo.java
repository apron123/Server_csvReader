package com.ziumks.csv.model.entity.base;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * DB와 연동되는 엔티티 객체
 *
 * @author  이상민
 * @since   2024.05.21 16:30
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "nodeinfo")
public class NodeInfo {

    @Id
    private long uid;

    @Column(name = "ioc_a", length = 200)
    private String iocA;

    @Column(name = "ioc_b", length = 200)
    private String iocB;

    @Column(name = "name_a", length = 100)
    private String nameA;

    @Column(name = "name_b", length = 100)
    private String nameB;

    @Column(name = "ip_a", length = 50)
    private String ipA;

    @Column(name = "ip_ab", length = 50)
    private String ipAb;

    @Column(name = "alramma")
    private int alramma;

    @Column(name = "alrammb")
    private int alrammb;

    @Column(name = "alramsa")
    private int alramsa;

    @Column(name = "alramsb")
    private int alramsb;

    @Column(name = "mode", length = 500)
    private String mode;

    public NodeInfo (List<String> lineList) {
        //1번째 열 uid -- index = 0
        this.uid = Long.parseLong(lineList.get(0));
        //2번째 열 groupName -- index = 1
        //this.groupName =  lineList.get(1);
        //3번째 열 locA -- index = 2
        this.iocA = lineList.get(2);
        // 4번째 열 locB -- index = 3
        this.iocB = lineList.get(3);
        // 5번째 열 nameA -- index = 4
        this.nameA = lineList.get(4);
        // 6번째 열 nameB -- index = 5
        this.nameB = lineList.get(5);
        // 7번째 열 ipA -- index = 6
        this.ipA = lineList.get(6);
        // 8번째 열 ipAb -- index = 7
        this.ipAb = lineList.get(7);
        // 9번째 열 alramMa -- index = 8 (int)
        this.alramma = Integer.parseInt(lineList.get(8));
        // 10번째 열 alramMb -- index = 9 (int)
        this.alrammb = Integer.parseInt(lineList.get(9));
        // 11번째 열 alramSa -- index = 10 (int)
        this.alramsa = Integer.parseInt(lineList.get(10));
        // 12번째 열 alramSb -- index = 11 (int)
        this.alramsb = Integer.parseInt(lineList.get(11));
        // 13번째 열 mode -- index = 12 (String)
        this.mode = lineList.get(12);
    }

}
