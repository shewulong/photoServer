package service;

import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 登录验证
 */
public class Login implements Tools {

	@Override
	public JSONObject work(JSONObject json) {
		String name = json.getString("name");
		String password = json.getString("password");
		JSONObject result = new JSONObject();
		result.put("clazz", this.getClass().toString());
		result.put("rs", BaseDao.login(name, password));
		return result;
	}

	public static void main(String[] args) {
		System.out.println(new Login().getClass().toString());
	}

}
