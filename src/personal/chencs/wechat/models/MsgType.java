package personal.chencs.wechat.models;

/**
 * MsgType类型常量
 */
public class MsgType {

	// 接收消息->接收普通消息
	public static final String TEXT = "text";
	public static final String IMAGE = "image";
	public static final String VOICE = "voice";
	public static final String VIDEO = "video";
	public static final String SHORTVIDEO = "shortvideo";
	public static final String LOCATION = "location";
	public static final String LINK = "link";

	// 接收消息->接收事件推送
	public static final String EVENT = "event";

	// 事件具体类型
	public static class Event {
		public static final String SUBSCRIBE = "subscribe";// 订阅
		public static final String UNSUBSCRIBE = "unsubscribe";// 取消订阅
		public static final String SCAN = "SCAN";// 扫码
		public static final String LOCATION = "LOCATION";// 上报地理位置
		public static final String CLICK = "CLICK";// 点击菜单拉取消息
		public static final String VIEW = "VIEW";// 点击菜单跳转链接
	}

	// 硬件平台->平台基础消息接口->接受设备消息
	public static final String DEVICE_TEXT = "device_text";
	// 硬件平台->平台基础消息接口->接受设备绑定/解绑
	public static final String DEVICE_EVENT = "device_event";

	// 设备具体事件类型
	public static class DeviceEvent {
		public static final String UNBIND = "unbind";
		public static final String BIND = "bind";
	}

	// 硬件平台->平台基础消息接口->接入社交功能
	public static final String HARDWARE = "hardware";
}
