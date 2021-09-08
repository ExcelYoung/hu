package com.drinks.milk.mongo.base.dao;

import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.List;

/**
 * mongodb,dao层封装
 *
 */
public interface BaseMongoDao<T, ID extends Serializable> {
    /**
     * 插入
     */
    T save(T entity);

    /**
     * 根据ID查询
     */
    T findById(ID id);

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     */
    T findById(ID id, String collectionName);

    /**
     * 获得所有该类型记录
     */
    List<T> findAll();

    /**
     * 获得所有该类型记录,并且指定了集合名(表的意思)
     */
    List<T> findAll(String collectionName);

    /**
     * 根据条件查询
     */
    List<T> find(Query query);

    /**
     * 根据条件查询一个
     */
    T findOne(Query query);

    /**
     * 根据条件 获得总数
     */
    long count(Query query);

    /**
     * 根据条件 更新
     */
    UpdateResult update(Query query, Update update);

    /**
     * 更新符合条件并sort之后的第一个文档 并返回更新后的文档
     */
    T updateOne(Query query, Update update);

    /**
     * 查询并更新符合条件的文档 并返回更新后的文档
     */
    T findAndModify(Query query, Update update, FindAndModifyOptions fmo);

    /**
     * 根据传入实体ID更新
     */
    UpdateResult update(T entity);

    /**
     * 根据条件 删除
     */
    void remove(Query query);

    /**
     * 根据主键ID删除
     */
    void remove(ID id);
}