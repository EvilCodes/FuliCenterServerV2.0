package com.fuli_center.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findGoodsDetails")
public class FindGoodsDetailsServlet extends HttpServlet{
	
	private static final long serialVersionUID = 8164914902637027984L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int catId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.GoodDetails.CAT_ID));
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		ArrayList<GoodDetailsBean> goodDetailsList = biz.findGoodsDetails(catId, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), goodDetailsList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
