package com.fuli_center.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.MessageBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/addCollect")
public class AddCollectServlet extends HttpServlet{
	private static final long serialVersionUID = -4260932945106083050L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Collect.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
//		String goodsName=request.getParameter(IFuLiCenterBiz.Collect.GOODS_NAME);
//		String goodsEnglishName=request.getParameter(IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME);
//		String goodsThumb=request.getParameter(IFuLiCenterBiz.Collect.GOODS_THUMB);
//		String goodsImg=request.getParameter(IFuLiCenterBiz.Collect.GOODS_IMG);
//		long addTime=Long.parseLong(request.getParameter(IFuLiCenterBiz.Collect.ADD_TIME));
		long addTime = System.currentTimeMillis();
//		CollectBean collect=new CollectBean(userName, goodsId, goodsName, goodsEnglishName, goodsThumb, goodsImg, addTime);
		CollectBean collect=new CollectBean(userName, goodsId, addTime);
		int id = biz.addCollect(collect);
		MessageBean msg=null;
		if(id>0){
			msg=new MessageBean(true, "收藏成功");
		}else{
			msg=new MessageBean(false, "收藏失败");
		}
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), msg);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
