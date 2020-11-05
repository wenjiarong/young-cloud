package org.springyoung.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springyoung.core.tenant.mp.TenantEntity;

/**
 * 购物日志表
 */
@Data
@TableName("young_trade_log")
public class TradeLog extends TenantEntity {

    @ApiModelProperty(value = "商品id")
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

}
