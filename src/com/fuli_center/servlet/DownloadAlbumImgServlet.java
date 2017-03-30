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

@WebServlet("/downloadAlbumImg")
public class DownloadAlbumImgServlet extends HttpServlet{
	private static final long serialVersionUID = -7980624847875952686L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgUrl=request.getParameter(I.KEY_ALBUM_IMG_URL);
		biz.downloadImg(response, imgUrl);
	}
}
