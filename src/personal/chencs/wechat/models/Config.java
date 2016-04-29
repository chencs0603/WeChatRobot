package personal.chencs.wechat.models;

import java.util.Properties;

/**
 * 微信公众平台测试账号信息及接口配置信息
 */
public abstract class Config {

	public static final String APPID;
	public static final String APPSECRET;
	public static final String TOKEN;

	static {
		try {
			Properties prop = new Properties();
			prop.load(Config.class.getResourceAsStream("config.properties"));

			APPID = prop.getProperty("appID", "");
			APPSECRET = prop.getProperty("appSecret", "");
			TOKEN = prop.getProperty("token", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("load config error " + e.getMessage());
		}
	}

}
