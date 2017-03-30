package com.fuli_center.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.pingplusplus.model.Charge;

@WebServlet("/downloadGoodsThumb")
public class DownloadGoodsThumbServlet extends HttpServlet{

	private static final long serialVersionUID = -7553434383140500708L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String goodsThumb=request.getParameter(IFuLiCenterBiz.Collect.GOODS_THUMB);
		biz.downloadImg(response, goodsThumb);
	}
}
