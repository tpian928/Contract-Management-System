package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Contract {
	
	static Statement st;
	private static Connection getConnection() {
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
	
	
	private int cid;
	private String cname;
	private String customer;
	private String btime;
	private String etime;
	private String content;
	private String draftmanname;
	
	/**
	 * 初始构造方法
	 * @param cid
	 * @param cname
	 * @param customer
	 * @param btime
	 * @param etime
	 * @param content
	 * @param draftmanname
	 */
	public Contract(int cid, String cname, String customer, String btime,
			String etime, String content, String draftmanname) {
		this.cid = cid;
		this.cname = cname;
		this.customer = customer;
		this.btime = btime;
		this.etime = etime;
		this.content = content;
		this.draftmanname = draftmanname;
		
		//进行合同插入操作，只有这里可以插入合同
		Connection conn = getConnection(); 
		try {
			String sql = "insert into ";
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
	
	/**
	 * 通过合同ID构造
	 * @param cid
	 */
	public Contract(int cid) {
		this.cid = cid;
	}
	
	//以下是get和set
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDraftmanname() {
		return draftmanname;
	}
	public void setDraftmanname(String draftmanname) {
		this.draftmanname = draftmanname;
	}
	
	
}
