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

import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findCategoryChildren")
public class FindCategoryChildrenServlet extends HttpServlet{
	private static final long serialVersionUID = 2448843293844113684L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int parentId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.CategoryChild.PARENT_ID));
//		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
//		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		CategoryChildBean[] children = biz.findCategoryChildren(parentId/*, pageId, pageSize*/);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), children);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
