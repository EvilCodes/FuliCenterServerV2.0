package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.utils.I;

@WebServlet("/downloadAvatar")
public class DownloadAvatarServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz  biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nameOrHxid = request.getParameter(I.NAME_OR_HXID);
		String avatarSuffix = request.getParameter(I.Avatar.AVATAR_SUFFIX);
		String avatarType = request.getParameter(I.AVATAR_TYPE);
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		biz.downAvatar(nameOrHxid,avatarSuffix,avatarType,response,width,height);
	}
}
