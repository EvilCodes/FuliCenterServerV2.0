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
	
@WebServlet("/updateNick")
public class UpdateNickServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1、接收参数
		String username = request.getParameter(I.User.USER_NAME);
		String nick = request.getParameter(I.User.NICK);
		// 2、交给业务层去处理，返回结果
		Result result = biz.updateNick(username,nick);
		// 3、将结果发送到页面
		JsonUtil.writeJsonToClient(result, response);
	}
}
