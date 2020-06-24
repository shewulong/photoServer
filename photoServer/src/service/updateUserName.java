package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;

public class updateUserName implements Tools {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		String name = json.getString("name");
		String password = json.getString("password");
		int status = BaseDao.updateUserName(uid, name, password);
		jsonArr.clear();
		JSONObject result = new JSONObject();
		result.put("status", status);
		jsonArr.add(result);
		return jsonArr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
