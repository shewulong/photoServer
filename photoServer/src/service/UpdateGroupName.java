package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 调用BaseDao的updateGroupName方法
 * 修改分组名
 * 返回状态
 */
public class UpdateGroupName implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		String newName = json.getString("newName");
		int status = BaseDao.updateGroupName(uid, gid, newName);
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
