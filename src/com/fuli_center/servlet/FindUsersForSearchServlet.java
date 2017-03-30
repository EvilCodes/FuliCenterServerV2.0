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

/**
 * 根据用户名或昵称，模糊分页查询用户数据信息
 */
@WebServlet("/findUsersForSearch")
public class FindUsersForSearchServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter(I.User.USER_NAME);
		String userNick = request.getParameter(I.User.NICK);
		String pageId = request.getParameter(I.PAGE_ID);
		String pageSize = request.getParameter(I.PAGE_SIZE);
		Result result = biz.findUsersForSearch(userName,userNick,pageId,pageSize);
		JsonUtil.writeJsonToClient(result, response);
	}
}
