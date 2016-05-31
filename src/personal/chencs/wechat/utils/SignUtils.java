package personal.chencs.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import personal.chencs.wechat.models.Config;

/**
 * 签名工具类
 * @author chencs
 *
 */
public class SignUtils {
	/**
	 * 获得JS-SDK使用权限签名
	 * 
	 * @param signature 签名指
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		// 将token、timestamp、nonce进行字典排序
		String[] paramArr = new String[] { Config.TOKEN, timestamp, nonce };
		Arrays.sort(paramArr);

		// 将三个字符串拼接
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

		String ciphertext = null;
		try {
			//进行SHA1签名
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = bytesToHexStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 签名值相等则返回true，否则返回false
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将byte数组转换为16进制字符串
	 * @param byteArray
	 * @return
	 */
	private static String bytesToHexStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将byte转换为16进制字符串
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
