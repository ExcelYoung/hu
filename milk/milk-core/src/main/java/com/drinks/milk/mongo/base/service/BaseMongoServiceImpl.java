package com.drinks.milk.mongo.base.service;

import com.drinks.milk.mongo.base.dao.BaseMongoDaoImpl;
import com.drinks.milk.mongo.base.page.Pagination;
import com.github.pagehelper.PageInfo;

import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * mongodb,service层封装
 *
 * @author zhangrt
 * @since V1.0
 */
public abstract class BaseMongoServiceImpl<T, ID extends Serializable, D extends BaseMongoDaoImpl<T, ID>> implements BaseMongoService<T, ID, D> {

    private static Logger logger = LoggerFactory.getLogger(BaseMongoServiceImpl.class.getName());

    @Autowired
    protected D dao;

    @SuppressWarnings("unchecked")
    @PostConstruct
    protected void init() {
    }

    public D getDao() {
        return dao;
    }

    @Override
    public T save(T entity) {
        return dao.save(entity);
    }

    @Override
    public T findById(ID id) {
        return dao.findById(id);
    }

    @Override
    public T findById(ID id, String collectionName) {
        return dao.findById(id, collectionName);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public List<T> findAll(String collectionName) {
        return dao.findAll(collectionName);
    }

    @Override
    public List<T> find(Query query) {
        return dao.find(query);
    }

    @Override
    public T findOne(Query query) {
        return dao.findOne(query);
    }

    @Override
    public long count(Query query) {
        return dao.count(query);
    }

    @Override
    public UpdateResult update(Query query, Update update) {
        return dao.update(query, update);
    }

    @Override
    public T updateOne(Query query, Update update) {
        return dao.updateOne(query, update);
    }

    @Override
    public UpdateResult update(T entity) {
        return dao.update(entity);
    }

    @Override
    public T findAndModify(Query query, Update update, FindAndModifyOptions fmo) {
        return dao.findAndModify(query, update, fmo);
    }

    @Override
    public void remove(Query query) {
        dao.remove(query);
    }

    @Override
    public void remove(ID id) {
        dao.remove(id);
    }

    /**
     * 分页查询
     *
     * @param query：查询对象
     * @param page       ： 分页对象
     *                   sort：排序字符串.格式为“排序字段 [排序方式]”排序方式为"asc/desc". 不指定排序方式时，默认为"asc".下面三种形式均可
     *                   1：username
     *                   2：username asc
     *                   3：username desc
     *                   支持多个字段的排序，多个间用逗号(","): 例：   field1 desc , field2 asc, field3
     * @return
     */
    @Override
    public PageInfo<T> page(Query query, Pagination<T> page) {
        //如果没有条件 则所有全部
        query = query == null ? new Query(Criteria.where("_id").exists(true)) : query;
        long count = dao.count(query);
        // 总数
        page.setTotal((int) count);
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        page.setPages((int) ((page.getTotal() / page.getPageSize()) + 1));

        //向Query查询对应中添加排序
        if (StringUtils.isNotBlank(page.getSort())) {
            String[] sorts = page.getSort().split(",");
            for (String itemSort : sorts) {
                itemSort = itemSort.trim();
                String[] item = itemSort.split(" ");
                if (item.length == 1) {
                    query.with(new Sort(Sort.Direction.ASC, item[0]));
                } else if (item.length == 2) {
                    if (item[1].toUpperCase().equals(Sort.Direction.ASC.name())) {
                        query.with(new Sort(Sort.Direction.ASC, item[0]));
                    } else if (item[1].toUpperCase().equals(Sort.Direction.DESC.name())) {
                        query.with(new Sort(Sort.Direction.DESC, item[0]));
                    }
                }

            }
        }
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        List<T> rows = dao.find(query);
        page.addAll(rows);
        return new PageInfo<T>(page);
    }
}
