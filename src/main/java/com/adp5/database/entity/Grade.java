package com.adp5.database.entity;

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
public class Grade {

    @NotNull
    private Long id;

    @NotEmpty
    @Min(value = 3)
    @Max(value = 3)
    private String name;

    private Date createTime;

    private Date updateTime;
}
