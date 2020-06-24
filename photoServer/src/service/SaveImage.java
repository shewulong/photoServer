package service;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;

public class SaveImage implements Tools {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		long timestamp = new Date().getTime();
		String uid = json.getString("uid");
		int gid = json.getInteger("gid");
		byte[] image = json.getBytes("image");
		int status = BaseDao.saveImage(timestamp, uid, gid, image);
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
