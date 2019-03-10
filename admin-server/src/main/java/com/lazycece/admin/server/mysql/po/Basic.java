package com.lazycece.admin.server.mysql.po;

import com.lazycece.admin.common.core.Status;
import lombok.Data;

import java.util.Date;

/**
 * @author lazycece
 */
@Data
public class Basic {
    private Long id;
    private Date createTime = new Date();
    private Date updateTime = new Date();
    private Status status = Status.active;
    private String operator;
}
