package service;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
import utils.RsToJson;
/*
 * 调用BaseDao的queryByTime方法
 * 按时间戳查询图片
 * 返回图片信息
 */
public class QueryByTime implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		long timestamp = json.getLongValue("timestamp");
		return RsToJson.convert(BaseDao.queryByTime(uid, timestamp));
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
