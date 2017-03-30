package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/downloadColorImg")
public class DownloadColorImgServlet extends HttpServlet {
	private static final long serialVersionUID = 155316433130552631L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgUrl=request.getParameter(IFuLiCenterBiz.Color.COLOR_IMG);
		biz.downloadImg(response, imgUrl);
	}
}	
