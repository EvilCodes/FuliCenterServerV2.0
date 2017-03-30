package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.MessageBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/deleteCollect")
public class DeleteCollectServlet extends HttpServlet{
	private static final long serialVersionUID = -4504435300272738289L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Collect.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		boolean isSuccess = biz.deleteCollect(userName, goodsId);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(isSuccess){
			msg=new MessageBean(true, "删除收藏成功");
		}else{
			msg=new MessageBean(false, "删除收藏失败");
		}
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
