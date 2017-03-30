package com.fuli_center.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.utils.I;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;

@WebServlet("/pay")
public class PayServlet extends HttpServlet{

	private static final long serialVersionUID = -129080538025497041L;
	
	private static final String KEY_EXTRAS = "extras";
	private static final String KEY_AMOUNT = "amount";
	private static final String KEY_CURRENCY = "currency";
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_BODY = "body";
	private static final String KEY_ORDER_NO = "order_no";
	private static final String KEY_CHANNEL = "channel";
	private static final String KEY_CLIENT_IP = "client_ip";
	private static final String KEY_APP_ID = "id";
	private static final String KEY_APP = "app";
	
	private static final String VALUE_RMB = "cny";
	
	
	/**
	 * pingpp API key
	 */
	private static String API_KEY = "sk_test_GG8yrPPy94WH8eHGmTSCajfH";
	
	/**
	 * pingpp app id
	 */
	private static String APP_ID = "app_v1SSeTir5W58Oyvb";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		try {
			//读取客户端上传的post请求中的参数
			BufferedReader reader=request.getReader();
			StringBuffer sb=new StringBuffer();
			String line;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			ObjectMapper om=new ObjectMapper();
			HashMap<String,Object> map = om.readValue(sb.toString(),HashMap.class);
			//获取商品数量
			int amount=Integer.parseInt(map.get(KEY_AMOUNT).toString());
			//获取支付通道信息
			String channel=map.get(KEY_CHANNEL).toString();
			//获取支付的日期
			String orderNo=map.get(KEY_ORDER_NO).toString();
			
			//获取附加数据
			HashMap<String, Object> extras=(HashMap<String, Object>) map.get(KEY_EXTRAS);
			
			HashMap<String, Object> chargeMap=new HashMap<String, Object>();
			chargeMap.put(KEY_AMOUNT, amount);
			chargeMap.put(KEY_CHANNEL, channel);
			chargeMap.put(KEY_ORDER_NO, orderNo);
			chargeMap.put(KEY_CURRENCY, VALUE_RMB);
			String clientIp=Server.getIpAddr(request);
			chargeMap.put(KEY_CLIENT_IP, clientIp);
			chargeMap.put(KEY_SUBJECT, extras.get(KEY_SUBJECT));
			chargeMap.put(KEY_BODY, extras.get(KEY_BODY));
			
			Pingpp.apiKey=API_KEY;
			HashMap<String, Object> appMap=new HashMap<String, Object>();
			appMap.put(KEY_APP_ID, APP_ID);
			chargeMap.put(KEY_APP, appMap);
			
			Server.chargeResult=Charge.create(chargeMap);
			System.out.println(Server.chargeResult.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
