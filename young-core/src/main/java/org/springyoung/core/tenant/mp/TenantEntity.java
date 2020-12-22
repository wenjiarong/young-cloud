package org.springyoung.core.tenant.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springyoung.core.mp.base.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    @ApiModelProperty("租户ID")
    private String tenantId;

}