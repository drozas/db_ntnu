//import oracle.jdbc.driver.*;
//import oracle.ord.im.OrdImage;

import java.sql.*;

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
    if (args.length != 2) {
      System.out.println("Usage: ImageLoader <versionID> <filename>");
      System.exit(10);
    }
    try {
      // Lag databaseoppkobling
     // Class.forName("oracle.jdbc.driver.OracleDriver");
      //Connection con = DriverManager.getConnection("jdbc:oracle:thin:@elefant.idi.ntnu.no:1521:elefant",
       //                                            "brukernavn", "passord");
		Class.forName(driver);
		Connection con = DriverManager.getConnection(connection_url);
      Statement stmnt = con.createStatement();
      addImage(stmnt, Integer.parseInt(args[0]), args[1]);
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
  public static void addImage(Statement stmnt, int versionID, String filename) throws Exception
  {
	  //drozas: changes for derby
    Connection con = stmnt.getConnection();
    con.setAutoCommit(false);
    // Initialiser f�rst tabellen slik at den allokerer plass til bildet
    //stmnt.executeUpdate("INSERT INTO Utgave(UtgaveID, Bildedata) VALUES(" + versionID + ", ORDSYS.ORDImage.init())");
    stmnt.executeUpdate("INSERT INTO VERSION(IDPicture, StoringDate) VALUES(" + versionID + ", ORDSYS.ORDImage.init())");
    
    // F� tak i OrdImage-objektet som nettopp ble laget
    //drozas: change for derby
    ResultSet ors = stmnt.executeQuery("SELECT Bildedata FROM Utgave WHERE UtgaveID = " +
                                                              versionID + " FOR UPDATE");
    if (ors.next()) {
    	//TODO: see how to get image
    	//OrdImage ordImage = (OrdImage)ors.getCustomDatum(1, OrdImage.getFactory());
      
      ors.close();
      // Legg inn selve bildet
      //ordImage.loadDataFromFile(filename);
      //ordImage.setProperties();
     //drozas: changed for derby
      PreparedStatement st_update =
          (PreparedStatement)con.prepareCall("UPDATE Utgave SET Bildedata = ? WHERE UtgaveID = ?");
      //st_update.setCustomDatum(1, ordImage);
      st_update.setInt(2, versionID);
      st_update.executeUpdate();
      st_update.close();
      con.commit();
      System.out.println(filename + " lastet inn i databasen");
    }
    else
      throw new Exception("Feil under lasting av bilde");
  }
}
