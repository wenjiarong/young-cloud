package org.springyoung.core.tenant.mp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建人")
    private Long createUser;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建部门")
    private Long createDept;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("更新人")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("业务状态")
    private Integer status;
    @TableLogic
    @ApiModelProperty("是否已删除")
    private Integer isDeleted;

    public BaseEntity() {
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$createUser = this.getCreateUser();
                Object other$createUser = other.getCreateUser();
                if (this$createUser == null) {
                    if (other$createUser != null) {
                        return false;
                    }
                } else if (!this$createUser.equals(other$createUser)) {
                    return false;
                }

                Object this$createDept = this.getCreateDept();
                Object other$createDept = other.getCreateDept();
                if (this$createDept == null) {
                    if (other$createDept != null) {
                        return false;
                    }
                } else if (!this$createDept.equals(other$createDept)) {
                    return false;
                }

                label110:
                {
                    Object this$createTime = this.getCreateTime();
                    Object other$createTime = other.getCreateTime();
                    if (this$createTime == null) {
                        if (other$createTime == null) {
                            break label110;
                        }
                    } else if (this$createTime.equals(other$createTime)) {
                        break label110;
                    }

                    return false;
                }

                Object this$updateUser = this.getUpdateUser();
                Object other$updateUser = other.getUpdateUser();
                if (this$updateUser == null) {
                    if (other$updateUser != null) {
                        return false;
                    }
                } else if (!this$updateUser.equals(other$updateUser)) {
                    return false;
                }

                label89:
                {
                    Object this$updateTime = this.getUpdateTime();
                    Object other$updateTime = other.getUpdateTime();
                    if (this$updateTime == null) {
                        if (other$updateTime == null) {
                            break label89;
                        }
                    } else if (this$updateTime.equals(other$updateTime)) {
                        break label89;
                    }

                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$isDeleted = this.getIsDeleted();
                Object other$isDeleted = other.getIsDeleted();
                if (this$isDeleted == null) {
                    if (other$isDeleted != null) {
                        return false;
                    }
                } else if (!this$isDeleted.equals(other$isDeleted)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseEntity;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $createUser = this.getCreateUser();
        result = result * 59 + ($createUser == null ? 43 : $createUser.hashCode());
        Object $createDept = this.getCreateDept();
        result = result * 59 + ($createDept == null ? 43 : $createDept.hashCode());
        Object $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());
        Object $updateUser = this.getUpdateUser();
        result = result * 59 + ($updateUser == null ? 43 : $updateUser.hashCode());
        Object $updateTime = this.getUpdateTime();
        result = result * 59 + ($updateTime == null ? 43 : $updateTime.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $isDeleted = this.getIsDeleted();
        result = result * 59 + ($isDeleted == null ? 43 : $isDeleted.hashCode());
        return result;
    }

    public String toString() {
        return "BaseEntity(id=" + this.getId() + ", createUser=" + this.getCreateUser() + ", createDept=" + this.getCreateDept() + ", createTime=" + this.getCreateTime() + ", updateUser=" + this.getUpdateUser() + ", updateTime=" + this.getUpdateTime() + ", status=" + this.getStatus() + ", isDeleted=" + this.getIsDeleted() + ")";
    }

}
