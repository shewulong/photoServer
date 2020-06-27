package service;

import com.alibaba.fastjson.JSONArray;
/*
 * 接收并解析JSONArray数据
 * 调用BaseDao处理
 * 返回处理结果并封装为JSONArray
 */
public interface PassData {
	public JSONArray work(JSONArray jsonArr);
}
