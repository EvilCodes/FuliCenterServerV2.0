package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/downloadImage")
public class DownloadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgUrl=request.getParameter(IFuLiCenterBiz.Boutique.IMAGE_URL);
		response.setContentType("image/jpeg");
		biz.downloadImg(response, imgUrl);
	}
}
