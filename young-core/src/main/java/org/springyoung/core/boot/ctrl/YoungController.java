package org.springyoung.core.boot.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springyoung.core.response.R;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.secure.utils.AuthUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Young控制器封装类
 *
 */
public class YoungController {

    /**
     * ============================     REQUEST    =================================================
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return this.request;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public YoungUser getUser() {
        return AuthUtil.getUser();
    }

    /** ============================     API_RESULT    =================================================  */

    /**
     * 返回ApiResult
     *
     * @param data
     * @return R
     */
    public <T> R<T> data(T data) {
        return R.data(data);
    }

    /**
     * 返回ApiResult
     *
     * @param data
     * @param message
     * @return R
     */
    public <T> R<T> data(T data, String message) {
        return R.data(data, message);
    }

    /**
     * 返回ApiResult
     *
     * @param data
     * @param message
     * @param code
     * @return R
     */
    public <T> R<T> data(T data, String message, int code) {
        return R.data(code, data, message);
    }

    /**
     * 返回ApiResult
     *
     * @param message
     * @return R
     */
    public R success(String message) {
        return R.success(message);
    }

    /**
     * 返回ApiResult
     *
     * @param message
     * @return R
     */
    public R fail(String message) {
        return R.fail(message);
    }

    /**
     * 返回ApiResult
     *
     * @param flag
     * @return R
     */
    public R status(boolean flag) {
        return R.status(flag);
    }

}