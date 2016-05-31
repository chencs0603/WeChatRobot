package personal.chencs.wechat.service;

import java.util.Map;

import personal.chencs.wechat.models.MsgType;
import personal.chencs.wechat.models.XmlResponse;



public class CoreService {
	
	public static String handle(Map<String, String> reqMap) {
		// 文档位置：接收消息
		String msgType = reqMap.get("MsgType");
		String fromUser = reqMap.get("FromUserName");
		String toUser = reqMap.get("ToUserName");
		System.out.println("MsgType:" + msgType + ", fromUser:" + fromUser + ", toUser:" + toUser);

		// 文本消息
		if (MsgType.TEXT.equals(msgType)) {
			// 可以在此处进行关键字自动回复
			String content = "[自动回复]收到文本消息：" + reqMap.get("Content");
			return XmlResponse.buildText(fromUser, toUser, content);
		}

		// 基础事件推送
		if (MsgType.EVENT.equals(msgType)) {
			String event = reqMap.get("Event");
			// 订阅/取消订阅事件
			switch (event) {
			case MsgType.Event.SUBSCRIBE:
				//根据openid获取用户基本信息
				
				// 保存用户信息
				
				// 回复欢迎语
				return XmlResponse.buildText(fromUser, toUser,
						"Welcome to WeChat Subscription of Chencs!");
			case MsgType.Event.UNSUBSCRIBE:
				// 删除用户信息
				return "";
			default:
				return "";
			}
		}

		// 设备事件推送
		if (MsgType.DEVICE_EVENT.equals(msgType)) {
			String reqContent = reqMap.get("Content");
			String deviceType = reqMap.get("DeviceType");
			String deviceID = reqMap.get("DeviceID");
			String sessionID = reqMap.get("SessionID");
			final String openID = reqMap.get("OpenID");
			String event = reqMap.get("Event");

			// 绑定/解绑事件
			switch (event) {
			case MsgType.DeviceEvent.BIND:
				// 存储用户和设备的绑定关系....
				
				break;
			case MsgType.DeviceEvent.UNBIND:
				// 删除用户和设备的绑定关系...
				
				break;
			default:
				break;
			}
			// 设备绑定/解绑事件可以回复空包体
			return "";
		}
		return "";
	}

}
