package com.drinks.milk.mongo.base.page;


import com.github.pagehelper.Page;

/**
 * @Description:
 * @Author: zhangrt
 * @Date: 2021-03-25 14:14
 **/
public class Pagination<E> extends Page<E> {

    private String sort;

    public Pagination() {
    }

    public Pagination(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public Pagination(int pageNum, int pageSize, String sort) {
        super(pageNum, pageSize);
        this.sort = sort;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
