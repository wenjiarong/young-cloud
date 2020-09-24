package org.springyoung.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springyoung.core.tenant.mp.TenantEntity;

@Data
@TableName("young_menu")
public class Menu extends TenantEntity {

    // 菜单
    public static final String TYPE_MENU = "0";
    // 按钮
    public static final String TYPE_BUTTON = "1";

    @ApiModelProperty(value = "上级菜单id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    @ApiModelProperty(value = "菜单/按钮名称")
    private String menuName;
    @ApiModelProperty(value = "菜单url")
    private String path;
    @ApiModelProperty(value = "对应 Vue组件")
    private String component;
    @ApiModelProperty(value = "权限标识")
    private String perms;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private String type;
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

}