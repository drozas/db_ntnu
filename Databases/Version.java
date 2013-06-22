//import oracle.jdbc.driver.OracleResultSet;
//import oracle.ord.im.OrdImage;


import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;

// M� gj�res:
// * Legge inn riktige tabellnavn i getImage()

/** @author Jon Olav Hauglid */
public class Version
{
  private int versionID;      // ID-en til denne utgaven
  private String description; // Beskrivelsen til denne utgaven

  /**
   * Kalles av Picture.fillVersionListModel n�r nye utgaver skal lages.
   *
   * @param versionID     ID-en til utgaven
   * @param description   Beskrivelsen til utgaven
   */
  public Version(int versionID, String description)
  {
    this.versionID = versionID;
    this.description = description;
  }

  /**
   * N�r en utgave blir valgt i brukergrensesnittet kalles denne metoden
   * for � hente bildedata fra databasen.
   *
   * @param stmnt       JDBC-statement for � kontakte databasen med.
   * @return            Imageobjekt som kan brukes i brukergrensesnittet.
   * @throws Exception  Hvis bildedata ikke finnes eller noe databasetr�bbel oppst�r.
   */
  public Image getImage(Statement stmnt) throws Exception
  {
    // Hent bildedata fra databasen
	  //drozas: changed for derby
	  
    ResultSet osr = stmnt.executeQuery("SELECT Bildedata FROM Utgave WHERE UtgaveID = " + versionID);
    if (osr.next()) {
    	//TODO: see how to get image
      //OrdImage ordImage = (OrdImage)osr.getCustomDatum(1, OrdImage.getFactory());
      //return Toolkit.getDefaultToolkit().createImage(ordImage.getDataInByteArray()); // Konstruer GUI-bilde-objekt
    	return null;
    }
    else
      throw new Exception("Fant ikke utgave med ID=" + versionID);
  }

  /**
   * Kalles av utgavelista n�r den skal finne ut hva slags tekststreng
   * hvert av elementene i lista skal vises ved.
   *
   * @return Tekststring som identifiserer utgaven.
   */
  public String toString()
  {
    if (description != null) // Hvis utgaven har en beskrivelse, viser vi denne i lista
      return description;
    return String.valueOf(versionID); // Hvis ikke, viser vi ID-en i stedet.
  }
}
