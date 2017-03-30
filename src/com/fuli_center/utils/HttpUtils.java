package com.fuli_center.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public final class HttpUtils {
	public static final int METHOD_GET=0;
	public static final int METHOD_POST=1;
	public static final int METHOD_GET_NO_DELAY=2;
	private static HttpClient mClient;
	private static final String UTF_8="utf-8";
	public static HttpEntity getEntity(String uri,ArrayList<BasicNameValuePair> params,int method) throws ClientProtocolException, IOException{
		mClient=new DefaultHttpClient();
		HttpUriRequest request=null;
		switch (method) {
		case METHOD_GET:
		case METHOD_GET_NO_DELAY:
			StringBuilder sb=new StringBuilder(uri);
			if(params!=null && !params.isEmpty()){
			    sb.append("?");
				for (BasicNameValuePair param : params) {
					sb.append(param.getName()).append("=")
					  .append(URLEncoder.encode(param.getValue(),UTF_8)).append("&");
				}
				sb.deleteCharAt(sb.length()-1);
			}
			request=new HttpGet(sb.toString());			
			break;
		case METHOD_POST:
			HttpPost post=new HttpPost(uri);
			if(params!=null && !params.isEmpty()){
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"UTF_8");
				post.setEntity(entity);
			}
			request=post;
			break;
		}
		if(method!=METHOD_GET_NO_DELAY){
			mClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			mClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
		}
		HttpResponse response = mClient.execute(request);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			return response.getEntity();
		}
		return null;
	}
	
	public static final InputStream getInputStream(String uri,ArrayList<BasicNameValuePair> params,int method) throws IllegalStateException, ClientProtocolException, IOException{
		HttpEntity entity = getEntity(uri, params, method);
		if(entity==null){
			return null;
		}
		return entity.getContent();
	}
	
	public static void closeClient(){
		if(mClient!=null){
			mClient.getConnectionManager().shutdown();
		}
	}
}
