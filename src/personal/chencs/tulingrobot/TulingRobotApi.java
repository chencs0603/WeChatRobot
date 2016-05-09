package personal.chencs.tulingrobot;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
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
	/**
	 * 图灵机器人api接口地址，
	 */
	private static final String TULING_ROBOT_API_URL = "http://www.tuling123.com/openapi/api";
	
	/**
	 * 调用图灵机器人api接口，获取智能回复内容
	 * @param content
	 * @return
	 * @throws JSONException 
	 */
	public String getTulingResult(String content) throws JSONException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(TULING_ROBOT_API_URL);
		
		JSONObject json = new JSONObject();
		json.put("key", Config.APIKEY);
		json.put("info", content);
		
		String body = json.toString();
		String resultContent = "";
		
		try {
			StringEntity entity = new StringEntity(body, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = httpclient.execute(httpPost); 
			try {  
                HttpEntity resultEntity = response.getEntity();  
                if (entity != null) {  
                    resultContent =  EntityUtils.toString(resultEntity, "UTF-8");
                    System.out.println("Response content: " + resultContent);  
                }  
            } finally {  
                response.close();  
            }  

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			  // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
		
		String result = "";
		JSONObject jsonResult = new JSONObject(resultContent);
		//以code=100000为例，参考图灵机器人api文档
		if(100000 == jsonResult.getInt("code")){
			result = jsonResult.getString("text");
		}else if(40001 == jsonResult.getInt("code")){
			result = jsonResult.getString("text");
		}
		else{
			result = "您的话太高深，我怎么听不懂呢";
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