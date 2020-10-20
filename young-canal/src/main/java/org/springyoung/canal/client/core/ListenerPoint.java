package org.springyoung.canal.client.core;



import org.springyoung.canal.annotation.ListenPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听 canal 操作
 *
 */
public class ListenerPoint {
    /**
     * 目标
     */
    private Object target;
    
    /**
     * 监听的方法和节点
     */
    private Map<Method, ListenPoint> invokeMap = new HashMap<>();
    
    /**
     * 构造方法，设置目标，方法以及注解类型
     *
     */
    ListenerPoint(Object target, Method method, ListenPoint anno) {
        this.target = target;
        this.invokeMap.put(method, anno);
    }
    
    /**
     * 返回目标类
     *
     */
    public Object getTarget() {
        return target;
    }
    
    /**
     * 获取监听的操作方法和节点
     *
     */
    public Map<Method, ListenPoint> getInvokeMap() {
        return invokeMap;
    }
}
