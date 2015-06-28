package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

	private String name,phone,address,fax,email,bankName,bankAccount,more;
	private int id;
	
	private static Connection getConnection() {
		Connection con = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/cm?useUnicode=true&characterEncoding=UTF-8",
							"root", "wangyifei928");
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; 
	}
	
	/**
	 * 通过以下参数构造Customer自动插入数据库
	 * @param name
	 * @param phone
	 * @param address
	 * @param fax
	 * @param email
	 * @param bankName
	 * @param bankAccount
	 * @param more
	 */
	public Customer(String name, String phone, String address, String fax,
			String email, String bankName, String bankAccount, String more) {
		super();
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.fax = fax;
		this.email = email;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.more = more;
		
		int auto_id = -1;
		
		//进行合同插入操作，只有这里可以插入合同
		Connection conn = getConnection(); 
		try {
			String sql = "insert into customer (name,tel,address,fax,email,bank,account,more) values('"+name+"','"+phone+"','"+address+"','"+fax+"','"+email+"','"+bankName+"','"+bankAccount+"','"+more+"')";
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				while (rs.next()) { 
				    auto_id = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				auto_id=-1;
				e.printStackTrace();
			}
			this.setId(auto_id);
			conn.close(); 
			
		} catch (SQLException e) {
			this.setId(auto_id);
			System.out.println("插入合同失败");
			System.err.println(e);
		}
		
	}
	
	/**
	 * 通过ID得到Customer
	 * @param id
	 */
	public Customer(int id) {
		this.id= id;
		Connection conn = getConnection(); 
		try {
			String sql = "select *from customer where id = '"+id+"'";
			Statement st = (Statement) conn.createStatement(); 
			ResultSet rs = st.executeQuery(sql); 
			while (rs.next()) { 
				this.setName(rs.getString(1));
				this.setAddress(rs.getString(2));
				this.setPhone(rs.getString(3));
				this.setFax(rs.getString(4));
				this.setBankName(rs.getString(5));
				this.setBankAccount(rs.getString(6));
				this.setEmail(rs.getString(7));
				this.setMore(rs.getString(8));
			}
			conn.close(); 
		} catch (SQLException e) {
			System.out.println("查询Customer失败");
			System.err.println(e);
		}
		
	}
	
	//以下自动生成
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
