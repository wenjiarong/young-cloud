package org.springyoung.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springyoung.core.tenant.mp.TenantEntity;

/**
 * 购物日志表
 */
@Data
@TableName("young_trade_log")
public class TradeLog extends TenantEntity {

    private String goodsId;

    private String goodsName;

}
