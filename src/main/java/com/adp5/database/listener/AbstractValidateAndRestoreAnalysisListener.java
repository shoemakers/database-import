package com.adp5.database.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-18
 * @Version 1.0
 */
@Slf4j
public abstract class AbstractValidateAndRestoreAnalysisListener<T> extends AbstractValidateAnalysisListener<T> implements DataBackUpInterface {

    private String versionMark;

    public AbstractValidateAndRestoreAnalysisListener(String applicationMark, String versionMark) {
        super(applicationMark);
        this.versionMark = versionMark;
    }

    @Override
    public void dataAnalysisBeforeHandler(List<T> dataList, AnalysisContext context) {
        log.info("获取备份的数据");
        String rootPath = getRootPath();
        // 获取需要备份的数据
        List<String> restoreDataList = getBackUpDataList(context);
        backUp(restoreDataList, rootPath, this.versionMark, context.readSheetHolder().getSheetName());
    }

    /**
     * 获取备份数据列表
     *
     * @param context
     * @return
     */
    protected abstract List<String> getBackUpDataList(AnalysisContext context);

    /**
     * 备份方法
     *
     * @param dataList
     * @param rootPath
     * @param versionMark
     * @param fileName
     */
    @Override
    public void backUp(List<String> dataList, String rootPath, String versionMark, String fileName) {
        log.info("备份数据");
        String filePath = getFilePath(rootPath, versionMark, fileName);
        File file = new File(filePath);

        FileAppender fileAppender = new FileAppender(file, 1024, true);
        dataList.forEach(s -> {
            fileAppender.append(s);
        });
        fileAppender.flush();
    }

    /**
     * 恢复数据
     *
     * @param rootPath
     * @param versionMark
     * @param fileName
     */
    @Override
    public void restoreBackup(String rootPath, String versionMark, String fileName) {
        log.info("恢复数据");
        String filePath = getFilePath(rootPath, versionMark);
        if (!FileUtil.isDirectory(filePath)) {
            return;
        }
        // 获取文件列表
        List<String> strings = FileUtil.listFileNames(filePath);
        if (CollectionUtil.isEmpty(strings)) {
            return;
        }
        // 读取每个文件到list中
        Map<String, List<String>> restoreDataMap = new HashMap<>(strings.size());
        for (String string : strings) {
            FileReader reader = new FileReader(string);
            List<String> dataList = reader.readLines();
            if (CollectionUtil.isNotEmpty(dataList)) {
                restoreDataMap.put(StrUtil.subAfter(string, File.separator, true), dataList);
            }
        }

        // 读取数据以后对数据进行恢复操作
        boolean restoreDataResult = restoreData(restoreDataMap);
        if (restoreDataResult) {
            // 恢复后移除所有文件
            FileUtil.del(filePath);
        }

    }

    @Override
    public void exceptionBeforeHandler(Exception exception, AnalysisContext context) {
        String rootPath = getRootPath();
        // 出现异常就进行数据恢复
        restoreBackup(rootPath, this.versionMark, context.readSheetHolder().getSheetName());

    }

    /**
     * 恢复数据实际方法[数据恢复后会删除该标识的文件]---恢复所有数据
     *
     * @param restoreDataMap: key: 文件名[excelSheet页名称/或表名]
     *                        value: 数据列表
     */
    protected boolean restoreData(Map<String, List<String>> restoreDataMap) {
        return false;
    }

    ;

    /**
     * 恢复一个数据
     *
     * @param restoreDataMap
     * @param fileName
     * @return
     */
    protected boolean restoreData(List<String> restoreDataMap, String fileName) {
        return false;
    }

    ;


    private String getRootPath() {
        return ResourceUtil.getResource("").getPath();
    }

    /**
     * 获取文件路径
     *
     * @param rootPath
     * @param versionMark
     * @return
     */
    private String getFilePath(String rootPath, String versionMark) {
        return getFilePath(rootPath, versionMark, null);
    }

    /**
     * 获取文件路径
     *
     * @param rootPath
     * @param versionMark
     * @param fileName
     * @return
     */
    private String getFilePath(String rootPath, String versionMark, String fileName) {

        Date date = new Date();
        String filePath =
                rootPath + DateUtil.year(date) + File.separator + DateUtil.month(date) + File.separator + DateUtil.dayOfMonth(date) + File.separator + versionMark;
        if (StrUtil.isNotEmpty(fileName)) {
            filePath += File.separator + fileName;
        }
        return filePath;
    }

}
