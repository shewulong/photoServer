package net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import service.Tools;

public class ServerHandle {

	private BufferedReader in;
	private PrintStream out;

	public ServerHandle(BufferedReader in, PrintStream out) {
		super();
		this.in = in;
		this.out = out;
	}

	public void handle() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		JSONObject json = receiveJson();
		Class<?> clazz = Class.forName(json.getString("clazz"));
		Tools tools = (Tools) clazz.newInstance();
		sendJson(tools.work(json));
		closeAll();
	}

// 发送json数据
	public void sendJson(JSONObject json) {
		out.println(json.toString());
	}

//	接收json数据
	public JSONObject receiveJson() {
		try {
			return JSON.parseObject(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	登录验证
	public int login(JSONObject json) {

		return 0;
	}

//	注册用户并验证用户名可用性
	public int Register(String name, String password) {
		System.out.println(UUID.randomUUID().toString().replace("-", ""));
		return 0;
	}

//	修改用户名和密码
	public int updateUserName(String uid, String newName, String newPassword) {

		return 0;
	}

//	修改个性签名
	public void updateSignature(String uid, String signature) {

	}

//	修改头像
	public void updateAvatar(String uid, byte[] img) {

	}

//	新建分组
	public int insertGroup(String uid, String groupName) {

		return 0;
	}

//	删除分组
	public void deleteGroup(String uid, int gid) {

	}

//	修改组名
	public int updateGroupName(String uid, int gid, String newName) {

		return 0;
	}

//	插入照片
	public void insertImage(String uid, int gid, String name, byte[] img) {
		long timestamp = new Date().getTime();
	}

//	删除照片
	public void deleteImage(String uid, long timestamp) {

	}

//	按类型查询图片（缩略图）
	public ResultSet queryByTyte(String uid, int gid) {

		return null;
	}

//	按时间戳查询图片（完整）
	public ResultSet queryByTime(String uid, long timestamp) {

		return null;
	}

//	关闭所有接口
	public void closeAll() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
