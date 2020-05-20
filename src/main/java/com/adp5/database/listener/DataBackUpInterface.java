package com.adp5.database.listener;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 数据处理接口
 *
 * @Author: hongyuliu
 * @Date: 2020-05-18
 * @Version 1.0
 */
public interface DataBackUpInterface {

    /**
     * 备份数据
     *
     * @param dataList
     */
    void backUp(List<String> dataList, String rootPath, String versionMark, String fileName);

    /**
     * 恢复备份
     *
     * @param versionMark
     */
    void restoreBackup(String rootPath, String versionMark, String fileName);
}
