package dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;

import utils.RsToJson;

public class BaseDao {

	private static Connection conn = null;

	public static void init() {
		try {
			Properties properties = new Properties();
			InputStream is;
			is = new BufferedInputStream(new FileInputStream("config.properties"));
			properties.load(is);
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	为每个用户创建一个表格保存图片路径
	public void createImgTab(String uid) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "create table ? ("
					+ "timestamp bigint not null,"
					+ "gid int not null,"
					+ "name varchar(32) not null,"
					+ "path varchar(32) not null,"
					+ "primary key (timestamp)"
					+ ");";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

//	登录验证
	public static JSONArray login(String name, String password) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select password from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, name);
			rs = pstemt.executeQuery();
			if(!rs.next()) {
				return new JSONArray();
			}
			if (!password.equals(rs.getString(1))) {
				return new JSONArray();
			}

			sql = "select * from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, name);
			rs = pstemt.executeQuery();
			return RsToJson.convert(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	注册用户并验证用户名可用性
	public static int register(String name, String password) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, name);
			rs = pstemt.executeQuery();
			if(rs.next()) {
				return 0;
			}

			String uid = UUID.randomUUID().toString().replace("-", "");
			sql = "insert into user (uid,name,password) values(?,?,?)";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setString(2, name);
			pstemt.setString(3, password);
			pstemt.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	修改用户名和密码
	public static int updateUserName(String uid, String newName, String newPassword) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, newName);
			rs = pstemt.executeQuery();
			if(rs.next()) {
				return 0;
			}
			sql = "update user set name=?,password=? where uid=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, newName);
			pstemt.setString(2, newPassword);
			pstemt.setString(3, uid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	修改个性签名
	public static int updateSignature(String uid, String signature) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "update user set signature=? where uid=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, signature);
			pstemt.setString(2, uid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	修改头像
	public static void updateAvatar(String uid, byte[] img) {

	}

//	新建分组
	public static int insertGroup(String uid, String groupName) {

		return 0;
	}

//	删除分组
	public static void deleteGroup(String uid, int gid) {

	}

//	修改组名
	public static int updateGroupName(String uid, int gid, String newName) {

		return 0;
	}

//	插入照片
	public static void insertImage(String uid, int gid, String name, byte[] img) {
		long timestamp = new Date().getTime();
	}

//	删除照片
	public static void deleteImage(String uid, long timestamp) {

	}

//	按类型查询图片（缩略图）
	public static ResultSet queryByTyte(String uid, int gid) {

		return null;
	}

//	按时间戳查询图片（完整）
	public static ResultSet queryByTime(String uid, long timestamp) {

		return null;
	}

	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
//		BaseDao.init();
//		System.out.println(BaseDao.login("yuki", "123456"));
//		System.out.println(BaseDao.register("yuki", "123456"));

	}

}
