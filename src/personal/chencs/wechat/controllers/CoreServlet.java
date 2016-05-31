package personal.chencs.wechat.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import personal.chencs.wechat.service.CoreService;
import personal.chencs.wechat.utils.SignUtils;

/**
 * 请求处理的核心类
 * 
 * @author chencs
 *
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * 请求校验（确认请求来自微信服务器）
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 获取参数（时间戳、随机数、签名指和随机数字符串）
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String signature = request.getParameter("signature");
			String echostr = request.getParameter("echostr");
			// 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtils.checkSignature(signature, timestamp, nonce)) {
				out(echostr, response);
			} else {
				out("", response);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			out("", response);
		}
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 编码格式
			request.setCharacterEncoding("UTF-8");

			// 验证签名
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String signature = request.getParameter("signature");
			if (!SignUtils.checkSignature(signature, timestamp, nonce)) {
				out("", response);
				return;
			}


			// 解析xml
			Map<String, String> reqMap = parseXml(request.getInputStream());
			System.out.println("request from wechat server:" + reqMap);

			// 处理请求
			String xmlStr = CoreService.handle(reqMap);

			System.out.println("response to wechat server:" + xmlStr);

			// null 转为空字符串
			xmlStr = xmlStr == null ? "" : xmlStr;

			out(xmlStr, response);
		} catch (Throwable e) {
			e.printStackTrace();
			// 异常时响应空串
			out("", response);
		}
	}

	/**
	 * 输出字符串
	 */
	protected void out(String str, HttpServletResponse response) {
		Writer out = null;
		try {
			response.setContentType("text/xml;charset=UTF-8");
			out = response.getWriter();
			out.append(str);
			out.flush();
		} catch (IOException e) {
			// ignore
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
	
	/**
	 * 解析请求中的xml元素为Map
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, String> parseXml(InputStream in) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(in);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
			return map;
		} catch (DocumentException e1) {
			e1.printStackTrace();
			return null;
		}

	}

}
