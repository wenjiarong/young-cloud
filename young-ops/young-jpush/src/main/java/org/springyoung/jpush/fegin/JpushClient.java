package org.springyoung.jpush.fegin;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springyoung.common.service.RedisService;
import org.springyoung.core.constant.CacheConstant;
import org.springyoung.core.tool.api.R;
import org.springyoung.core.secure.YoungUser;
import org.springyoung.core.secure.utils.AuthUtil;
import org.springyoung.core.tool.utils.ObjectUtil;
import org.springyoung.jpush.constant.AppType;
import org.springyoung.jpush.entity.PushBean;
import org.springyoung.jpush.entity.SendMsg;
import org.springyoung.jpush.service.JiGuangPushService;

import java.util.HashMap;

/**
 * @ClassName JpushClient
 * @Description TODO
 * @Author 小温
 * @Date 2020/5/29 11:30
 * @Version 1.0
 */
@RestController
@AllArgsConstructor
public class JpushClient implements IJpushClient {

    private final RedisService redisService;
    private final JiGuangPushService jiGuangPushService;

    /**
     * 单独对regId进行推送
     *
     * @param sendMsg 推送实体
     * @param userId  用户id
     * @return
     */
    @PostMapping(PUSH)
    public void push(@RequestBody SendMsg sendMsg, @RequestParam String userId) {
        String signs = (String) redisService.hget(CacheConstant.CACHE_JPUSH_REGID, userId);
        if (ObjectUtil.isNotEmpty(signs)) {
            String[] split = signs.split(",");
            for (String sign : split) {
                String[] s = sign.split(":");
                String type = s[0];
                String regId = s[1];
                PushBean pushBean = new PushBean();
                pushBean.setTitle(sendMsg.getTitle());
                pushBean.setAlert(sendMsg.getContent());
                HashMap<String, String> map = new HashMap<>();
                map.put("type", sendMsg.getType());
                map.put("data", sendMsg.getData());
                pushBean.setExtras(map);
                if (AppType.ios.getSign().equals(type)) {
                    jiGuangPushService.pushIos(pushBean, regId);
                } else if (AppType.android.getSign().equals(type)) {
                    jiGuangPushService.pushAndroid(pushBean, regId);
                }
            }
        }
    }


    /**
     * 存储用户发过来的regId
     *
     * @param regId 设备对应的唯一极光ID
     * @return
     */
    @PostMapping(CONNECT)
    public R connect(@RequestParam String regId, @RequestParam String type) {
        YoungUser user = AuthUtil.getUser();
        if (user == null) {
            R.fail("非法连接");
        }
        String userId = user.getUserId().toString();
        String sign = type + ":" + regId;
        String signs = (String) redisService.hget(CacheConstant.CACHE_JPUSH_REGID, userId);
        if (StringUtils.isNotBlank(signs)) {
            if (signs.indexOf(sign) == -1) {
                signs = signs + "," + sign;
                redisService.hset(CacheConstant.CACHE_JPUSH_REGID, userId, signs);
            }
        } else {
            redisService.hset(CacheConstant.CACHE_JPUSH_REGID, userId, sign);
        }
        return R.data(true);
    }

}
