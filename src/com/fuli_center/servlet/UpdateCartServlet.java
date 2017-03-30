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

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 6720899265218737091L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cartId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.ID));
		int count=Integer.parseInt(request.getParameter(IFuLiCenterBiz.Cart.COUNT));
		boolean isChecked=Boolean.parseBoolean(request.getParameter(IFuLiCenterBiz.Cart.IS_CHECKED));
		boolean isSuccess = biz.updateCart(cartId, count, isChecked);
		ObjectMapper om=new ObjectMapper();
		MessageBean msg=null;
		if(isSuccess){
			msg=new MessageBean(true, "修改购物车中的商品成功");
		}else{
			msg=new MessageBean(false, "修改购物车中的商品失败");
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
