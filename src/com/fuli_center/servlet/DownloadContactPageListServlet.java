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

@WebServlet("/downloadContactPageList")
public class DownloadContactPageListServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter(I.Contact.USER_NAME);
		String pageId = request.getParameter(I.PAGE_ID);
		String pageSize = request.getParameter(I.PAGE_SIZE);
		Result result = biz.findContactPagesByUserName(userName, pageId, pageSize);
		JsonUtil.writeJsonToClient(result, response);
	}
}
