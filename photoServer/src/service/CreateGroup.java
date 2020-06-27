package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.BaseDao;
/*
 * 调用BaseDao的createGroup方法
 * 创建分组
 * 返回状态
 */
public class CreateGroup implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		String groupName = json.getString("groupName");
		int status = BaseDao.createGroup(uid, groupName);
		jsonArr.clear();
		JSONObject result = new JSONObject();
		result.put("status", status);
		jsonArr.add(result);
		return jsonArr;
	}

	public static void main(String[] args) {
		JSONArray jsonArr = new JSONArray();
		jsonArr.add(new JSONObject());
		JSONObject json = new JSONObject();
		json.put("uid", "e24da681cd6e401ab3b9813da7d37aed");
		json.put("groupName", "hello");
		jsonArr.add(json);
		BaseDao.init();
		System.out.println(new CreateGroup().work(jsonArr));
	}

}
