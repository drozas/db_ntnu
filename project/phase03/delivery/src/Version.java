import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;

// M� gj�res:
// * Legge inn riktige tabellnavn i getImage()

/** @author Jon Olav Hauglid */
public class Version
{

	private String storingDate;
	private String idPicture;
	private String description; 

  
  public Version(String storingDate, String idPicture, String description)
  {
    this.storingDate = storingDate;
    this.idPicture = idPicture;
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
	
	  //Select the version
	  String query = "SELECT data FROM Version WHERE IDPICTURE = '" + idPicture + 
		"' and STORINGDATE= '" + storingDate + "'";

	  
	  ResultSet rs = stmnt.executeQuery(query);

	
	  
	  	if (rs.next()) 
	  	{
	  		//Recover first element of class blob
	    	Blob blob = rs.getBlob(1);
	    	FileOutputStream out = new FileOutputStream(storingDate + "-" + idPicture);
	    	BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream()); 
	    	
	    	  int b; 
	    	  byte[] buffer = new byte[10240]; // 10kb buffer
	    	  while ((b = in.read(buffer, 0, 10240)) != -1) { 
	    	     out.write(buffer, 0, b); 
	    }
  

    	return Toolkit.getDefaultToolkit().createImage(blob.getBytes(1, (int)blob.length()));


    }
    else
      throw new Exception("There is not picture with idpicture=" + idPicture + " and storingdate=" + storingDate);
  }

  /**
   * Kalles av utgavelista n�r den skal finne ut hva slags tekststreng
   * hvert av elementene i lista skal vises ved.
   *
   * @return Tekststring som identifiserer utgaven.
   */
  public String toString()
  {
    if (description != null) 
      return description;
    return this.idPicture + this.storingDate; 
  }
}
