package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 登录验证
 */
public class Login implements Tools {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String name = json.getString("name");
		String password = json.getString("password");
		jsonArr.clear();
		jsonArr.addAll(BaseDao.login(name, password));
		return jsonArr;
	}

	public static void main(String[] args) {
		BaseDao.init();
		JSONArray jsonArr = new JSONArray();
		JSONObject clazz = new JSONObject();
		JSONObject json = new JSONObject();
		clazz.put("clazz", "service.Login");
		json.put("name", "yuki");
		json.put("password", "123456");
		jsonArr.add(clazz);
		jsonArr.add(json);
		
		System.out.println(new Login().work(jsonArr));
	}

}
