package org.springyoung.canal.annotation.table;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.core.annotation.AliasFor;
import org.springyoung.canal.annotation.ListenPoint;

import java.lang.annotation.*;

/**
 * 表结构发生变化，新增时，先判断数据库实例是否存在，不存在则创建
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ListenPoint(eventType = CanalEntry.EventType.CREATE)
public @interface CreateTableListenPoint {
	
	/**
	 * canal 指令
	 * default for all
	 *
	 */
	@AliasFor(annotation = ListenPoint.class)
	String destination() default "";
	
	/**
	 * 数据库实例
	 *
	 */
	@AliasFor(annotation = ListenPoint.class)
	String[] schema() default {};
}
