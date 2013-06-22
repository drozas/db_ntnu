
import java.sql.*;


public class ExampleConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driver = "org.apache.derby.jdbc.ClientDriver";
		String db_name = "picturesDB";
		String connection_url = "jdbc:derby://localhost:1527/" + db_name + ";create=true";
		String table = "MI_TABLA";
		try
		{
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connection_url);
		

			try
			{
				Statement stmt= conn.createStatement();
				
				//Example of execution of insert
				//String insert = "INSERT INTO " + table + " VALUES(400, 'CUATROCIENTOS')";
				//stmt.execute(insert);
				
				//Example of select 
				String query = "SELECT * FROM " + table + " WHERE ID>100";
				
				//Execute the select and take the values in a set
				ResultSet rs = stmt.executeQuery(query);
				
				//Show results
				System.out.println("Results of executing " + query + "\n");
				
				while (rs.next())
				{
					int x = rs.getInt("ID");
					String number = rs.getString("NAME");
					
					System.out.println(number + " in Spanish is " + x + "\n");
				}
				
				
			}catch(SQLException e)
			{
				e.printStackTrace();
				
			}finally{
				//We have to close even if there is any exception
				conn.close();
			}
			
		}catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		

	}

}
