package com.itheiam.search.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HttpUtil {

	/**
	 * get请求
	 * 
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String get(String url, String charset) {
		String result = null;
		try {
			// 创建请求发送对象HttpClient
			HttpClient client = new DefaultHttpClient();
			// 创建get
			HttpGet get = new HttpGet(url);
			// 服务端返回数据
			HttpResponse response = client.execute(get);// 发送 没有封装线程的对象
														// 就不需要回调
			InputStream input = response.getEntity().getContent();
			byte[] data = StreamUtils.readInputStream(input);
			result = new String(data, charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String post(String url, List<BasicNameValuePair> params, String charset) {
		String result = null;
		try {
			// 创建请求发送对象HttpClient
			HttpClient client = new DefaultHttpClient();
			// 创建post
			// String url = "http://192.168.32.10:8080/web/LoginServlet";
			HttpPost post = new HttpPost(url);
			// 变量 NameValuePair:Map.Entry
			// NameValuePair username = new BasicNameValuePair("username",
			// "中国ujj");
			// NameValuePair passowrd = new BasicNameValuePair("password",
			// "中国ujj");
			// List<NameValuePair> params = new ArrayList<NameValuePair>();
			// params.add(username);
			// params.add(passowrd);
			UrlEncodedFormEntity form = new UrlEncodedFormEntity(params, "utf-8");
			post.setEntity(form);// 添加表单到请求里，带到服务端
			// 服务端返回数据
			HttpResponse response = client.execute(post);// 发送 没有封装线程的对象
			InputStream input = response.getEntity().getContent();
			byte[] data = StreamUtils.readInputStream(input);
			result = new String(data, charset);
			Log.i("wzx", result);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
