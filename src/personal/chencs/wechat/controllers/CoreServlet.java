package personal.chencs.wechat.controllers;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
