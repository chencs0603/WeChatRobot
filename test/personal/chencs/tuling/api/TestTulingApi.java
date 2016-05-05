package personal.chencs.tuling.api;

import org.junit.Test;

public class TestTulingApi {

	@Test
	public void test() {
		String result = new TulingApiProcess().getTulingResult("深圳天气");
		System.out.println(result);
	}

}
