package com.fuli_center.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.CartBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findCarts")
public class FindCartsServlet extends HttpServlet {
	private static final long serialVersionUID = 7341217489003439673L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter(IFuLiCenterBiz.Cart.USER_NAME);
//		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
//		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
//		CartBean[] carts = biz.findCarts(userName, pageId, pageSize);
		List<CartBean> carts = biz.findCarts(userName);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), carts);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
