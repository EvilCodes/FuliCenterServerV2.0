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

@WebServlet("/findCollectCount")
public class FindCollectCountServlet extends HttpServlet{
	private static final long serialVersionUID = 2284913644063996219L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter(IFuLiCenterBiz.Collect.USER_NAME);
		int count = biz.findCollectCount(userName);
		ObjectMapper om=new ObjectMapper();
		try {
			MessageBean msg=null;
			if(count>0){
				msg=new MessageBean(true, ""+count);
			}else{
				msg=new MessageBean(false, "查询失败");
			}
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
