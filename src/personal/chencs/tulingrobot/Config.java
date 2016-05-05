package personal.chencs.tulingrobot;

import java.util.Properties;

public class Config {
	public static final String APIKEY;
	public static final String SECRET;

	static {
		try {
			Properties prop = new Properties();
			prop.load(Config.class.getResourceAsStream("config.properties"));

			APIKEY = prop.getProperty("apiKey", "");
			SECRET = prop.getProperty("secret", "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("load config error " + e.getMessage());
		}
	}
}
