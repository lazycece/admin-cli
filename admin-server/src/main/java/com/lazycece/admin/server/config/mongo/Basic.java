package com.lazycece.admin.server.config.mongo;

import com.lazycece.admin.common.core.Status;
import com.lazycece.admin.common.utils.UUIDUtils;
import lombok.Data;

/**
 * @author lazycece
 */
@Data
public class Basic {
    /**
     * 数据唯一ID
     */
    private String uuid = UUIDUtils.uuid();
    private Long createTime = System.currentTimeMillis() / 1000;
    private Long updateTime = System.currentTimeMillis() / 1000;
    private Status status = Status.active;
    private String operator;
}
