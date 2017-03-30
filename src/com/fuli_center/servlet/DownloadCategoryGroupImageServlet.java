package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/downloadCategoryGroupImage")
public class DownloadCategoryGroupImageServlet extends HttpServlet{

	private static final long serialVersionUID = -8804311480437939707L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName=request.getParameter(IFuLiCenterBiz.CategoryParent.IMAGE_URL);
		biz.downloadImg(response, fileName);
	}
}
