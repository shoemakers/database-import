package com.adp5.database.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: hongyuliu
 * @Date: 2020-05-13
 * @Version 1.0
 */
@Data
public class Book {

    @NotNull(message = "图书id不能为空")
    @ExcelProperty(index = 0)
    private Long id;

    @NotEmpty(message = "图书名称不能为空")
    @Min(value = 3, message = "图书名称不能小于3个字符")
    @Max(value = 4, message = "图书名称不能大于4个字符")
    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private Date createTime;

    @ExcelProperty(index = 3)
    private Date updateTime;
}
