package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import com.alibaba.fastjson.JSONArray;

import service.Tools;

public class Handle {

	private BufferedReader in;
	private PrintStream out;

	public Handle(BufferedReader in, PrintStream out) {
		super();
		this.in = in;
		this.out = out;
	}

	public void handle() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JSONArray json = receiveJson();
		Class<?> clazz = Class.forName(json.getJSONObject(0).getString("clazz"));
		Tools tools = (Tools) clazz.newInstance();
		sendJson(tools.work(json));
		closeAll();
	}

// 发送json数据
	public void sendJson(JSONArray json) {
		out.println(json.toString());
	}

//	接收json数据
	public JSONArray receiveJson() {
		try {
			return JSONArray.parseArray(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	关闭所有接口
	public void closeAll() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
