package org.springyoung.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springyoung.core.tenant.mp.TenantEntity;

/**
 * 文件信息表实体类
 */
@Data
@TableName("young_file_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FileInfo对象", description = "文件信息表")
public class FileInfo extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件服务器编号
     */
    @ApiModelProperty(value = "文件服务器编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileServerId;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
