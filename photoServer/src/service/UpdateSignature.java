package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
/*
 * 调用BaseDao的updateSignature方法
 * 修改用户个性签名
 * 返回状态
 */
public class UpdateSignature implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		String signature = json.getString("signature");
		int status = BaseDao.updateSignature(uid, signature);
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
