package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findBoutiques")
public class FindBoutiquesServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoutiqueBean[] boutiques = biz.findBoutiques();
		ObjectMapper om=new ObjectMapper();
		try {
			if(boutiques!=null){
				om.writeValue(response.getOutputStream(), boutiques);
				System.out.println("精选首页数据下载完成");
			}else{
				System.out.println("精选首页数据读取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
