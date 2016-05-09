package personal.chencs.tulingrobot;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

	@Test
	public void test() {
		Assert.assertEquals("1e9d5e36a5fdf53d8141139101b96f81", Config.APIKEY);
		Assert.assertEquals("4638ac84cbbfc41c", Config.SECRET);
	}

}
