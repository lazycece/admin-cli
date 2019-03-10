package com.lazycece.admin.server.mongodb;

import org.springframework.data.mongodb.core.query.Update;

/**
 * User: lanjian
 */
public interface IMongoUpdate {
    void update(Update update);
}
