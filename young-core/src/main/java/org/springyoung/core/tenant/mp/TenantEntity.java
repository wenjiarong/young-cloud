package org.springyoung.core.tenant.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantEntity extends BaseEntity {

    @ApiModelProperty("租户ID")
    private String tenantId;

}