package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import service.Tools;

public class RsToJson {

	public static JSONArray convert(ResultSet rs) throws SQLException {
		JSONArray jsonArr = new JSONArray();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject json = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String key = metaData.getColumnLabel(i);
				if ("java.lang.Long".equals(metaData.getColumnClassName(i))) {
					long value = rs.getLong(key);
					json.put(key, value);
				} else if ("java.lang.String".equals(metaData.getColumnClassName(i))) {
					String value = rs.getString(key);
					json.put(key, value);
				} else if("java.lang.Integer".equals(metaData.getColumnClassName(i))) {
					int value = rs.getInt(key);
					json.put(key, value);
				} else if("[B".equals(metaData.getColumnClassName(i))) {
					byte[] value = rs.getBytes(key);
					json.put(key, value);
				}
			}
			jsonArr.add(json);
		}
		return jsonArr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
