package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.table.AlertTableListenPoint;

/**
 * @ClassName: AlertTableListener
 * @Author: 小温
 * @Date: 2020/5/30 21:04
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class AlertTableListener {

    @AlertTableListenPoint
    public void onEventAlertTable(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（修改表信息操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

}
