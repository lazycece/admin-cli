package com.lazycece.admin.server.mongodb;

import com.lazycece.admin.server.mongodb.exception.QueryException;
import com.mongodb.WriteConcern;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;

/**
 * User: lanjian
 * Email:lanjian@i5c6c.com
 * Version:1.0
 */
public class Mongo extends MongoQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mongo.class);

    public static final String ID = "_id";

    private String prefix = null;

    private String suffix = null;

    private MongoTemplate mongoOperations;


    public Mongo(MongoTemplate mongoOperations) {
        super();
        this.mongoOperations = mongoOperations;
    }

    public Mongo safe() {
        mongoOperations.setWriteConcern(WriteConcern.SAFE);
        return this;
    }

    public Mongo fileds(String... fileds) {
        for (String field : fileds) {
            field(field);
        }
        return this;
    }

    public Mongo prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Mongo suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public Mongo buildCriteria(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public List geoFind(Double lat, Double lng, Class cls) {
        NearQuery nearQuery = NearQuery.near(lng, lat, Metrics.KILOMETERS);
        nearQuery.query(query);
        nearQuery.spherical(true);
        nearQuery.num(query.getSkip() + query.getLimit());
        GeoResults geoResults = mongoOperations.geoNear(nearQuery, cls, collectionName(cls));
        List<GeoResult> results = geoResults.getContent();
        List list = new ArrayList();
        for (GeoResult g : results) {
            list.add(g.getContent());
        }
        return list;
    }


    public Mongo multiEq(String property, String... values) {
        Criteria[] criterias = new Criteria[values.length];
        int index = 0;
        for (String str : values) {
            criterias[index] = Criteria.where(property).is(str);
            index++;
        }
        criteria.andOperator(criterias);
        return this;
    }


    public static Mongo build(MongoTemplate source) {
        return new Mongo(source);
    }


    public Mongo mySelf(IMyselfQuery myself) {
        myself.query(criteria);
        return this;
    }

    public Mongo eq(String key, Object value) {
        and(key).is(value);
        return this;
    }

    public Mongo fuzzy(String key, Object value) {
        String re = String.valueOf(value);
        re = re.replace("(", "\\(").replace(")", "\\)");
        and(key).regex(re, "i");
        return this;
    }


    public Mongo ne(String key, Object value) {
        and(key).ne(value);
        return this;
    }

    public Mongo or(String key, Object... value) {
        push(OP.OR, genOrCriteria(key, value));
        return this;
    }

    public Mongo or(String[] key, Object... value) {
        push(OP.OR, genOrCriteria(key, value));
        return this;
    }

    public Mongo or(String[] key, Object[] value, Operator[] operators) {
        push(OP.OR, genOrCriteria(key, value, operators));
        return this;
    }

    public Mongo nor(String key, Object... value) {
        push(OP.NOR, genOrCriteria(key, value));
        return this;
    }

    public Mongo nor(String[] key, Object... value) {
        push(OP.NOR, genOrCriteria(key, value));
        return this;
    }

    public Mongo nor(String[] key, Object[] value, Operator[] operators) {
        push(OP.NOR, genOrCriteria(key, value, operators));
        return this;
    }

    public Mongo and(String key, Object... value) {
        push(OP.AND, genOrCriteria(key, value));
        return this;
    }

    public Mongo and(String[] key, Object... value) {
        push(OP.AND, genOrCriteria(key, value));
        return this;
    }

    public Mongo and(String[] key, Object[] value, Operator[] operators) {
        push(OP.AND, genOrCriteria(key, value, operators));
        return this;
    }

    private void push(OP op, List<Criteria> criteriaList) {
        List<Criteria> criterias = map.get(op);
        if (criterias == null) {
            criterias = new ArrayList<Criteria>();
            map.put(op, criterias);
        }
        criterias.addAll(criteriaList);
    }


    public void init() {
        Set<OP> ops = map.keySet();
        for (OP op : ops) {
            List<Criteria> criterias = map.get(op);
            if (CollectionUtils.isNotEmpty(criterias)) {
                op.op(criteria, criterias.toArray(new Criteria[]{}));
            }
        }
    }

    private enum OP {
        OR, AND, NOR;

        public void op(Criteria criteria, Criteria[] criterias) {
            switch (this) {
                case OR:
                    criteria.orOperator(criterias);
                    return;
                case AND:
                    criteria.andOperator(criterias);
                    return;
                case NOR:
                    criteria.norOperator(criterias);
                    return;
                default:
                    throw new RuntimeException("not support");
            }
        }
    }

    private Map<OP, List<Criteria>> map = new HashMap<OP, List<Criteria>>();

    private List<Criteria> genOrCriteria(String key, Object[] value) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        for (int i = 0; i < value.length; i++) {
            criterias.add(Criteria.where(key).is(value[i]));
        }
        return criterias;
    }

    private List<Criteria> genOrCriteria(String[] key, Object[] value) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        for (int i = 0; i < value.length; i++) {
            criterias.add(Criteria.where(key[i]).is(value[i]));
        }
        return criterias;
    }

    private List<Criteria> genOrCriteria(String[] key, Object[] value, Operator[] operators) {
        List<Criteria> criterias = new ArrayList<Criteria>();
        for (int i = 0; i < value.length; i++) {
            if (Operator.in.equals(operators[i])) {
                Object[] tmp = (Object[]) value[i];
                criterias.add(Criteria.where(key[i]).in(tmp));
            }
            if (Operator.nin.equals(operators[i])) {
                Object[] tmp = (Object[]) value[i];
                criterias.add(Criteria.where(key[i]).nin(tmp));
            }
            if (Operator.regex.equals(operators[i])) {
                String re = String.valueOf(value[i]);
                re = re.replace("(", "\\(").replace(")", "\\)");
                criterias.add(Criteria.where(key[i]).regex(re, "i"));
            }
            if (Operator.eq.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).is(value[i]));
            }
            if (Operator.gt.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).gt(value[i]));
            }
            if (Operator.lt.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).lt(value[i]));
            }
            if (Operator.gte.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).gte(value[i]));
            }
            if (Operator.lte.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).lte(value[i]));
            }
            if (Operator.ne.equals(operators[i])) {
                criterias.add(Criteria.where(key[i]).ne(value[i]));
            }
        }
        return criterias;
    }

    public Mongo gte(String key, Object value) {
        and(key).gte(value);
        return this;
    }

    public Mongo gt(String key, Object value) {
        and(key).gt(value);
        return this;
    }

    public Mongo lt(String key, Object value) {
        and(key).lt(value);
        return this;
    }

    public Mongo lte(String key, Object value) {
        and(key).lte(value);
        return this;
    }


    public Mongo in(String key, Object... value) {
        and(key).in(value);
        return this;
    }

    public Mongo nin(String key, Object... value) {
        and(key).nin(value);
        return this;
    }

    public Mongo size(String key, int size) {
        and(key).size(size);
        return this;
    }

    public Mongo exists(String key, boolean flag) {
        and(key).exists(flag);
        return this;
    }

    public Mongo limit(int limit, int page, int skip) {
        if (page < 1) {
            throw new RuntimeException("page is invalid ...");
        }
        query.limit(limit);
        query.skip(skip + ((page - 1) * limit));
        return this;
    }

    public Mongo limit(int limit, int page) {
        limit(limit, page, 0);
        return this;
    }

    public Mongo skip(int limit, int skip) {
        if (skip < 0) {
            throw new RuntimeException("skip is invalid ...");
        }
        query.limit(limit);
        query.skip(skip);
        return this;
    }

    public Mongo desc(String... properties) {
        query.with(new Sort(Sort.Direction.DESC, properties));
        return this;
    }

    public Mongo asc(String... properties) {
        query.with(new Sort(Sort.Direction.ASC, properties));
        return this;
    }

    public Mongo type(String key, int type) {
        and(key).type(type);
        return this;
    }

    public <T> T load(String id, Class<T> clazz) {
        return eq(ID, id).one(clazz);
    }

    public enum MongoType {
        UPDATE, INSERT
    }

    private String collectionName(Object obj) {
        Class clazz = obj.getClass();
        return collectionName(clazz);
    }

    private String collectionName(Class clazz) {
        String collectionName = null;
        if (clazz.isAnnotationPresent(MongoCollection.class)) {
            MongoCollection table = (MongoCollection) clazz.getAnnotation(MongoCollection.class);
            collectionName = table.value();
        } else {
            collectionName = clazz.getSimpleName();
        }
        StringBuffer cn = new StringBuffer();
        if (StringUtils.isNotBlank(prefix)) {
            cn.append(prefix);
        }
        cn.append(collectionName);
        if (StringUtils.isNotBlank(suffix)) {
            cn.append(suffix);
        }
        return cn.toString();
    }

    public MongoType insert(IMongoUpdate update, Object obj) {
        if (count(obj.getClass()) > 0) {
            if (update != null) {
                updateFirst(update, obj.getClass());
            }
            return MongoType.UPDATE;
        } else {
            insert(obj);
            return MongoType.INSERT;
        }
    }

    public void save(Object obj) {
        mongoOperations.save(obj, collectionName(obj));
    }

    public UpdateResult upsert(IMongoUpdate update, Class clazz) {
        Update _update = new Update();
        update.update(_update);
        init();
        return mongoOperations.upsert(query, _update, collectionName(clazz));
    }


    public UpdateResult updateMulti(IMongoUpdate update, Class clazz) {
        Update _update = new Update();
        update.update(_update);
        init();
        return mongoOperations.updateMulti(query, _update, collectionName(clazz));
    }


    public UpdateResult updateFirst(IMongoUpdate update, Class clazz) {
        Update _update = new Update();
        update.update(_update);
        init();
        return mongoOperations.updateFirst(query, _update, collectionName(clazz));
    }

    public void findAndModify(IMongoUpdate update, Class clazz) {
        Update _update = new Update();
        update.update(_update);
        init();
        mongoOperations.findAndModify(query, _update, clazz, collectionName(clazz));
    }


    public void remove(Class cls) {
        init();
        mongoOperations.remove(query, collectionName(cls));
    }


    public long count(Class clazz) {
        init();
        return mongoOperations.count(query, collectionName(clazz));
    }

    public List list(Class clazz) {
        init();
        return mongoOperations.find(query, clazz, collectionName(clazz));
    }

    public <T> T findAndUpdate(Class clazz, IMongoUpdate update) {
        Update _update = new Update();
        update.update(_update);
        init();
        return (T) mongoOperations.findAndModify(query, _update, clazz, collectionName(clazz));
    }

    public <T> T findAndUpdate(Class clazz, IMongoUpdate update, boolean upsert, boolean returnNew) {
        Update _update = new Update();
        update.update(_update);
        init();
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(upsert).returnNew(returnNew);
        return (T) mongoOperations.findAndModify(query, _update, options, clazz, collectionName(clazz));
    }


    public <T> T one(Class clazz) {
        init();
        return (T) mongoOperations.findOne(query, clazz, collectionName(clazz));
    }

