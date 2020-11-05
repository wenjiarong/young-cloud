package org.springyoung.jpush.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springyoung.jpush.entity.PushBean;
import org.springyoung.jpush.service.JiGuangPushService;
import org.springyoung.jpush.service.MyJiGuangPushService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 推送服务
 * 封装业务功能相关
 */
@Service
@AllArgsConstructor
public class JiGuangPushServiceImpl implements JiGuangPushService {
	/**
	 * 一次推送最大数量 (极光限制1000)
	 */
	private static final int max_size = 800;

	private MyJiGuangPushService jPushService;

	/**
	 * 推送全部, 不支持附加信息
	 *
	 * @return
	 */
	@Override
	public boolean pushAll(PushBean pushBean) {
		return jPushService.pushAll(pushBean);
	}

	/**
	 * 推送全部ios
	 *
	 * @return
	 */
	@Override
	public boolean pushIos(PushBean pushBean) {
		return jPushService.pushIos(pushBean);
	}

	/**
	 * 推送ios 指定id
	 *
	 * @return
	 */
	@Override
	public boolean pushIos(PushBean pushBean, String... registids) {
		registids = checkRegistids(registids); // 剔除无效registed
		while (registids.length > max_size) { // 每次推送max_size个
			jPushService.pushIos(pushBean, Arrays.copyOfRange(registids, 0, max_size));
			registids = Arrays.copyOfRange(registids, max_size, registids.length);
		}
		return jPushService.pushIos(pushBean, registids);
	}

	/**
	 * 推送全部android
	 *
	 * @return
	 */
	@Override
	public boolean pushAndroid(PushBean pushBean) {
		return jPushService.pushAndroid(pushBean);
	}

	/**
	 * 推送android 指定id
	 *
	 * @return
	 */
	@Override
	public boolean pushAndroid(PushBean pushBean, String... registids) {
		registids = checkRegistids(registids); // 剔除无效registed
		while (registids.length > max_size) { // 每次推送max_size个
			jPushService.pushAndroid(pushBean, Arrays.copyOfRange(registids, 0, max_size));
			registids = Arrays.copyOfRange(registids, max_size, registids.length);
		}
		return jPushService.pushAndroid(pushBean, registids);
	}

	/**
	 * 剔除无效registed
	 *
	 * @param registids
	 * @return
	 */
	@Override
	public String[] checkRegistids(String[] registids) {
		List<String> regList = new ArrayList<String>(registids.length);
		for (String registid : registids) {
			if (registid!=null && !"".equals(registid.trim())) {
				regList.add(registid);
			}
		}
		return regList.toArray(new String[0]);
	}

}
