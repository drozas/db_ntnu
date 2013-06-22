import javax.swing.*;
import java.awt.*;
import java.sql.*;

// M� gj�res:
// * Implementere begge fillPictureListModel()
// * Implementere showDetails()
// * Implementere fillVersionListModel()

/** @author Jon Olav Hauglid */
public class Picture
{
  private static JTextField textFields[] = new JTextField[8]; // Tekstkomponenter for visning av detaljer om bilder
  private String title;                                       // Tittelen til dette bildet

  /**
   * Brukes til � fylle listemodellen til bildelista med innhold.
   * Innholdet skal v�re instanser av klassen Picture.
   *
   * @param stmnt         JDBC-statement for � kontakte databasen med.
   * @param modelPictures Listmodellen som skal fylles med Picture-objekter.
   * @throws SQLException Hvis noe gikk galt med databasekommunikasjonen.
   */
  public static void fillPictureListModel(Statement stmnt, DefaultListModel modelPictures) throws SQLException
  {
	  	//Remove previous elements
	  	modelPictures.removeAllElements();
		
		String query = "SELECT * FROM Picture";
		
		//Execute the select and take the values in a set
		ResultSet rs = stmnt.executeQuery(query);
		
		//Add all the pictures to the list
		while (rs.next())
		{
			Picture pic = new Picture(rs.getString("TITLE"));
			modelPictures.addElement(pic);
		}
		
  }

  /**
   * Brukes til � fylle listemodellen til bildelista med innhold.
   * 
   * Innholdet skal v�re instanser av klassen Picture.
   * Denne metoden kalles n�r lista skal fylles p� bakgrunn av et s�keuttrykk.
   *
   * @param stmnt         JDBC-statement for � kontakte databasen med.
   * @param modelPictures Listmodellen som skal fylles med Picture-objekter.
   * @param searchString  Tekststrengen som det skal matches p� i databasen.
   * @throws SQLException Hvis noe gikk galt med databasekommunikasjonen.
   */
  public static void fillPictureListModel(Statement stmnt, DefaultListModel modelPictures,
                                          String searchString) throws SQLException
  {
	  	//Remove previous elements
	  	modelPictures.removeAllElements();

	  	//Take all the pictures with this substring in the title
		String query = "SELECT * FROM Picture WHERE TITLE LIKE '%" + searchString + "%'";
		
		//Execute the select and take the values in a set
		ResultSet rs = stmnt.executeQuery(query);
		
		//Add them
		while (rs.next())
		{
			Picture pic = new Picture(rs.getString("TITLE"));
			modelPictures.addElement(pic);

		}
  }

  /**
   * Metode som kalles en gang av hovedprogrammet for � f� den GUI-komponenten
   * som skal brukes til � vise detaljer om bilder.
   * Metoden er statisk da denne komponenten deles av alle instanser av Picture.
   *
   * @return GUI-komponent.
   */
  public static JComponent getDetailComponent()
  {
    for (int i = 0; i < textFields.length; i++) {  // Initialiser tekstfelt
      textFields[i] = new JTextField();
      textFields[i].setEditable(false);
    }
    JPanel panel = new JPanel(new GridLayout(4, 4, 6, 6)); // Legg tekstfelt og labler i et 4x4 rutenett
    panel.add(new JLabel("Description of the picture:"));
    panel.add(textFields[0]);
    panel.add(new JLabel("Date in which was taken:"));
    panel.add(textFields[1]);
    panel.add(new JLabel("Photographer:"));
    panel.add(textFields[2]);
    panel.add(new JLabel("E-mail:"));
    panel.add(textFields[3]);
    panel.add(new JLabel("Photographer's description:"));
    panel.add(textFields[4]);
    panel.add(new JLabel("Homepage:"));
    panel.add(textFields[5]);
    panel.add(new JLabel("Cammera brand:"));
    panel.add(textFields[6]);
    panel.add(new JLabel("Cammera model:"));
    panel.add(textFields[7]);
    return panel;
  }

  
  private Picture(String title)
  {
    this.title = title;
  }

  /**
   * Kalles av bildelista n�r den skal finne ut hva slags tekststreng
   * hvert av elementene i lista skal vises ved.
   *
   * @return Tekststring som identifiserer bildet.
   */
  public String toString()
  {
    return title;
  }

  /**
   * Kalles av hovedprogrammet n�r detaljer om dette bildet skal vises.
   * Skal fylle ut tekstfeltene i textFields[] med data fra databasen.
   *
   * @param stmnt         JDBC-statement for � kontakte databasen med.
   * @throws SQLException Hvis noe gikk galt med databasekommunikasjonen.
   */
  public void showDetails(Statement stmnt) throws SQLException
  {    
  
  	//Taking picture info
	String query = "SELECT description, pdate,camera  FROM Picture WHERE TITLE = '" + this.title + "'";
	ResultSet rs = stmnt.executeQuery(query);
	if (rs.next()) 
	{
		String description = rs.getString("DESCRIPTION");
		String pDate = rs.getString("pDate");	
		int cameraId = rs.getInt("CAMERA");
		
	  	//Taking camera info
		query = "SELECT brand, model,firstname,lastname  FROM Camera WHERE ID = " + cameraId;
		rs = stmnt.executeQuery(query);
		if (rs.next()) 
		{
			String brand = rs.getString("brand");
			String model = rs.getString("model");	
			String firstname = rs.getString("firstname");
			String lastname = rs.getString("lastname");
			
		  	//Taking photographer info
			query = "SELECT mail, description,homepage FROM Photographer WHERE firstname = '" 
				+ firstname + "' and lastname= '" + lastname + "'";
			rs = stmnt.executeQuery(query);
			if (rs.next()) 
			{
				String mail = rs.getString("mail");
				String description_ph = rs.getString("description");	
				String homepage = rs.getString("homepage");
				
				//If everything was gathered properly, show it
				textFields[0].setText(description);
				textFields[1].setText(pDate);
				textFields[2].setText(firstname + " " + lastname);
				textFields[3].setText(mail);
				textFields[4].setText(description_ph);
				textFields[5].setText(homepage);
				textFields[6].setText(brand);
				textFields[7].setText(model);

			}
			
		}
		
	}
		
	
	  
  }

  /**
   * Brukes til � fylle listemodellen til utgavelista med innhold.
   * Innholdet skal v�re instanser av klassen Version.
   *
   * @param stmnt         JDBC-statement for � kontakte databasen med.
   * @param modelVersions Listmodellen som skal fylles med Version-objekter.
   * @throws SQLException Hvis noe gikk galt med databasekommunikasjonen.
   */
  public void fillVersionListModel(Statement stmnt, DefaultListModel modelVersions) throws SQLException
  {
	  	//Remove previous elements
	  	modelVersions.removeAllElements();
	  	
	  	//Take all the versions of this picture
		String query = "SELECT * FROM Version WHERE IDPICTURE= '" + this.title + "'" ;
		
		//Execute the select and take the values in a set
		ResultSet rs = stmnt.executeQuery(query);
		
		//Add them
		while (rs.next())
		{
			Version version = new Version(rs.getString("STORINGDATE"),rs.getString("IDPICTURE"),rs.getString("DESCRIPTION"));
			modelVersions.addElement(version);

		}
  }
}
