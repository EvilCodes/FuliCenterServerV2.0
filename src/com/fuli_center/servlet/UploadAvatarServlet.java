package com.fuli_center.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.Message;
import com.fuli_center.biz.FuLiCenterBiz;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.utils.I;

@WebServlet("/uploadAvatar")
public class UploadAvatarServlet extends HttpServlet{

	private static final long serialVersionUID = 7874161528899601616L;
	private IFuLiCenterBiz biz = new FuLiCenterBiz();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		String type = request.getParameter(I.AVATAR_TYPE);
		if(type.equals(I.AVATAR_TYPE_USER_PATH)) {
			String userName = request.getParameter(I.User.USER_NAME);
			isSuccess = Server.uploadAvatar(userName, I.AVATAR_TYPE_USER, request);
		} else {
			String hxid = request.getParameter(I.Group.HX_ID);
			isSuccess = Server.uploadAvatar(hxid, I.AVATAR_TYPE_GROUP, request);
		}
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_UPLOAD_AVATAR_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
