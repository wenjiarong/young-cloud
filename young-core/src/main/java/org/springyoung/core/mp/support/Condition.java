package org.springyoung.core.mp.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springyoung.core.tool.utils.Func;

public class Condition {

    public Condition() {
    }

    public static <T> IPage<T> getPage(Query query) {
        Page<T> page = new Page((long) Func.toInt(query.getCurrent(), 1), (long)Func.toInt(query.getSize(), 10));
        page.setAsc(Func.toStrArray(SqlKeyword.filter(query.getAscs())));
        page.setDesc(Func.toStrArray(SqlKeyword.filter(query.getDescs())));
        return page;
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return new QueryWrapper(entity);
    }

}