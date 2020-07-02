package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import com.alibaba.fastjson.JSONArray;

import service.PassData;
import utils.ErrorLog;

public class Handle {

	private BufferedReader in;
	private PrintStream out;

	public Handle(BufferedReader in, PrintStream out) {
		super();
		this.in = in;
		this.out = out;
	}

//	处理请求
	public void handle() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		JSONArray jsonArr = receiveJson();
		if(jsonArr == null) return;
		Class<?> clazz = Class.forName(jsonArr.getJSONObject(0).getString("clazz"));
		PassData pd = (PassData) clazz.newInstance();
		sendJson(pd.work(jsonArr));
		pd = null;
		jsonArr = null;
		closeAll();
	}

// 发送json数据
	public void sendJson(JSONArray json) {
		out.println(json.toString());
	}

//	接收json数据
	public JSONArray receiveJson() {
		try {
			String buf = in.readLine();
			if("network check".equals(buf)) {
				out.println("network check");
				return null;
			}
			return JSONArray.parseArray(buf);
		} catch (IOException e) {
			ErrorLog.log(e);
		}
		return null;
	}

//	关闭所有接口
	public void closeAll() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			ErrorLog.log(e);
		}
	}

}
