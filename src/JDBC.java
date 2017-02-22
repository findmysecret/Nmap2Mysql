import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class JDBC {

	public static Connection getConnection(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("can not find the Driver");
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/nmap?useSSL=false";
		String username = "nmap";
		String userpass = "nmap";
		
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url, username, userpass);
		} catch (SQLException e) {
			System.out.println("jdbc connct failed");
			e.printStackTrace();
		}
		
		return conn;
	}
	
//	public static void main(String[] args) {
//		Connection conn = getConnection();
//		System.out.println(conn);
//
//	}
//	
	
}
