package org.springyoung.jpush.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SendMsg
 * @Description TODO
 * @Author 小温
 * @Date 2020/6/1 8:52
 * @Version 1.0
 */
@Data
public class SendMsg {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "数据")
    private String data;

}
