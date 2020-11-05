package org.springyoung.jpush.config;

import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JiGuangConfig {

	// appkey
	@Value("${push.appkey}")
	private String appkey;
	// secret
	@Value("${push.secret}")
	private String secret;
	private JPushClient jPushClient;

	// 推送客户端
	@PostConstruct
	public void initJPushClient() {
		jPushClient = new JPushClient(secret, appkey);
	}

	// 获取推送客户端
	public JPushClient getJPushClient() {
		return jPushClient;
	}

}
