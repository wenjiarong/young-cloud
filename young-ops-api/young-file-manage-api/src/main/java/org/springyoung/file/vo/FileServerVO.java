package org.springyoung.file.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 文件服务器信息表实体类
 */
@Data
@ApiModel(value = "FileServerVO对象", description = "FileServerVO对象")
public class FileServerVO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String protocol;

}
