package org.springyoung.auth.service;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springyoung.auth.properties.YoungAuthProperties;
import org.springyoung.auth.properties.YoungValidateCodeProperties;
import org.springyoung.common.constant.CacheConstant;
import org.springyoung.common.constant.YoungConstant;
import org.springyoung.common.exception.ValidateCodeException;
import org.springyoung.common.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 1、createCaptcha方法通过验证码配置文件YoungValidateCodeProperties生成相应的验证码，比如PNG格式的或者GIF格式的，
 * 验证码图片的长宽高，验证码字符的类型（纯数字，纯字母或者数字字母组合），验证码字符的长度等；
 * <p>
 * 2、setHeader用于设置响应头。在生成验证码图片后我们需要将其返回到客户端，所以需要根据不同的验证码格式设置不同的响应头；
 * <p>
 * 3、create方法用于生成验证码。在前后端不分离的架构下，我们通过浏览器传输的jsessionid来和验证码图片一一对应，
 * 但前后的分离的模式下，客户端发送的请求并没有携带jsessionid（因为不再基于Session），所以我们需要客户端在
 * 发送获取验证码请求的时候，携带一个key（比如按一定算法生成的随机字符串，模拟jsessionid）来和验证码一一对应。
 * 于是我们在create里一开始就从请求中获取key值，然后根据验证码配置文件生成验证码，并将验证码字符保存到了Redis中
 * （Redis Key为young.captcha. + 客户端上送的key值，有效时间为配置文件定义的120秒），并将验证码图片以流的形式返回给客户端。
 * <p>
 * 4、check用于校验验证码，逻辑很简单，就是根据客户端上送的key，从Redis中取出相应的验证码，然后和用户输入的验证码进行比较。
 */

@Service
@AllArgsConstructor
public class ValidateCodeService {

    private final RedisService redisService;
    private final YoungAuthProperties properties;

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        String key = request.getParameter("key");
        if (StringUtils.isBlank(key)) {
            throw new ValidateCodeException("验证码key不能为空");
        }
        YoungValidateCodeProperties code = properties.getCode();
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);
        redisService.setEx(CacheConstant.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), code.getTime());
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     *
     * @param key   前端上送 key
     * @param value 前端上送待校验值
     */
    public void check(String key, String value) throws ValidateCodeException {
        Object codeInRedis = redisService.get(CacheConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    private Captcha createCaptcha(YoungValidateCodeProperties code) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(code.getType(), YoungConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, YoungConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

}