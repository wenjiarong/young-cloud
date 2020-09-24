package org.springyoung.core.tenant.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TenantEntity extends BaseEntity {

    @ApiModelProperty("租户ID")
    private String tenantId;

    public TenantEntity() {
    }

    public String toString() {
        return "TenantEntity(tenantId=" + this.getTenantId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TenantEntity)) {
            return false;
        } else {
            TenantEntity other = (TenantEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$tenantId = this.getTenantId();
                Object other$tenantId = other.getTenantId();
                if (this$tenantId == null) {
                    if (other$tenantId != null) {
                        return false;
                    }
                } else if (!this$tenantId.equals(other$tenantId)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TenantEntity;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = super.hashCode();
        Object $tenantId = this.getTenantId();
        result = result * 59 + ($tenantId == null ? 43 : $tenantId.hashCode());
        return result;
    }

}