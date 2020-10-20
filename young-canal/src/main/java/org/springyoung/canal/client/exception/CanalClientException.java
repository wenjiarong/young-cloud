package org.springyoung.canal.client.exception;

/**
 * canal 操作的异常类
 *
 */
public class CanalClientException extends RuntimeException {
    
    /**
     * 默认构造方法
     *
     */
    public CanalClientException() {
    }
    /**
     *  带错误信息的构造方法
     *
     */
    public CanalClientException(String message) {
        super(message);
    }
    
    /**
     * 带错误信息和其造成原因的构造方法
     *
     */
    public CanalClientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * 带造成错误信息的构造方法
     *
     */
    public CanalClientException(Throwable cause) {
        super(cause);
    }
    
    public CanalClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
