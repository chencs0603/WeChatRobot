package personal.chencs.tulingrobot;

import org.junit.Test;

import personal.chencs.tulingrobot.TulingRobotApi;

public class TulingApiTest {

	@Test
	public void test(){
		new TulingRobotApi();
		String result = TulingRobotApi.getTulingResult("深圳天气");
		System.out.println(result);
	}

}
