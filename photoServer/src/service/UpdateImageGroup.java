package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 调用BaseDao的updateImageGroup方法
 * 修改图片的所属分组
 * 返回状态
 */
public class UpdateImageGroup implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		long timestamp = json.getLongValue("timestamp");
		int newgid = json.getIntValue("newgid");
		int status = BaseDao.updateImageGroup(uid, gid, timestamp, newgid);
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
