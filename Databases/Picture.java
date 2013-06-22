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
  private int pictureID;                                      // ID-en til dette bildet
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
	  //recover conexion
	  //select * from Pictures
	  //return dataSet Pictures
	  //For each picture in data set, add to the modelPicturesList
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
    panel.add(new JLabel("Beskrivelse:"));
    panel.add(textFields[0]);
    panel.add(new JLabel("Eksponeringsdato:"));
    panel.add(textFields[1]);
    panel.add(new JLabel("Fotograf:"));
    panel.add(textFields[2]);
    panel.add(new JLabel("E-Post:"));
    panel.add(textFields[3]);
    panel.add(new JLabel("Beskrivelse:"));
    panel.add(textFields[4]);
    panel.add(new JLabel("Hjemmeside:"));
    panel.add(textFields[5]);
    panel.add(new JLabel("Kameramerke:"));
    panel.add(textFields[6]);
    panel.add(new JLabel("Kameramodell:"));
    panel.add(textFields[7]);
    return panel;
  }

  /**
   * Kalles av fillPictureListModel n�r lista over bilder skal fylles.
   * Konstruktoren er derfor privat - ingen andre skal ha bruk for den.
   *
   * @param pictureID  ID-en til bildet. Trengs n�r mer data skal leses inn om bildet.
   * @param title      Titlen til bildet. Trengs fordi det er titlen som vises i bildet.
   */
  private Picture(int pictureID, String title)
  {
    this.pictureID = pictureID;
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
  }
}
