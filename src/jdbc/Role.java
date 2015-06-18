package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Role {

	private boolean hasRights = false;
	static Statement st;
	
	public Role(String pw) {
		if (pw.equals("miye8eth2rarimu")) {
			this.hasRights=true;
		}
		else {
			this.hasRights=false;
		}
	}
	
	public static Connection getRoleConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			con = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/cm?useUnicode=true&characterEncoding=UTF-8",
							"root", "wangyifei928");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}
	
	
	public boolean add(String name,String desc) {
		boolean theresult = false;	
		if (hasRights) {
			Connection conn = getRoleConnection(); 
			try {
				
				String sql = "insert into role (name,description) values('"+name+"','"+desc+"')";
				st = (Statement) conn.createStatement();
				int resultnum = st.executeUpdate(sql);
				System.out.println(resultnum);
				if(resultnum==1){
					theresult=true;
				}
				else{
					theresult=false;
				}

				conn.close(); // 关闭数据库连接

			} catch (SQLException e) {
				theresult=false;
				System.out.println("查询数据失败");
				System.err.println(e);
			}
		}
		
		System.out.println("add user result is "+theresult);	
		return theresult;
	}
	
	
	
	
}
