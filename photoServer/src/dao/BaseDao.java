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
			if (!rs.next()) {
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
			insertGroup(uid, "默认");
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
	public static int updateAvatar(String uid, byte[] img) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "update user set avatar=? where uid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setBytes(1, img);
			pstemt.setString(2, uid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	新建分组
	public static int insertGroup(String uid, String groupName) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select gname from groups where gname=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, groupName);
			rs = pstemt.executeQuery();
			if(rs.next() && groupName.equals(rs.getString("gname"))) {
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
			sql = "insert into groups (uid,gid,gname,imgcount) values(?,?,?,?);";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.setString(3, groupName);
			pstemt.setInt(4, 0);
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
			sql = "update groups set imgcount=imgcount-1 where uid=? and gid=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.executeUpdate();
			sql = "update groups set imgcount=imgcount+1 where uid=? and gid=?";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, newgid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	删除分组
	public static int deleteGroup(String uid, int gid, int all) {
		if(gid == 1) return 0;
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "delete from groups where uid=? and gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.execute();
			if(all == 1) {
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
			sql = "select count(*) as count from images where uid=? and gid=1;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			ResultSet rs = pstemt.executeQuery();
			int num = 0;
			while(rs.next()) {
				num = rs.getInt("count");
			}
			sql = "update groups set imgcount=? where uid=? and gid=1;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, num);
			pstemt.setString(2, uid);
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
		if(gid == 1) return 0;
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
	public static int saveImage(long timestamp, String uid, int gid, byte[] img) {
		PreparedStatement pstemt = null;
		String sql = null;
		try {
			sql = "insert into images (timestamp,uid,gid,image) values(?,?,?,?);";
			pstemt = conn.prepareStatement(sql);
			pstemt.setLong(1, timestamp);
			pstemt.setString(2, uid);
			pstemt.setInt(3, gid);
			pstemt.setBytes(4, img);
			pstemt.execute();
			sql = "update groups set imgcount=imgcount+1 where uid=? and gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.executeUpdate();
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
			sql = "update groups set imgcount=imgcount-1 where uid=? and gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setString(1, uid);
			pstemt.setInt(2, gid);
			pstemt.executeUpdate();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

//	按类型查询图片
	public static JSONArray queryByTyte(String uid, int gid) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from images where gid=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setInt(1, gid);
			rs = pstemt.executeQuery();
			return RsToJson.convert(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

//	按时间戳查询图片（完整）
	public static JSONArray queryByTime(String uid, long timestamp) {
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "select * from images where timestamp=?;";
			pstemt = conn.prepareStatement(sql);
			pstemt.setLong(1, timestamp);
			rs = pstemt.executeQuery();
			return RsToJson.convert(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		BaseDao.init();
//		System.out.println(BaseDao.login("mafu", "123456"));
//		System.out.println(BaseDao.register("mafu", "123456"));
//		System.out.println(BaseDao.insertGroup("99d0555fbbbb4700ac0ede33b5202660", "hello"));
//		FileInputStream fis = new FileInputStream("./images/yui.jpg");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		int b;
//		while((b = fis.read()) != -1) {
//			baos.write(b);
//		}
//		byte[] img = baos.toByteArray();
//		System.out.println(BaseDao.saveImage(new Date().getTime(), "99d0555fbbbb4700ac0ede33b5202660", 2, img));
//		System.out.println(BaseDao.updateAvatar("99d0555fbbbb4700ac0ede33b5202660", img));

//		JSONArray jsonArr = BaseDao.queryByTyte("ed84e2add80a4451b89f11350295f0aa", 1);
//		JSONArray jsonArr = BaseDao.queryByTime("99d0555fbbbb4700ac0ede33b5202660", 1592997639870L);
//		for (int i = 0; i < jsonArr.size(); i++) {
//			JSONObject json = jsonArr.getJSONObject(i);
//			FileOutputStream fos = new FileOutputStream("./images/down.jpg");
//			byte[] img = json.getBytes("image");
//			System.out.println(json);
//			fos.write(img, 0, img.length);
//			fos.close();
//		}

//		System.out.println(BaseDao.deleteImage("99d0555fbbbb4700ac0ede33b5202660", 1, 1592997633802L));
//		System.out.println(BaseDao.updateGroupName("99d0555fbbbb4700ac0ede33b5202660", 2, "good"));
//		System.out.println(BaseDao.deleteGroup("99d0555fbbbb4700ac0ede33b5202660", 2, 0));
//		System.out.println(BaseDao.updateImageGroup("99d0555fbbbb4700ac0ede33b5202660", 2, 1592997639870L, 1));
//		System.out.println(BaseDao.updateSignature("99d0555fbbbb4700ac0ede33b5202660", "hello world"));
		BaseDao.close();

	}

}
