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
import java.util.Properties;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;

import utils.RsToJson;

public class BaseDao {

	private static Connection conn = null;
//	初始化连接
	public static void init() {
		try {
			Properties properties = new Properties();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("config.properties"));
			properties.load(bis);
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			conn = DriverManager.getConnection(url, user, password);
			bis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	登录验证
	public static ResultSet login(String name, String password) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select password from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, name);
			rs = pstemt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			if (!password.equals(rs.getString(1))) {
				return null;
			}

			sql = "select * from user where name=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, name);
			return pstemt.executeQuery();
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
			if (rs.next()) {
				return 0;
			}
			String uid = UUID.randomUUID().toString().replace("-", "");
			sql = "insert into user (uid,name,password,gcount) values(?,?,?,0);";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setString(2, name);
			pstemt.setString(3, password);
			pstemt.execute();
			createGroup(uid, "默认");
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
			if (rs.next()) {
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

//	新建分组
	public static int createGroup(String uid, String groupName) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select gname from groups where uid=? and gname=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setString(2, groupName);
			rs = pstemt.executeQuery();
			if (rs.next()) {
				return 0;
			}
			sql = "select gcount from user where uid=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			rs = pstemt.executeQuery();
			int gid = 0;
			while (rs.next()) {
				gid = rs.getInt("gcount") + 1;
			}
			sql = "insert into groups (uid,gid,gname) values(?,?,?);";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.setString(3, groupName);
			pstemt.execute();
			sql = "update user set gcount=gcount+1 where uid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	修改图片所属分组
	public static int updateImageGroup(String uid, int gid, long timestamp, int newgid) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "update images set gid=? where uid=? and gid=? and timestamp=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, newgid);
			pstemt.setString(2, uid);
			pstemt.setInt(3, gid);
			pstemt.setLong(4, timestamp);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	删除分组
	public static int deleteGroup(String uid, int gid, int allFlag) {
		if (gid == 1)
			return 0;
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "delete from groups where uid=? and gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.execute();
			if (allFlag == 1) {
				sql = "delete from images where uid=? and gid=?;";
				pstemt = conn.prepareStatement(sql);
				pstemt.setString(1, uid);
				pstemt.setInt(2, gid);
				pstemt.execute();
			}
			sql = "update images set gid=1 where gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, gid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	修改组名
	public static int updateGroupName(String uid, int gid, String newName) {
		if (gid == 1)
			return 0;
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "update groups set gname=? where uid=? and gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, newName);
			pstemt.setString(2, uid);
			pstemt.setInt(3, gid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	保存照片
	public static int saveImage(long timestamp, String uid, int gid, String name, byte[] image) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "insert into images (timestamp,uid,gid,name,image) values(?,?,?,?,?);";
			pstemt = conn.prepareStatement(sql);
			pstemt.setLong(1, timestamp);
			pstemt.setString(2, uid);
			pstemt.setInt(3, gid);
			pstemt.setString(4, name);
			pstemt.setBytes(5, image);
			pstemt.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	删除照片
	public static int deleteImage(String uid, int gid, long timestamp) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "delete from images where gid=? and timestamp=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, gid);
			pstemt.setLong(2, timestamp);
			pstemt.execute();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	按分组查询图片
	public static ResultSet queryByTyte(String uid, int gid) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "select * from images where gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, gid);
			return pstemt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	按时间戳查询图片
	public static ResultSet queryByTime(String uid, long timestamp) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from images where timestamp=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setLong(1, timestamp);
			return pstemt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	修改个性签名
	public static int updateSignature(String uid, String signature) {
		PreparedStatement pstemt = null;
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
	public static int updateAvatar(String uid, String avaname, byte[] image) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "update user set avaname=?,avatar=? where uid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, avaname);
			pstemt.setBytes(2, image);
			pstemt.setString(3, uid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
//	关闭连接
	public static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
	}

}
