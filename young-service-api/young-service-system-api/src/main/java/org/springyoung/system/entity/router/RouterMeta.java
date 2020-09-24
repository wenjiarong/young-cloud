package org.springyoung.system.entity.router;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建一个与之对应的meta对象
 * 因为权限是在后端控制的，所以去掉了role属性
 */
@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)表示，如果属性值是null的话，不参与序列化
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

    private static final long serialVersionUID = 5499925008927195914L;

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "图标")
    private String icon;

}