//    public CommandResult executeCommand(String json) {
//        return mongoOperations.executeCommand(json);
//    }

    public void insert(Object obj) {
        mongoOperations.insert(obj, collectionName(obj));
    }

    public void insert(Object obj, String collectionName) {
        mongoOperations.insert(obj, collectionName);
    }

    public void insertBatch(List objs, Class clazz) {
        mongoOperations.insert(objs, collectionName(clazz));
    }

    public List all(Class clazz) {
        return mongoOperations.findAll(clazz, collectionName(clazz));
    }

//    public void ensureIndex(String name, Order order, Class cls) {
//        DBObject dbObject = new BasicDBObject();
//        dbObject.put(name, order.getOrderValue());
//        mongoOperations.getCollection(collectionName(cls)).createIndex(dbObject);
//    }
//
//    public void ensureIndex2D(String name, Class cls) {
//        DBObject dbObject = new BasicDBObject();
//        dbObject.put(name, "2d");
//        mongoOperations.getCollection(collectionName(cls)).createIndex(dbObject);
//    }


    public Mongo between(String key, Object begin, Object end, Between between) {
        between.between(and(key), begin, end);
        return this;
    }

    public enum Order {
        desc(-1), asc(1);

        private int orderValue;

        Order(int orderValue) {
            this.orderValue = orderValue;
        }

        public int getOrderValue() {
            return orderValue;
        }
    }

    public enum Between {
        EQ, NEQ, FEQ, EEQ;

        public void between(Criteria criteria, Object begin, Object end) {
            switch (this) {
                case EQ:
                    criteria.lte(end).gte(begin);
                    break;
                case NEQ:
                    criteria.lt(end).gt(begin);
                    break;
                case FEQ:
                    criteria.lt(end).gte(begin);
                    break;
                case EEQ:
                    criteria.lte(end).gt(begin);
                    break;
                default:
                    throw new QueryException("no Between enum");
            }
        }
    }
}