package org.springyoung.canal.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springyoung.canal.annotation.CanalEventListener;
import org.springyoung.canal.annotation.table.DropIndexListenPoint;

/**
 * @ClassName: RenameTableListener
 * @Author: 小温
 * @Date: 2020/5/30 21:31
 * @Version: 1.0
 */
@CanalEventListener
@Slf4j
public class RenameTableListener {

    @DropIndexListenPoint
    public void onEventRenameTable(CanalEntry.RowChange rowChange) {
        log.info("======================注解方式（修改表名称操作）==========================");
        log.info("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
        log.info("\n======================================================");
    }

}
