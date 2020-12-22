package org.springyoung.file.handler.code;

import org.springyoung.core.tool.api.IResultCode;

/**
 * @ClassName AppResultCode
 * @Description APP错误返回码
 * @Author 小温
 * @Date 2020/5/14 15:23
 * @Version 1.0
 */
public enum ErrorCode implements IResultCode {

    THE_INPUT_IP_IS_NOT_RECOGNIZED(19000, "输入IP无法识别"),
    CONNECTION_FAILED(19001, "连接失败"),
    THE_DIRECTORY_OR_FILE_TO_DOWNLOAD_DOES_NOT_EXIST(19002, "需要下载的目录或文件不存在"),
    INVALID_COMMAND(19003, "无效的命令"),
    FILE_SERVER_ERROR_PLEASE_TRY_AGAIN_LATER(19004, "文件服务器出错，请稍后再试"),
    THE_CONNECTED_SERVER_DIRECTORY_OR_FILE_DOES_NOT_EXIST(19005, "连接的服务器目录或文件不存在"),
    THE_USER_DOES_NOT_EXIST_AND_CANNOT_BE_DELETED(19006, "用户不存在，无法删除"),
    THE_ATTACHED_SERVER_DOES_NOT_SUPPORT_ASCII_TRANSPORT(19007, "用户不存在，无法删除"),
    THIS_PATH_DOES_NOT_EXIST(19008, "此路径不存在");

    final int code;
    final String message;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    ErrorCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

}
