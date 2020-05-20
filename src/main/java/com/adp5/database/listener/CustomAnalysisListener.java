package com.adp5.database.listener;

import java.util.List;
import java.util.Map;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-18
 * @Version 1.0
 */
public abstract class CustomAnalysisListener<T> extends AbstractValidateAndRestoreAnalysisListener<T> {

    public CustomAnalysisListener(String applicationMark, String versionMark) {
        super(applicationMark, versionMark);
    }

    /**
     * 恢复所有备份数据
     *
     * @param restoreDataMap: key: 文件名[excelSheet页名称/或表名]
     * @return
     */
    @Override
    protected boolean restoreData(Map<String, List<String>> restoreDataMap) {
        return false;
    }
}
