package org.springyoung.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springyoung.core.mp.base.BaseEntity;

/**
 * 文件服务器信息表实体类
 */
@Data
@TableName("young_file_server")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FileServer对象", description = "文件服务器信息表")
public class FileServer extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "文件服务器地址")
	@TableField("IP")
	private String ip;

	@ApiModelProperty(value = "服务器名称")
	private String name;

	@JsonSerialize(using = ToStringSerializer.class)
	private Integer port;

	@ApiModelProperty(value = "文件传输协议类型 1: tftp 2: http 3:ftp")
	private String protocol;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "传输文件源名称路径")
	private String sourcePath;

	@ApiModelProperty(value = "传输文件目的名称路径")
	private String destinationPath;

	@ApiModelProperty(value = "备注")
	private String remark;

}
