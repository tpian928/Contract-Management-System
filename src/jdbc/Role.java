package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import obj.User;

public class Role {

	static Statement st;
	static Statement st2;
	
	private String roleid;
	private String rolename;
	
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
	
	/**
	 * 添加角色
	 * @param name 角色名称
	 * @param desc 角色描述
	 * @return
	 */
	public static boolean add(String name,String desc) {
		boolean theresult = false;	

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

		
		System.out.println("add user result is "+theresult);
		
		return theresult;
	}
	
	/**
	 * 添加角色
	 * @param name
	 * @param desc
	 * @param funcArr
	 * @return
	 */
	public boolean add(String name,String desc,ArrayList<Integer> funcArr) {
		boolean theresult = false;	

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
		
		resetRolesFunc(name, funcArr);
		
		return theresult;
	}
	
	/**
	 * 给角色添加功能
	 * @param function_id 功能id
	 * @param role_id 角色id
	 */
	private boolean addFuncToRole(int function_id,int role_id) {
		boolean theresult = false;	

		Connection conn = getRoleConnection(); 
		try {
			
			String sql = "insert into role_has_function (function_id,role_id) values('"+function_id+"','"+role_id+"') ";
			st = (Statement) conn.createStatement();
			int resultnum = st.executeUpdate(sql);
			
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

		return theresult;
	}

	/**
	 * 删除一个角色的所有功能记录
	 * @param role_id
	 */
	private void deleteRoleFunc(int role_id) {

		Connection conn = getRoleConnection(); 
		try {
			
			String sql = "delete from role_has_function where role_id='"+role_id+"'";
			st = (Statement) conn.createStatement();
			st.executeUpdate(sql);
			conn.close(); // 关闭数据库连接

		} catch (SQLException e) {
			System.out.println("错误");
			System.err.println(e);
		}
	}
	
	/**
	 * 重设一个角色的功能
	 * @return
	 */
	public void resetRolesFunc(int role_id,ArrayList<Integer> funcArr) {

		deleteRoleFunc(role_id);
		for(Integer tmp:funcArr){
			addFuncToRole(tmp, role_id);
		}

	}
	
	public void resetRolesFunc(String role_name,ArrayList<Integer> funcArr) {
		
		int role_id = 0;
		
		Connection conn = getRoleConnection(); 
		try {
			String sql = "select id from role where name='"+role_name+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				role_id = rs.getInt("id");
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询role_id失败");
			System.err.println(e);
		}
		
		deleteRoleFunc(role_id);
		for(Integer tmp:funcArr){
			addFuncToRole(tmp, role_id);
		}

	}
	
	/**
	 * 返回某个角色的的功能ID数组
	 * @param role_id 角色ID
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArrayList<Integer> getAllFunc(int role_id) {
		ArrayList<Integer> funcArr = new ArrayList<Integer>();
		Connection conn = getRoleConnection(); 
		try {
			String sql = "select function_id from role_has_function where role_id='"+role_id+"'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				funcArr.add(rs.getInt("function_id"));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询数据失败");
			System.err.println(e);
		}
		return funcArr;
	}
	
	/**
	 * 获取用户拥有的功能
	 * @param uid
	 * @return
	 */
	public static Set<Integer> getUserFunc(String uid) {
		
		Set<Integer> funcArr = new HashSet<>();
		
		Connection conn = getRoleConnection(); 
		try {
			String sql = "select function_id from role_has_function where role_id in (select role_id from user_has_role where user_id = '"+uid+"')";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				funcArr.add(rs.getInt("function_id"));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询数据失败");
			System.err.println(e);
		}
		
		return funcArr;
	}
	
	public static boolean hasThisFunc(String uid,int func_id) {
		
		Set<Integer> funcArr = getUserFunc(uid);
		return funcArr.contains(func_id);
	}
	
	public static Set<User> getUsers(String username) {
		
		if (username==null) {
			username="";
		}
		
		Set<User> userSet = new HashSet<User>();
		
		Connection conn = getRoleConnection(); 
		try {
			String sql = "select id,name from user where name like '%"+username+"%'";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				User mUser = new User();
				int theid = rs.getInt("id");
				mUser.setId(theid+"");
				mUser.setName(rs.getString("name"));
				String sql2 = "select name from role where id in (select role_id from user_has_role where user_id = '"+theid+"')";
				st2 = (Statement) conn.createStatement(); 
				ResultSet rs2 = st2.executeQuery(sql2); 
				Set<String> roleNameSet = new HashSet<String>();
				while (rs2.next()) {
					roleNameSet.add(rs2.getString("name"));
				}
				mUser.setRoleNameSet(roleNameSet);
				userSet.add(mUser);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("失败");
			System.err.println(e);
		}
		
		return userSet;
	}
	
	/**
	 * 得到数据库中的所有角色
	 * @return
	 */
	public Set<Role> getRoles() {
		Set<Role> roleSet = new HashSet<Role>();
		
		Connection conn = getRoleConnection(); 
		try {
			String sql = "select *from role";
			st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				Role mRole = new Role();
				mRole.setRoleid(rs.getInt("id")+"");
				mRole.setRolename(rs.getString("name"));
				roleSet.add(mRole);
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询角色失败");
			System.err.println(e);
		}
		
		return roleSet;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	
}
