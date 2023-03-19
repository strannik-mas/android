import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tester {
	
	private static final String url = "jdbc:mysql://localhost:3306/users";
	private static final String user = "root";
	private static final String pass = "";
	
	private static Connection con;
	private static Statement statement;
	private static ResultSet result;
	
	private static PreparedStatement prepared;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			con = DriverManager.getConnection(url, user, pass);
			
			statement = con.createStatement();
			
			prepared = con.prepareStatement("select * from users where age > ?");
			result = statement.executeQuery("select * from users");
			
			while(result.next()) {
				String name = result.getString("name");
				System.out.println("name: " + name);
			}
			
			prepared.setInt(1, 26);
			result = prepared.executeQuery();
			System.out.println("=================");
			while(result.next()) {
				String name = result.getString("name");
				System.out.println("name: " + name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
