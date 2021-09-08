package com.drinks.milk.mongo.dao.insure;

import com.drinks.milk.mongo.base.dao.BaseMongoDaoImpl;
import com.drinks.milk.mongo.base.utils.GenericsUtils;
import com.drinks.milk.mongo.entity.insure.ChaosInsureOrder;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class ChaosInsureOrderDao extends BaseMongoDaoImpl<ChaosInsureOrder,String> {

    /**
     * @Description: 更新订单
     * @Param: entity 订单实体
     * @return: com.mongodb.client.result.UpdateResult
     * @Author: zhangrt
     * @Date: 2021/4/2 10:39
     */
    public UpdateResult updateOrder(ChaosInsureOrder entity) {
        if (entity == null) {
            return null;
        }
        Query query = new Query(Criteria.where("id").is(entity.getId()));
        Update update = GenericsUtils.getUpdateObj(entity);
        if (update == null) {
            return null;
        }
        Class<ChaosInsureOrder> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        return mongoTemplate.updateFirst(query, update, entityClass);
    }


    /**
     * @Description: 按条件批量更新
     * @Param: query 更新条件
     * @Param: update 更新结果
     * @return: com.mongodb.client.result.UpdateResult
     * @Author: zhangrt
     * @Date: 2021/4/2 10:40
     */
    public UpdateResult updateOrder(Query query, Update update) {
        if (update == null) {
            return null;
        }
        Class<ChaosInsureOrder> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        return mongoTemplate.updateMulti(query, update, entityClass);
    }

    /**
     * @Description: 查询订单实体
     * @Param: entity 订单实体
     * @return: com.mongodb.client.result.UpdateResult
     * @Author: zhangrt
     * @Date: 2021/4/2 10:39
     */
    public ChaosInsureOrder findById(String id) {
       //Query query = new Query(Criteria.where("id").is(id));
        Class<ChaosInsureOrder> entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        System.out.println("id是"+id);
        return mongoTemplate.findById(id,entityClass,"chaos_insure_order");
    }

}
