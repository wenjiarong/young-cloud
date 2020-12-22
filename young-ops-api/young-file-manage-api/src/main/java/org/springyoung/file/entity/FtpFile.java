package org.springyoung.file.entity;

import lombok.Data;

/**
 * @ClassName FtpFile
 * @Description TODO
 * @Author 小温
 * @Date 2020/12/11 9:56
 * @Version 1.0
 */
@Data
public class FtpFile {

	private String fileName;

	//0:文件夹  1：文件
	private Integer type;

}
