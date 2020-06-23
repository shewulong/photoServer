package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ServerHandle {

	private DataInputStream dis;
	private DataOutputStream dos;

	public ServerHandle(DataInputStream dis, DataOutputStream dos) {
		super();
		this.dis = dis;
		this.dos = dos;
	}
	
	public void handle() {
		
		closeAll();
	}
	
	// 发送json数据
		public void sendJson(JSONObject json) {
			try {
				dos.write(json.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		接收json数据
		public JSONObject receiveJson() {
			byte[] buf = new byte[1024 * 8];
			try {
				dis.read(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return JSON.parseObject(new String(buf));
		}

//	进行选择性处理
	public void select() {

	}

//	登录验证
	public int login(Map map) {

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
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
