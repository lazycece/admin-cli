package com.lazycece.admin.server.mongodb;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by lanjian on 14-8-28.
 */
public abstract class MongoQuery {
    protected Query query;

    protected Criteria criteria;

    protected Field field;

    public MongoQuery() {
        criteria = new Criteria();
        query = Query.query(criteria);
        field = query.fields();
    }


    protected void field(String key) {
        field.include(key);
    }

    protected Criteria and(String key) {
        return criteria.and(key);
    }

}
