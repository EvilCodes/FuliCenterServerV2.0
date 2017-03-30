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

import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.MessageBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/addCart")
public class AddCartServlet extends HttpServlet{
	private static final long serialVersionUID = 5216647127285985091L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodsId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.GOODS_ID));
		String userName=request.getParameter(IFuLiCenterBiz.Cart.USER_NAME);
		int count=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.COUNT));
		boolean isChecked=Boolean.parseBoolean(request.getParameter(IFuLiCenterBiz.Cart.IS_CHECKED));
		CartBean cart=new CartBean(0, userName, goodsId, count, isChecked);
		boolean flag = biz.addCart(cart);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(flag){
			msg=new MessageBean(true, "success");
		}else{
			msg=new MessageBean(false, "添加失败");
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
