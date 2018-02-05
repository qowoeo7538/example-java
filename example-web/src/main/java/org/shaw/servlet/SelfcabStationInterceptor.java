package org.shaw.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SelfcabStationInterceptor extends HttpServlet {
	public static final String MAC_REQUEST = "RequestMac";
	public static final String EMP_BASE = "RequestPsnbasdoc";
	public static final String EMP_WORK = "RequestPsndoc";
	public static final String SecretKey = "jNpKcyXrHfNJ";
	/***
	 * 服务端返回客户端错误信息
	 */
	private static String err= "<response><success>false</success><errorMsg>签名不正确</errorMsg></response>";
	/***
	 * 服务端返回客户端错误信息
	 */
	private static String io_exception= "<response><success>false</success><errorMsg>服务器异常</errorMsg></response>";
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数据解码的字符集
	 */
	private static final String CHAR_SET = "UTF-8";
	/**
	 * <request>
		X	<mailNos>232323276737</mailNos>
		X	<cnOrderCode>LP123456</cnOrderCode>
			<time>2015-12-7 20:11:47</time>
		X	<desc>快件已由XXX菜鸟驿站代收，请及时取件，如有疑问请联系XXXX</desc>
			<city>杭州市</city>
			<stationName>XXX驿站店</stationName>
			<stationContact>0751-56518888</stationContact>
		</request>
	 */


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HttpServletResponse response = resp;
		HttpServletRequest request = req;
		//防止中文乱码 update 2016-6-21 xiaoshuai
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		//获取post参数
		StringBuffer sbBuffer = new StringBuffer() ;
		BufferedReader br = null;
		String line = null;
		try {
			request.setCharacterEncoding(CHAR_SET);
			br=new BufferedReader(new InputStreamReader(request.getInputStream()));
			while((line=br.readLine())!=null){
				sbBuffer.append(line) ;
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				out.write(new String(io_exception.getBytes(),"UTF-8"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}finally{
				out.close();
			}
		} finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String logistics_interface = null;
		String data_digest = null;
		try {
			//参数非空
			if(null!=sbBuffer.toString()&&!"".equals(sbBuffer.toString())){
				String requestContent = java.net.URLDecoder.decode(sbBuffer.toString(),"utf-8");
				logistics_interface =splitString(requestContent, "logistics_interface=");
				data_digest=splitString(requestContent, "data_digest=");
				if (doSign(logistics_interface, CHAR_SET, SecretKey).equals(data_digest)) {
						// TODO xxxx
				}else{
					try {
						out.write(new String(err.getBytes(),"UTF-8"));
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						out.close();
					}
				}
			}else{
				try {
					out.write(new String(err.getBytes(),"UTF-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					out.close();
				}
			}


		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			try {
				out.write(new String(io_exception.getBytes(),"UTF-8"));
			} catch (IOException e2) {
				e2.printStackTrace();
			}finally{
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				out.write(new String(io_exception.getBytes(),"UTF-8"));
			} catch (IOException e2) {
				e2.printStackTrace();
			}finally{
				out.close();
			}
		}
	}
	/**
	 * MD5签名算法说明
	 * @param content
	 * @param charset
	 * @param keys
	 * @return
	 * 算法说明：根据传入的content（报文内容）、charset（编码方式）、keys（SecretKey）三个参数进行加密。编码方式目前支持GBK与UTF-8两种，签名SecretKey为菜鸟为CP生成，签名体为报文内容+签名Key，签名算法为：对签名内容进行md5，后将内容转换成base64编码。
		例如：content="hello1234"; charset="utf-8"; keys="key123";
		签名结果为：ufYU7rvXhHY3IDyZgyt6SA== 即为正确。
	 */
	public static String doSign(String content, String charset, String keys) {
		 String sign = "";
	        content = content + keys;
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(content.getBytes(charset));
	            // sign = new String(Base64.encodeBase64(md.digest()), charset);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	        return sign;
	    }
	//分离参数方法
	public static String splitString(String str,String temp){
		String result = null;
		if (str.indexOf(temp) != -1) {
			if (str.substring(str.indexOf(temp)).indexOf("&") != -1) {
				result = str.substring(str.indexOf(temp)).substring(str.substring(str.indexOf(temp)).indexOf("=")+1, str.substring(str.indexOf(temp)).indexOf("&"));
			} else {
				result = str.substring(str.indexOf(temp)).substring(str.substring(str.indexOf(temp)).indexOf("=")+1);
			}
		}
		return result;
	}
}