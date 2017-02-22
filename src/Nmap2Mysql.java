import java.sql.SQLException;

public class Nmap2Mysql {

	public static void main(String[] args) throws SQLException {
		String path = "D:/nmap-7.01/nmaplist.xml";
		WriteDB.createDB();		
		System.out.println("waiting...");
		WriteDB.insertDB(path);
		System.out.println("finished");
	}

}
