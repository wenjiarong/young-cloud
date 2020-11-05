package org.springyoung.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springyoung.core.tenant.mp.TenantEntity;

@Data
@TableName("young_transaction_log")
public class TransactionLog extends TenantEntity {

    @ApiModelProperty(value = "事务id")
    private String transactionId;

    @ApiModelProperty(value = "备注")
    private String remark;

}