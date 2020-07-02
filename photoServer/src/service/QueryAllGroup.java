package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
import utils.RsToJson;

public class QueryAllGroup implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		return RsToJson.convert(BaseDao.queryAllGroup(uid));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
