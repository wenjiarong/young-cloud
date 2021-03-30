package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.table.CreateIndexListenPoint;
import org.springyoung.canal.client.core.CanalMsg;

/**
 * @ClassName: CreateIndexListener
 * @Author: 小温
 * @Date: 2020/5/30 21:05
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class CreateIndexListener {

    @CreateIndexListenPoint
    public void onEventCreateIndex(CanalMsg canalMsg, CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（创建索引操作）==========================");
        log.info("use " + canalMsg.getSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

}
