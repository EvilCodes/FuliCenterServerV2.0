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

import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;

@WebServlet("/findNewAndBoutiqueGoods")
public class FindNewAndBoutiqueGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int catId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID));
		int pageId=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_ID));
		int pageSize=Integer.parseInt(request.getParameter(IFuLiCenterBiz.PAGE_SIZE));
		NewGoodBean[] goods = biz.findNewAndBoutiqueGoods(catId, pageId, pageSize);
		ObjectMapper om=new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), goods);
			for (NewGoodBean good : goods) {
				System.out.println(good.getGoodsEnglishName());
			}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
