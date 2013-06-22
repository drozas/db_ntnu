import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// M� gj�res:
// * Legge inn rett brukernavn/passord i main()

/** @author Jon Olav Hauglid */
public class DB3 implements ListSelectionListener, ActionListener
{
  private JFrame window = new JFrame("Pictures database by Rozas & Bono");  // Programvinduet
  private JList listPictures;                                           // GUI-liste over bilder
  private JList listVersions;                                           // GUI-liste over utgaver
  private DefaultListModel modelPictures = new DefaultListModel();      // Modellen til bilde-lista
  private DefaultListModel modelVersions = new DefaultListModel();      // Modellen til utgave-lista
  private JLabel imageLabel = new JLabel("", JLabel.CENTER);            // Komponent for � vise bilder i
  private JTextField tfSearch = new JTextField();                       // Tekstfelt for s�keuttrykk
  private Statement stmnt;                                              // JDBC-statement for SQL-utf�ring
  
  	//drozas: added for derby
	private static String driver = "org.apache.derby.jdbc.ClientDriver";
	private static String db_name = "picturesDB";
	private static String connection_url = "jdbc:derby://localhost:1527/" + db_name + ";create=true";

  /**
   * Start-metoden til programmet. Lager databaseoppkobling og kaller konstruktor.
   *
   * @param args        Kommandolinjeargumenter. Brukes ikke
   */
  public static void main(String args[])
  {
    try {

    	
    	//drozas: changes to derby driver manager
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(connection_url);
		DB3 db = new DB3(conn);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Konstuktoren til hovedprogrammet. Lagrer brukergrensesnitt og henter initielle data fra databasen.
   *
   * @param con         Databaseoppkobling
   * @throws Exception  Dersom kontakt med databasen ikke oppn�s
   */
  public DB3(final Connection con) throws Exception
  {
    stmnt = con.createStatement();

    // Initier brukergrensesnittkomponenter
    listPictures = new JList(modelPictures);
    listPictures.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listPictures.addListSelectionListener(this);
    listVersions = new JList(modelVersions);
    listVersions.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listVersions.addListSelectionListener(this);
    tfSearch.addActionListener(this);
    JLabel label1 = new JLabel("Pictures:");
    JLabel label2 = new JLabel("Versions:");
    JLabel label3 = new JLabel("Search:");


    // Lag venstreside av brukergrensesnitt - lister over bilder og utgaver
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel leftPanel = new JPanel(gbl);
    gbc.fill = gbc.BOTH;
    gbc.gridwidth = gbc.REMAINDER;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.insets = new Insets(3, 3, 0, 3);
    gbl.setConstraints(label3, gbc);
    leftPanel.add(label3);
    gbl.setConstraints(tfSearch, gbc);
    leftPanel.add(tfSearch);
    gbl.setConstraints(label1, gbc);
    leftPanel.add(label1);
    gbc.weighty = 1.0;
    gbl.setConstraints(listPictures, gbc);
    leftPanel.add(listPictures);
    gbc.weighty = 0.0;
    gbl.setConstraints(label2, gbc);
    leftPanel.add(label2);
    gbc.weighty = 1.0;
    gbl.setConstraints(listVersions, gbc);
    leftPanel.add(listVersions);

    // Lag h�yreside av brukergrensesnitt - detaljer om bilde og visning av utgave
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(new JScrollPane(Picture.getDetailComponent()), BorderLayout.NORTH);
    rightPanel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);

    // Sett sammen h�yre- og venstreside
    Container pane = window.getContentPane();
    pane.setLayout(new BorderLayout());
    pane.add(leftPanel, BorderLayout.WEST);
    pane.add(rightPanel, BorderLayout.CENTER);

    // Lukk databaseoppkobling n�r vinduet blir lukket
    window.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent event)
      {
        try {
          stmnt.close();
          con.close();
        } catch (SQLException e) {
        }
        System.exit(0);
      }
    });

    //
    // Fyll modellen til bilde-lista med Picture-objekter fra databasen
    Picture.fillPictureListModel(stmnt, modelPictures);
    // Velg det f�rste bildet i lista
    listPictures.setSelectedIndex(0);

    // Sett vindusst�rrelse og vis vinduet
    window.setSize(1024, 768);
    window.setVisible(true);
  }

  /**
   * N�r noen velger noe i en av listen i programmet blir denne metoden kalt.
   *
   * @param event Informasjon om hendelsen som oppstod
   */
  public void valueChanged(ListSelectionEvent event)
  {
    if (event.getValueIsAdjusting()) // Ikke gj�r noe s� lenge som det kommer flere eventer
      return;
    if (event.getSource() == listPictures && !listPictures.isSelectionEmpty())
      try {
    	 
        Picture picture = (Picture)listPictures.getSelectedValue(); // F� tak i bildet som er valgt
        picture.showDetails(stmnt);                                 // Hent og vis detaljer om bildet
        picture.fillVersionListModel(stmnt, modelVersions);         // Les inn utgavene og vis disse i lista
        listVersions.setSelectedIndex(0);                           // Velg den f�rste utgaven
      } catch (Exception e) {
        e.printStackTrace();
      }
    else if (event.getSource() == listVersions && !listVersions.isSelectionEmpty()) {
      try {
    	  
        Version selectedItem = (Version)listVersions.getSelectedValue();  // F� tak i den valgte utgaven
        imageLabel.setIcon(new ImageIcon(selectedItem.getImage(stmnt)));  // Hent bildet og vis det
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * N�r man trykker enter i s�kefeltet blir denne metoden kalt.
   *
   * @param event Informasjon om hendelsen som oppstod.
   */
  public void actionPerformed(ActionEvent event)
  {
    if (event.getSource() == tfSearch) {
      String searchString = tfSearch.getText();
      try {
        if (searchString.equals(""))          // Hvis s�kestrengen er tom, viser vi alle bilder
          Picture.fillPictureListModel(stmnt, modelPictures);
        else                                  // Hvis kun bilder som tilfredsstiller s�kestrengen
          Picture.fillPictureListModel(stmnt, modelPictures, searchString);
        if (modelPictures.size() > 0)         // Fikk vi noen bilder som resultat?
          listPictures.setSelectedIndex(0);   // Hvis informasjon om det f�rste bildet i resultatet
        else
          modelVersions.clear();              // Ingen bilder => Tom liste over utgaver
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
