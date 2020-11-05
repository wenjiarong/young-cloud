package org.springyoung.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springyoung.core.tool.utils.Func;

public class Condition {

    /**
     * 转化成mybatis plus中的Page
     *
     * @param query 查询条件
     * @return IPage
     */
    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
        page.setAsc(Func.toStrArray(SqlKeyword.filter(query.getAscs())));
        page.setDesc(Func.toStrArray(SqlKeyword.filter(query.getDescs())));
        return page;
    }

    /**
     * 获取mybatis plus中的QueryWrapper
     *
     * @param entity 实体
     * @param <T>    类型
     * @return QueryWrapper
     */
    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper<>(entity);
    }

}