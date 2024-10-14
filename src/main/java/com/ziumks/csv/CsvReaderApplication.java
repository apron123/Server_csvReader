package com.ziumks.csv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class CsvReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvReaderApplication.class, args);
    }

}
