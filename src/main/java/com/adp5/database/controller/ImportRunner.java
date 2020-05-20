package com.adp5.database.controller;

import com.adp5.database.core.config.ApplicationEnum;
import com.adp5.database.entity.Book;
import com.adp5.database.entity.Grade;
import com.adp5.database.util.BookAnalysisListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-18
 * @Version 1.0
 */
@Component
public class ImportRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        String filePath = "C:\\Users\\sshoemaker\\Desktop\\test.xlsx";
        // 1. 读取文件数据
        ReadSheet bookSheet = EasyExcelFactory.readSheet("book")
                .autoTrim(true)
                .head(Book.class)
                .registerReadListener(new BookAnalysisListener("111","111"))
                .build();
        ExcelReader build = EasyExcelFactory.read(filePath).build();
        build.read(bookSheet).finish();
    }
}
