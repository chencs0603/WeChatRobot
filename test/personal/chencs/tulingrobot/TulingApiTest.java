package personal.chencs.tulingrobot;

import org.json.JSONException;
import org.junit.Test;

import personal.chencs.tulingrobot.TulingRobotApi;

public class TulingApiTest {

	@Test
	public void test() throws JSONException {
		String result = new TulingRobotApi().getTulingResult("深圳天气");
		System.out.println(result);
	}

}
