package com.drinks.milk.mongo.base.dao;

import com.drinks.milk.mongo.base.utils.GenericsUtils;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongodb,dao层封装
 *
 * @author zhangrt
 * @since V1.0
 */
public abstract class BaseMongoDaoImpl<T, ID extends Serializable> implements BaseMongoDao<T, ID> {

    private Class<T> entityClass;
    /**
     * spring mongodb　集成操作类
     */
    protected MongoTemplate mongoTemplate;

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void init() {
        entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
    }

    /**
     * 注入mongodbTemplate
     *
     * @param mongoTemplate
     */
    @Autowired
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public T findById(ID id) {
        return mongoTemplate.findById(id, entityClass);
    }

    @Override
    public T findById(ID id, String collectionName) {
        return mongoTemplate.findById(id, entityClass, collectionName);
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(entityClass);
    }

    @Override
    public List<T> findAll(String collectionName) {
        return mongoTemplate.findAll(entityClass, collectionName);
    }

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, entityClass);
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, entityClass);
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, entityClass);
    }

    @Override
    public UpdateResult update(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return mongoTemplate.updateMulti(query, update, entityClass);
    }

    @Override
    public T updateOne(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return findAndModify(query, update, new FindAndModifyOptions().returnNew(true));
    }

    @Override
    public T findAndModify(Query query, Update update, FindAndModifyOptions fmo) {
        return mongoTemplate.findAndModify(query, update, fmo, entityClass);
    }

    @Override
    public UpdateResult update(T entity) {
        //Field[] fields = entityClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = entityClass;
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        if (fieldList == null || fieldList.size() <= 0) {
            return null;
        }
        Field idField = null;
        // 查找ID的field
        for (Field field : fieldList) {
            if (field.getAnnotation(Id.class) != null) {
                idField = field;
                break;
            }
        }
        if (idField == null) {
            return null;
        }
        idField.setAccessible(true);
        Object id = null;
        try {
            id = idField.get(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (id instanceof String) {
            if (id == null || "".equals(((String) id).trim())) {
                return null;
            }
        } else {
            if (id == null) {
                return null;
            }
        }

        // 根据ID更新
        Query query = new Query(Criteria.where(idField.getName()).is(id));
        Update update = GenericsUtils.getUpdateObj(entity);
        if (update == null) {
            return null;
        }
        return mongoTemplate.updateFirst(query, update, entityClass);
    }

    @Override
    public void remove(Query query) {
        mongoTemplate.remove(query, entityClass);
    }

    @Override
    public void remove(ID id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, entityClass);
    }

}
