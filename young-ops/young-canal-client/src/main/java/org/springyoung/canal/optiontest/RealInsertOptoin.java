package org.springyoung.canal.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springyoung.canal.client.abstracts.option.content.InsertOption;

import java.util.List;

/**
 * 实现的新增处理机制
 */
@Component
@Slf4j
public class RealInsertOptoin extends InsertOption {

    /**
     * 新增数据操作
     *
     * @param destination 指令
     * @param schemaName  实例名称
     * @param tableName   表名称
     * @param rowChange   数据
     */
    @Override
    public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
        log.info("======================接口方式（新增数据操作）==========================");
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDatasList) {
            String sql = "use " + schemaName + ";\n";
            StringBuffer colums = new StringBuffer();
            StringBuffer values = new StringBuffer();
            rowData.getAfterColumnsList().forEach((c) -> {
                colums.append(c.getName() + ",");
                values.append("'" + c.getValue() + "',");
            });
            sql += "INSERT INTO " + tableName + "(" + colums.substring(0, colums.length() - 1) + ") VALUES(" + values.substring(0, values.length() - 1) + ");";
            log.info(sql);
        }
        log.info("\n======================================================");
    }

}
