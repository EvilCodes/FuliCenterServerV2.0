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

import com.fuli_center.bean.CollectBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findCollects")
public class FindCollectsServlet extends HttpServlet{
	private static final long serialVersionUID = -9031310999717770802L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		// CollectBean[] collects = biz.findCollects(userName, pageId, pageSize);
		List<CollectBean> list = biz.findCollects(userName, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), list);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
