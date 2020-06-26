package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;

public class QueryByTyte implements Tools {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		return BaseDao.queryByTyte(uid, gid);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
