package org.springyoung.system.entity.router;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  创建路由对象VueRouter
 * @param <T>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter<T> implements Serializable {

    private static final long serialVersionUID = -3327478146308500708L;

    @ApiModelProperty(value = "唯一标识")
    @JsonIgnore
    private String id;
    @ApiModelProperty(value = "父标识")
    @JsonIgnore
    private String parentId;
    @ApiModelProperty(value = "对应路由path")
    private String path;
    @ApiModelProperty(value = "路由名称")
    private String name;
    @ApiModelProperty(value = "对应路由组件component")
    private String component;
    @ApiModelProperty(value = "重定向地址")
    private String redirect;
    @ApiModelProperty(value = "路由元信息")
    private RouterMeta meta;
    @ApiModelProperty(value = "是否渲染在菜单上")
    private Boolean hidden = false;
    @ApiModelProperty(value = "是否一直显示根路由")
    private Boolean alwaysShow = false;
    @ApiModelProperty(value = "子路由")
    private List<VueRouter<T>> children;
    @ApiModelProperty(value = "是否有父级")
    @JsonIgnore
    private Boolean hasParent = false;
    @ApiModelProperty(value = "是否有儿子")
    @JsonIgnore
    private Boolean hasChildren = false;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

}