package dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class BaseDao {

	private Connection conn;

	public BaseDao() throws IOException, ClassNotFoundException, SQLException {
//		String driverName = "com.mysql.cj.jdbc.Driver";
//		Class.forName(driverName);
		Properties properties = new Properties();
		InputStream is = new BufferedInputStream(new FileInputStream("config.properties"));
		properties.load(is);
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		conn = DriverManager.getConnection(url, user, password);
	}

//	为每个用户创建一个表格保存数据
	public void createTable(int id) {

	}

//	登录验证
	public int login(String name, String password) {

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

	public void close() throws SQLException {
		conn.close();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		BaseDao bd = new BaseDao();

		bd.close();
	}

}
