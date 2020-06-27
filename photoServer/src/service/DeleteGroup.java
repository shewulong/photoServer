package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 调用BaseDao的deleteGroup方法
 * 删除分组
 * 返回状态
 */
public class DeleteGroup implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		int allFlag = json.getIntValue("allFlag");
		int status = BaseDao.deleteGroup(uid, gid, allFlag);
		jsonArr.clear();
		JSONObject result = new JSONObject();
		result.put("status", status);
		jsonArr.add(result);
		return jsonArr;
	}

}
