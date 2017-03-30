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
import com.fuli_center.pojo.User;
import com.fuli_center.utils.I;
import com.fuli_center.utils.JsonUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter(I.User.USER_NAME);
		String password = request.getParameter(I.User.PASSWORD);
		User user = new User();
		user.setMUserName(userName);
		user.setMUserPassword(password);
		Result result = biz.login(user);
		JsonUtil.writeJsonToClient(result, response);
	}
}
