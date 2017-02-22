import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

public class WriteDB {

	/*
	 * 创建数据库
	 */
	public static void createDB() throws SQLException{
		String create_sql = "CREATE TABLE IF NOT EXISTS `nmaplist` "
				+ "(  `id` int(99) NOT NULL AUTO_INCREMENT,  `ip` varchar(32) DEFAULT NULL,  `port` varchar(5) DEFAULT NULL,"
				+ "  `protocol` varchar(8) DEFAULT NULL,  `state` varchar(8) DEFAULT NULL,  `reason` varchar(64) DEFAULT NULL,"
				+ "  `service` varchar(128) DEFAULT NULL,  `product` varchar(128) DEFAULT NULL,  `version` varchar(128) DEFAULT NULL,"
				+ "  `extrainfo` varchar(255) DEFAULT NULL,  PRIMARY KEY (`id`),  KEY `iport` (`ip`,`port`) USING BTREE)"
				+ " ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
		Connection conn = JDBC.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute(create_sql);
	}
	
	/*
	 * 批量插入数据库
	 */
	public static boolean insertDB(String path){
		Connection conn = JDBC.getConnection();
		List nmaplist =  ReadXML.getContent(path);
		Iterator it = nmaplist.iterator();
		String[] array;
		try{
			conn.setAutoCommit(false);
			PreparedStatement pstmt;
			String sql = "INSERT nmaplist(ip,port,protocol,state,reason,service,product,version,extrainfo) VALUES(?,?,?,?,?,?,?,?,?)";
			
			pstmt = (PreparedStatement) conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
			while(it.hasNext()){
				array = it.next().toString().split("'");
				for(int i = 1;i<=array.length;i++){
					System.out.println(array[i-1]);
					pstmt.setString(i, array[i-1]);
					
				}
				pstmt.addBatch();  
			}
			pstmt.executeBatch();
			conn.commit();
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}


}
