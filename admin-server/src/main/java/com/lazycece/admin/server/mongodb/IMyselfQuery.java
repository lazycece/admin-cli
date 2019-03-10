package com.lazycece.admin.server.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * User: lanjian
 */
public interface IMyselfQuery {
    void query(Criteria criteria);
}
