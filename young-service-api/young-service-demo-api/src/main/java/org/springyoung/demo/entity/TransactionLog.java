package org.springyoung.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springyoung.core.tenant.mp.TenantEntity;

@Data
@TableName("young_transaction_log")
public class TransactionLog extends TenantEntity {

    private String transactionId;

    private String remark;

}