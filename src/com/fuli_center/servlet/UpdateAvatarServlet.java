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

@WebServlet("/updateAvatar")
public class UpdateAvatarServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1、接收参数
		String nameOrHxid = request.getParameter(I.NAME_OR_HXID);
		String avatarType = request.getParameter(I.AVATAR_TYPE);
		// 2、交给业务层处理
		Result result = biz.updateAvatar(nameOrHxid,avatarType,request);
		// 3、将结果返回给客户端
		JsonUtil.writeJsonToClient(result, response);
	}
}
