package service;

import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;

public class Register implements Tools {

	@Override
	public JSONObject work(JSONObject json) {
		String name = json.getString("name");
		String password = json.getString("password");
		int status = BaseDao.register(name, password);
		JSONObject result = new JSONObject();
		result.put("status", status);
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
