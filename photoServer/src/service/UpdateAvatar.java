package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 调用BaseDao的updateAvatar方法
 * 修改用户头像
 * 返回状态
 */
public class UpdateAvatar implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		String avaname = json.getString("avaname");
		byte[] image = json.getBytes("image");
		int status = BaseDao.updateAvatar(uid, avaname, image);
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
