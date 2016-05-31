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
 * ������ĺ�����
 * 
 * @author chencs
 *
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * ����У�飨ȷ����������΢�ŷ�������
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// ��ȡ������ʱ������������ǩ��ָ��������ַ�����
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String signature = request.getParameter("signature");
			String echostr = request.getParameter("echostr");
			// ����У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
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
	 * ����΢�ŷ�������������Ϣ
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// �����ʽ
			request.setCharacterEncoding("UTF-8");

			// ��֤ǩ��
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String signature = request.getParameter("signature");
			if (!SignUtils.checkSignature(signature, timestamp, nonce)) {
				out("", response);
				return;
			}


			// ����xml
			Map<String, String> reqMap = parseXml(request.getInputStream());
			System.out.println("request from wechat server:" + reqMap);

			// ��������
			String xmlStr = CoreService.handle(reqMap);

			System.out.println("response to wechat server:" + xmlStr);

			// null תΪ���ַ���
			xmlStr = xmlStr == null ? "" : xmlStr;

			out(xmlStr, response);
		} catch (Throwable e) {
			e.printStackTrace();
			// �쳣ʱ��Ӧ�մ�
			out("", response);
		}
	}

	/**
	 * ����ַ���
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
	 * ���������е�xmlԪ��ΪMap
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
