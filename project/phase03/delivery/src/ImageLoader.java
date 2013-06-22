import java.sql.*;

import java.io.*;

// M� gj�res:
// * Legge inn rett brukernavn/passord i main()
// * Legge inn riktige tabellnavn i addImage()

/** @author Jon Olav Hauglid */
public class ImageLoader
{
  /**
   * Eksempel p� bruk av ImageLoader.addImage()
   *
   * @param args        Kommandolinje-parametre. Tar ID som 1. parameter og
   *                    bildefilnavn som 2.
   */
	
	//drozas: added for derby
	private static String driver = "org.apache.derby.jdbc.ClientDriver";
	private static String db_name = "picturesDB";
	private static String connection_url = "jdbc:derby://localhost:1527/" + db_name + ";create=true";
  public static void main(String args[])
  {
    if (args.length != 3) {
      System.out.println("Usage: ImageLoader <versionID> <date> <path>");
      System.exit(10);
    }
    try {

		Class.forName(driver);
		Connection con = DriverManager.getConnection(connection_url);
		Statement stmnt = con.createStatement();
		addImage(stmnt, args[0], args[1], args[2]);
		stmnt.close();
     	con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Metode som kan brukes til � legge bilder inn i databasen.
   * Inneholder SQL-kode som m� tilpasses den datamodell som brukes.
   *
   * @param stmnt       JDBC-statement for � kontakte databasen med.
   * @param versionID   ID som utgaven skal f� i databasen. M� ikke finnes fra f�r.
   * @param filename    Filnavn til bildet som skal legges inn i databasen.
   * @throws Exception  Dersom bildet ikke ble lastet inn i databasen.
   */
  public static void addImage(Statement stmnt, String storingDate, String idPicture,  String path) throws Exception
  {

    Connection con = stmnt.getConnection();
    //Prepare update over the blob colum
    PreparedStatement statement = con.prepareStatement("UPDATE Version SET data = (?) " +
    		"WHERE storingDate = '"+ storingDate + "' and idPicture= '" + idPicture + "'");

    //Access to the file itself
    String img_path= path + storingDate + "-" + idPicture;
    File imageFile = new File(img_path);
    InputStream in = new FileInputStream( imageFile );

    statement.setBinaryStream(1, in, (int) imageFile.length());
    statement.executeUpdate();
    

  }
}
