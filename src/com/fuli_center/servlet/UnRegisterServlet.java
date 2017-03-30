package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.bean.Result;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.utils.I;
import com.fuli_center.utils.JsonUtil;

@WebServlet("/unregister")
public class UnRegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收用户参数
		String userName = request.getParameter(I.User.USER_NAME);
		Result result = biz.unRegister(userName);
		JsonUtil.writeJsonToClient(result, response);
	}
}
