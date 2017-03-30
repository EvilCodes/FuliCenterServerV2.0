package com.fuli_center.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.pingplusplus.model.Charge;

@WebServlet("/findCharge")
public class FindChargeServlet extends HttpServlet{
	private static final long serialVersionUID = -2026405527138982114L;

	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), Server.chargeResult);
			String text = om.writeValueAsString(Server.chargeResult);
			HashMap<String, Object> charge = om.readValue(text, HashMap.class);
			om.writeValue(response.getOutputStream(), charge);
		}  catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
