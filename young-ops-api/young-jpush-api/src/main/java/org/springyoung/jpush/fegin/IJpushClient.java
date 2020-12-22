package org.springyoung.jpush.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springyoung.core.constant.YoungServerConstant;
import org.springyoung.core.tool.api.R;
import org.springyoung.jpush.entity.SendMsg;

/**
 * 极光推送 Feign接口类
 */
@FeignClient(value = YoungServerConstant.YOUNG_JPUSH, contextId = "jpushClient")
public interface IJpushClient {

	String API_PREFIX = "/client";
	String PUSH = API_PREFIX + "/push";
	String CONNECT = API_PREFIX + "/connect";

	/**
	 * 单独对regId进行推送
	 *
	 * @param sendMsg 推送实体
	 * @param userId  用户id
	 * @return
	 */
	@PostMapping(PUSH)
	void push(SendMsg sendMsg, @RequestParam String userId);


	/**
	 * 存储用户发过来的regId
	 *
	 * @param regId 设备对应的唯一极光ID
	 * @return
	 */
	@PostMapping(CONNECT)
	R connect(@RequestParam String regId, @RequestParam String type);

}
