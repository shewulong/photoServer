package service;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;

public class QueryByTime implements Tools {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		long timestamp = json.getLongValue("timestamp");
		return BaseDao.queryByTime(uid, timestamp);
	}

	public static void main(String[] args) {
		System.out.println(new Date().getTime());
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new Date().getTime());
	}

}
