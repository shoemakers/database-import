package com.adp5.database.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.adp5.database.core.exception.ApplicationException;
import com.adp5.database.core.exception.DataValidateException;
import com.adp5.database.util.ValidatorUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-15
 * @Version 1.0
 */
@Slf4j
public abstract class AbstractValidateAnalysisListener<T> extends AnalysisEventListener<T> {

    /**
     * 应用标识
     */
    private String applicationMark;

    /**
     * 数据列表
     */
    private List<T> dataList;

    public AbstractValidateAnalysisListener(String applicationMark) {
        this.applicationMark = applicationMark;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        // 1. 校验注解内容
        ValidatorUtils.validateEntity(data);
        // 3. 对数据进行处理
        // 2. 应用id校验
        applicationValidate(data);
        invokeProcess(data, context);
    }

    /**
     * 应用校验
     *
     * @param data
     */
    private void applicationValidate(T data) {
        Class<? extends Object> tClass = data.getClass();
        Long id;
        try {
            Field field = tClass.getDeclaredField("id");
            field.setAccessible(true);
            id = (Long) field.get(data);
        } catch (Exception e) {
            throw new ApplicationException("反射获取id异常");
        }
        if (id > 400000000000L) {
            throw new ApplicationException("应用id校验出错");
        }
//        if (!id.toString().startsWith(applicationMark)) {
//            throw new ApplicationException("应用校验失败");
//        }
    }

    /**
     * 单条数据执行数据保存或其他处理
     *
     * @param data
     * @param context
     */
    public void invokeProcess(T data, AnalysisContext context) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(CollectionUtil.isEmpty(this.dataList)){
            return;
        }
        dataAnalysisBeforeHandler(this.dataList, context);
        doAfterDataAnalysis(this.dataList, context);
        dataAnalysisAfterHandler(this.dataList, context);
    }

    /**
     * 前置处理器
     *
     * @param dataList
     * @param context
     */
    public void dataAnalysisBeforeHandler(List<T> dataList, AnalysisContext context) {
    }

    /**
     * 后置处理器
     *
     * @param dataList
     * @param context
     */
    public void dataAnalysisAfterHandler(List<T> dataList, AnalysisContext context) {
    }

    /**
     * 所有数据处理方法
     *
     * @param dataList
     * @param context
     */
    public abstract void doAfterDataAnalysis(List<T> dataList, AnalysisContext context);

    /**
     * 异常前置处理起
     *
     * @param exception
     * @param context
     */
    public void exceptionBeforeHandler(Exception exception, AnalysisContext context) {
    }


    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

        exceptionBeforeHandler(exception, context);
        if (exception instanceof DataValidateException) {
            dataValidateExceptionProcess(exception, context);
        } else if (exception instanceof ApplicationException) {
            applicationExceptionProcess(exception, context);
        } else {
            onExceptionProcess(exception, context);
        }
    }

    /**
     * 其他异常处理
     *
     * @param exception
     * @param context
     */
    public void onExceptionProcess(Exception exception, AnalysisContext context) {
        // 其他异常
    }

    /**
     * 校验异常处理
     *
     * @param exception
     * @param context
     */
    public void dataValidateExceptionProcess(Exception exception, AnalysisContext context) {
        log.error("数据校验异常：[{}]", exception.getMessage());
    }

    /**
     * 应用校验异常
     *
     * @param exception
     * @param context
     */
    public void applicationExceptionProcess(Exception exception, AnalysisContext context) {
        log.error("应用校验异常：[]", exception.getMessage());
    }

}
