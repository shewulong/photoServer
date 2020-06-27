package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dao.BaseDao;
import utils.RsToJson;
/*
 * 调用BaseDao的queryByType
 * 按图片类型编号查询
 * 返回图片信息
 */
public class QueryByTyte implements PassData {

	@Override
	public JSONArray work(JSONArray jsonArr) {
		JSONObject json = jsonArr.getJSONObject(1);
		String uid = json.getString("uid");
		int gid = json.getIntValue("gid");
		return RsToJson.convert(BaseDao.queryByTyte(uid, gid));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
