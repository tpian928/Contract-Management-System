package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import obj.User;
import func.Security;



public class UserJDBCAction {

	static Statement st;
	
	public static Connection getUserConnection() {
		Connection con = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/cm?useUnicode=true&characterEncoding=UTF-8",
							"root", "wangyifei928");// 创建数据连接,这里需要改成cm
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; 
	}
	
	/**
	 * 添加用户
	 * @param name 用户名
	 * @param password 密码
	 * @return 是否成功添加到User表
	 */
	public static User addUser(String name,String password) {
		
		String idstr = "";
		Connection conn = getUserConnection(); // 同样先要获取连接，即连接到数据库
		String access_taken = Security.RandomString(15);
		String sql = "INSERT INTO user (name, password,access_taken) values('"+name+"', '"+password+"','"+access_taken+"')";
		System.out.println(sql);
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) { // 判断是否还有下一个数据
				int auto_id = rs.getInt(1);
				idstr = auto_id+"";
				System.out.println("auto_id is "+auto_id);
			}
		} catch (SQLException e) {
			idstr="";
			e.printStackTrace();
		}
		User m = new User(idstr, access_taken);
		
		return m;
	}
	

	/**
	 * 登录时进行判断，是不是我的用户
	 * @param name 用户名
	 * @param password 用户密码
	 * @return user表的id,如果没有这个用户则返回空字符串
	 */
	public static User isMyUser(String name,String password) {
		String id="";
		String access_taken = "";
		Connection conn2 = getUserConnection(); // 同样先要获取连接，即连接到数据库
		try {
			
			String sql = "select id,access_taken from user where name='"+name+"' AND password='"+password+"'";
			
			st = (Statement) conn2.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) { 
				id=rs.getString("id");
				access_taken = rs.getString("access_taken");
			}

			conn2.close(); // 关闭数据库连接

		} catch (SQLException e) {

			System.out.println("查询数据失败");
			System.err.println(e);
		}
		
		return new User(id, access_taken);
	}
	
	public static boolean hasFunc() {
		
		
		
		return false;
	}
	
}
