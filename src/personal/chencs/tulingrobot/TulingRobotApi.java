package personal.chencs.tulingrobot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 调用图灵机器人api接口，获取智能回复内容
 * @author chencs
 *
 */
public class TulingRobotApi {
	/** 此处为图灵api接口，参数key需要自己去注册申请，先以11111111代替 */
	/**
	 * 图灵机器人api接口地址，
	 */
	private static final String TULING_ROBOT_API_URL = "http://www.tuling123.com/openapi/api";
	
	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param content
	 * @return
	 * @throws JSONException 
	 */
	public String getTulingResult(String content) throws JSONException{
		
		JSONObject json = new JSONObject();
		json.put("key", "1e9d5e36a5fdf53d8141139101b96f81");
		json.put("info", content);
		
		String body = json.toString();
		String resultContent;
		
		try {
			HttpPost httpPost = new HttpPost(TULING_ROBOT_API_URL);
			StringEntity entity = new StringEntity(body, "UTF-8");
			httpPost.setEntity(entity);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpPost);
			resultContent = new Utf8ResponseHandler()
					.handleResponse(response);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
//		String param = "";
//		try {
//			param = TULING_ROBOT_API_URL + URLEncoder.encode(content,"utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} //将参数转为url编码
//		
//		/** 发送httpget请求 */
//		HttpGet request = new HttpGet(param);
//		String result = "";
//		try {
//			HttpResponse response = HttpClients.createDefault().execute(request);
//			if(response.getStatusLine().getStatusCode()==200){
//				result = EntityUtils.toString(response.getEntity());
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		/** 请求失败处理 */
//		if(null==result){
//			return "对不起，你说的话真是太高深了……";
//		}
		
		String result = "";
		JSONObject jsonResult = new JSONObject(resultContent);
		//以code=100000为例，参考图灵机器人api文档
		if(100000==jsonResult.getInt("code")){
			result = jsonResult.getString("text");
		}
		
		return result;
	}
	/**
	 * utf-8编码
	 */
	static class Utf8ResponseHandler implements ResponseHandler<String> {
		public String handleResponse(final HttpResponse response)
				throws HttpResponseException, IOException {
			final StatusLine statusLine = response.getStatusLine();
			final HttpEntity entity = response.getEntity();
			if (statusLine.getStatusCode() >= 300) {
				EntityUtils.consume(entity);
				throw new HttpResponseException(statusLine.getStatusCode(),
						statusLine.getReasonPhrase());
			}
			return entity == null ? null : EntityUtils
					.toString(entity, "UTF-8");
		}
	}
}