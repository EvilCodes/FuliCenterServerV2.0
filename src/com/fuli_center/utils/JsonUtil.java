package com.fuli_center.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.fuli_center.bean.Result;

public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	public static void writeJsonToClient(Result result , HttpServletResponse response){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			mapper.writeValue(pw, result);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
}
