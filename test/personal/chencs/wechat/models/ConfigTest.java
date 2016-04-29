package personal.chencs.wechat.models;

import org.junit.Test;
import org.junit.Assert;

public class ConfigTest {

	@Test
	public void test() {
		Assert.assertEquals("wxa9082c86a0ddbcf6", Config.APPID);
		Assert.assertEquals("d4624c36b6795d1d99dcf0547af5443d", Config.APPSECRET);
		Assert.assertEquals("chencs0603", Config.TOKEN);
	}

}
