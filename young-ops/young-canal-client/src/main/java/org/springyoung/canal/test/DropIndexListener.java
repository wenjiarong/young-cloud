package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.table.DropIndexListenPoint;

/**
 * @ClassName: DropIndexListener
 * @Author: 温家荣-wjr
 * @Date: 2020/5/30 21:29
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class DropIndexListener {

    @DropIndexListenPoint
    public void onEventDropIndex(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（删除索引操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

}
