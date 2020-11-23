package org.springyoung.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springyoung.core.tenant.mp.TenantEntity;

import java.util.Date;

@Data
@TableName("young_user")
public class User extends TenantEntity {

    // 用户状态：有效
    public static final String STATUS_VALID = "1";
    // 用户状态：锁定
    public static final String STATUS_LOCK = "0";
    // 默认头像
    public static final String DEFAULT_AVATAR = "default.jpg";
    // 默认密码
    public static final String DEFAULT_PASSWORD = "1234qwer";
    // 性别男
    public static final String SEX_MALE = "0";
    // 性别女
    public static final String SEX_FEMALE = "1";
    // 性别保密
    public static final String SEX_UNKNOW = "2";

    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "联系电话")
    private String mobile;
    @ApiModelProperty(value = "性别 0男 1女 2 保密")
    private String sex;
    @ApiModelProperty(value = "头像")
    private String image;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近访问时间")
    private Date lastLoginTime;
    @ApiModelProperty(value = "部门id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    @TableField(exist = false)
    private String deptName;
    @ApiModelProperty(value = "角色id,多个逗号隔开")
    @TableField(exist = false)
    private String roleIds;
    @ApiModelProperty(value = "角色名称,多个逗号隔开")
    @TableField(exist = false)
    private String roleNames;

}