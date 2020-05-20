package com.adp5.database.util;

import com.adp5.database.entity.Book;
import com.adp5.database.listener.CustomAnalysisListener;
import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-18
 * @Version 1.0
 */
@Slf4j
public class BookAnalysisListener extends CustomAnalysisListener<Book> {

    /**
     * 有参构造方法
     *
     * @param applicationMark: 应用标识[1001001]
     * @param versionMark:     版本标识 [id]
     */
    public BookAnalysisListener(String applicationMark, String versionMark) {
        super(applicationMark, versionMark);
    }

    /**
     * 保存数据到数据库
     */
    @Override
    public void doAfterDataAnalysis(List<Book> dataList, AnalysisContext context) {
        log.info("所有数据---->[{}]", dataList);
    }

    /**
     * 获取需要备份的数据
     *
     * @param context
     * @return
     */
    @Override
    protected List<String> getBackUpDataList(AnalysisContext context) {
        log.info("需要备份的数据");
        // 从数据库查询的
        return Arrays.asList("1", "2");
    }

    /**
     * 数据恢复
     *
     * @param restoreDataMap: key: 文件名[excelSheet页名称/或表名]
     * @return
     */
    @Override
    protected boolean restoreData(Map<String, List<String>> restoreDataMap) {

        return super.restoreData(restoreDataMap);
    }

}
