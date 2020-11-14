package bll;
import java.util.List;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.io.*;
import java.nio.charset.StandardCharsets;

import GUI.Gui;
import bo.Item;
import bo.Tag;
import bo.Technique;
import bo.Enchainement;
import dal.ConnectionProvider;
import dal.DAO;
import dal.exceptions.DALException;
import dal.jdbc.ItemDAOJdbcImpl;
import dal.jdbc.TagDAOJdbcImpl;
import dal.Factory;


public class Controler {
	private static DAO<Item> ItemDAO;
	private static DAO<Tag> TagDAO;
	static Logger logger = Logger.getLogger("log");  
	static FileHandler fh;


	public static void main(String[] args) throws SQLException, DALException, IOException {

		// Loading SQL drivers
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		}

		// Setting up logger 
		fh = new FileHandler("D:\\Documents\\TKD\\Cahier technique\\Database\\InitTables\\log.txt");  
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
		fh.setFormatter(formatter);  

		// Creating objects to access the DAL
		ItemDAO = Factory.getItemDAO();
		TagDAO =  Factory.getTagDAO();

		
		///////////////////////////////////////////////////////////////////////////////////////////////
		///////////////////////// Processing the files ///////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		////// Resetting the tables
		createTables();

		//// Reading Tags and filling the Tags table
		String fileTags = "D:\\Documents\\TKD\\Cahier technique\\Database\\InitTables\\Tags.txt";
		List<Tag> listTag = readFileTag(fileTags);

		//// Reading item files and filling the DB
		String stemItems = "D:\\Documents\\TKD\\Cahier technique\\Database\\Exercices\\";

		readAllItems(stemItems);
		readAllFilesItems(stemItems);
		processDB();

		 

		// Test of GUI

		Gui gui = new Gui();

		// End of main()
	}

	public static void readAllItems(String pathToFile) throws IOException, DALException {

		File f = new File(pathToFile);
		String[] pathnames;
		pathnames = f.list();

		List<String> lines = new ArrayList<String>();
		boolean newItem = true;

		for (String pathname : pathnames) {
			newItem = true;
			File f2 = new File(pathToFile + pathname);
			BufferedReader br = new BufferedReader(new FileReader(f2, StandardCharsets.UTF_8)); 

			String st; 
			String firstWord = "";
			String[] splitLine = null;
			String Nom = null;
			String Type;
			int iEnch = 0;
			String line = null;
			boolean modif = false;
			String curType = null;

			while ((st = br.readLine()) != null) {
				if (st.length() > 0) {
					if(st.charAt(0) == '#') {
						newItem = true;
					}
				}
				line = st ;
				//System.out.println("line : " + line);
				lines.add(line + "\n");
				splitLine = st.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
				firstWord = splitLine[0];

				if( firstWord.equals("Type")) {
					Type = st.split(":")[1];
					Type = Type.trim();
					curType = Type;

					///////////////////// Edition du fichier pour ajouter le bon Nom d'enchainement

					if (Type.equals("Enchainement")) {
						line = br.readLine();
						//System.out.println("line : " + line);
						splitLine = line.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
						firstWord = splitLine[0];
						if (firstWord.equals("Nom")) {
							Nom = line.split(":")[1];
							Nom = Nom.trim();
							//System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Enchainement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Enchainement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Enchainement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							//System.out.println(Nom);
							modif = true;
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Enchainement");
							newItem = false;
						}
						//System.out.println("Nom enchainement : " + Nom);
						//ItemDAO.insertMiniItem(Nom);
						lines.add(line + "\n");
					}		

					///////////////////

					///////////////////// Edition du fichier pour ajouter le bon Nom d'Etirement

					if (Type.equals("Etirement")) {
						line = br.readLine();
						//System.out.println("line : " + line);
						splitLine = line.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
						firstWord = splitLine[0];
						if (firstWord.equals("Nom")) {
							Nom = line.split(":")[1];
							Nom = Nom.trim();
							//System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Etirement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Etirement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Etirement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							//System.out.println(Nom);
							modif = true;
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Etirement");
							newItem = false;
						}
						//System.out.println("Nom enchainement : " + Nom);
						//ItemDAO.insertMiniItem(Nom);
						lines.add(line + "\n");
					}		

					/////////////////// Edition du fichier pour ajouter le bon Nom d'Educatif

					if (Type.equals("Educatif")) {
						line = br.readLine();
						//System.out.println("line : " + line);
						splitLine = line.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
						firstWord = splitLine[0];
						if (firstWord.equals("Nom")) {
							Nom = line.split(":")[1];
							Nom = Nom.trim();
							//System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Educatif");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Educatif");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Educatif-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							//System.out.println(Nom);
							modif = true;
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Educatif");
							newItem = false;
						}
						//System.out.println("Nom enchainement : " + Nom);
						//ItemDAO.insertMiniItem(Nom);
						lines.add(line + "\n");
					}		

					/////////////////// Edition du fichier pour ajouter le bon Nom d'Echauffement

					if (Type.equals("Echauffement")) {
						line = br.readLine();
						//System.out.println("line : " + line);
						splitLine = line.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
						firstWord = splitLine[0];
						if (firstWord.equals("Nom")) {
							Nom = line.split(":")[1];
							Nom = Nom.trim();
							//System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Echauffement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Echauffement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Echauffement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							//System.out.println(Nom);
							modif = true;
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Echauffement");
							newItem = false;
						}
						//System.out.println("Nom enchainement : " + Nom);
						//ItemDAO.insertMiniItem(Nom);
						lines.add(line + "\n");
					}		

				}

				if (firstWord.equals("Nom") && newItem ) {
					Nom = st.split(":")[1];
					Nom = Nom.trim();
					//System.out.println(Nom);
					((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, curType);
				}
			}

			br.close();

			//System.out.println("Testing reading file : ");
			//System.out.println(lines);

			if (modif) {
				FileWriter fw = new FileWriter(f2, StandardCharsets.UTF_8);
				BufferedWriter out = new BufferedWriter(fw);
				for(String s : lines)
					out.write(s);
				out.flush();
				out.close();
			}
			lines.clear();
		}

	}

	public static List<Tag> readFileTag(String pathToFile) throws IOException, DALException {
		List<Tag> listTag = new ArrayList<>()  ;
		Tag tag;

		File file = new File(pathToFile); 
		String st;

		BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

		while ((st = br.readLine()) != null) {
			tag = new Tag(st);
			listTag.add(tag);
			TagDAO.insert(tag);
		}

		br.close();
		return listTag;
	}

	public static List<Item> readFileItem(String folder, String filename) throws IOException, DALException {
		String pathToFile = folder + filename;
		List<Item> listIT = new ArrayList<>()  ;
		Item it;
		File file = new File(pathToFile); 

		BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8)); 

		String st; 
		String firstWord = "";
		String[] splitLine = null;

		String Type = "";
		String Nom = "";
		String Descriptif = "";
		String Pratiquants = "Non-spécifique";
		String Nombre = "Seul";
		String Dispositif = "Rien";
		int Niveau = 0;
		List<String> Tags = new ArrayList<>();
		List<String> Etapes = new ArrayList<>();
		List<String> Details = new ArrayList<>();

		while ((st = br.readLine()) != null) {
			splitLine = st.split("[ \\t\\n\\,\\?\\;\\.\\:\\!]") ;
			firstWord = splitLine[0];
			// System.out.println(splitLine.length);

			if (firstWord.equals("Type")) {
				Type = st.split(":")[1];
				Type = Type.trim();
			}

			if (firstWord.equals("Nom")) {
				Nom = st.split(":")[1];
				Nom = Nom.trim();
				// System.out.println(Nom);
			}

			if (firstWord.equals("Descriptif")) {
				Descriptif = st.split(":")[1];
				Descriptif = Descriptif.trim();
			}

			if (firstWord.equals("Nombre")) {
				Nombre = st.split(":")[1];
				Nombre = Nombre.trim();
			}

			if (firstWord.equals("Pratiquants")) {
				Pratiquants = st.split(":")[1];
				Pratiquants = Pratiquants.trim();
			}

			if (firstWord.equals("Dispositif")) {
				Dispositif = st.split(":")[1];
				Dispositif = Dispositif.trim();
			}

			if (firstWord.equals("Niveau")) {
				String SNiveau = st.split(":")[1];
				SNiveau = SNiveau.trim();
				Niveau = Integer.parseInt(SNiveau);
			}

			// System.out.println(Descriptif);

			if (firstWord.equals("Tags")) {
				String AllTags = st.split(":")[1];
				AllTags = AllTags.trim();
				String[] splAllTags = AllTags.split(";");
				for (String temp : splAllTags) {
					temp = temp.trim();
					Tags.add(temp);
					if (!existingTag(temp)) {
						System.out.println("New tag to be added : " + temp);
						logger.info("New tag to be added : " + temp); 
					}
				}
			}

			if (firstWord.equals("Etapes")) {
				String AllEtapes = st.split(":")[1];
				String[] splAllEtapes = AllEtapes.split(";");
				for (String temp : splAllEtapes) {
					Etapes.add(temp);
					//System.out.println(temp);
				}
			}

			if (firstWord.equals("Details")) {
				String AllDetails = st.split(":")[1];
				String[] splAllDetails = AllDetails.split(";");
				for (String temp : splAllDetails) {
					Details.add(temp);
					//System.out.println(temp);
				}
			}

			if (!firstWord.isEmpty()) {
				if (firstWord.substring(0, 1).equals("#")) {
					if (Type.equals("Technique") || Type.equals("Etirement") || Type.equals("Echauffement") || Type.equals("Educatif") )  {
						List<String> Tags_temp =  new ArrayList<String>(Tags);
						Tags_temp.add(Type);
						Tags_temp.add("Nombre : " + Nombre);
						Tags_temp.add("Pratiquants : " + Pratiquants);
						it = new Technique(Type, Nom, Descriptif, Tags_temp, Niveau, filename, Nombre, Pratiquants, Dispositif);
						listIT.add(it);
						ItemDAO.insert(it);
						//System.out.println(it);
					}
					else if (Type.equals("Enchainement")) {
						List<String> Etapes_temp =  new ArrayList<String>(Etapes); 
						List<String> Details_temp =  new ArrayList<String>(Details);
						List<String> Tags_temp =  new ArrayList<String>(Tags);
						Tags_temp.add(Type);
						Tags_temp.add("Nombre : " + Nombre);
						Tags_temp.add("Pratiquants : " + Pratiquants);
						it = new Enchainement(Nom, Descriptif, Tags_temp, Etapes_temp, Niveau, filename, Details_temp, Nombre, Pratiquants, Dispositif);
						listIT.add(it);
						ItemDAO.insert(it);
						((ItemDAOJdbcImpl) ItemDAO).insertEnchainement((Enchainement) it);
						//System.out.println(it);
					}
					// Reset list variables
					Tags.clear();
					Etapes.clear();
					Details.clear();
				}
			}

		}	

		br.close();
		return listIT;
	}

	public static void readAllFilesItems( String folder) throws IOException, DALException {
		File f = new File(folder);
		String[] pathnames;
		pathnames = f.list();

		List<Item> listIT = new ArrayList<>()  ;

		for (String pathname : pathnames) {
			listIT = readFileItem(folder, pathname);
			System.out.println("############################## \n ###################### \n Starting to scan the list of Items in file : " + pathname + "\n ###########");
			for (Item it : listIT) {
				if (it instanceof Technique) {
					System.out.println("Technique read ");
				}
				if (it instanceof Enchainement) {
					System.out.println("Enchainement read");
				}
			}
		}
	}

	public static boolean existingTag (String tag) throws DALException {
		boolean exists = false;
		Tag tag_temp = new Tag(tag);
		exists = !(((ItemDAOJdbcImpl) ItemDAO).checkInsertNom(tag) && TagDAO.checkInsert(tag_temp, "Tags")) ;

		return exists;
	}


	public static void createTables() throws DALException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS Items;\r\n"
					+ "\r\n"
					+ "CREATE TABLE Items (\r\n"
					+ "    ID int UNIQUE,\r\n"
					+ "    Type varchar(255) NOT NULL,\r\n"
					+ "    Nom  varchar(255) NOT NULL UNIQUE,\r\n"
					+ "    Descriptif varchar(8000),\r\n"
					+ "    Niveau int,\r\n"
					+ "    Nombre varchar(255) DEFAULT 'Seul',\r\n"
					+ "    Dispositif varchar(255) DEFAULT 'Rien',\r\n"
					+ "    Pratiquants varchar(255) DEFAULT 'Non-spécifique',\r\n"
					+ "    Filename varchar(255) NOT NULL\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS Tags;\r\n"
					+ "\r\n"
					+ "CREATE TABLE Tags (\r\n"
					+ "    ID int IDENTITY(1,1) PRIMARY KEY,\r\n"
					+ "    Nom varchar(255) NOT NULL UNIQUE\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS ItemsTags;\r\n"
					+ "\r\n"
					+ "CREATE TABLE ItemsTags (\r\n"
					+ "    IDItems int NOT NULL, \r\n"
					+ "    IDTags int NOT NULL\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS ItemsItems;\r\n"
					+ "\r\n"
					+ "CREATE TABLE ItemsItems (\r\n"
					+ "    IDItems1 int NOT NULL, \r\n"
					+ "    IDItems2 int NOT NULL\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS ItemsID;\r\n"
					+ "\r\n"
					+ "CREATE TABLE ItemsID (\r\n"
					+ "    ID int IDENTITY(1,1) PRIMARY KEY NOT NULL, \r\n"
					+ "    Nom varchar(255) NOT NULL UNIQUE,\r\n"
					+ "    Type varchar(255) NOT NULL\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS Enchainements;\r\n"
					+ "\r\n"
					+ "CREATE TABLE Enchainements (\r\n"
					+ "    ID int PRIMARY KEY NOT NULL, \r\n"
					+ "    Etapes varchar(8000) NOT NULL,\r\n"
					+ "    Details varchar(8000)\r\n"
					+ ");\r\n"
					+ "\r\n"
					+ "DROP TABLE IF EXISTS ItemsTagsList;\r\n"
					+ "\r\n"
					+ "CREATE TABLE ItemsTagsList(\r\n"
					+ "    ID int NOT NULL, \r\n"
					+ "   Nom varchar(255) NOT NULL,\r\n"
					+ "   Type  varchar(255) NOT NULL\r\n"
					+ ");\r\n");

		} catch (SQLException throwables) {
			throwables.printStackTrace();
			throw new DALException("Erreur insert");
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur close");
			}
		}
	}

	public static int genericRetrieveId(String Nom) throws DALException {
		String typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(Nom);
		int idKeyWord = 0;
		if (typeKeyWord.equals("Item")) {
			idKeyWord = ItemDAO.retrieveId(Nom);
		}
		else if (typeKeyWord.equals("Tag")) {
			idKeyWord = TagDAO.retrieveId(Nom);
		}
		return idKeyWord;
	}

	public static String fetchEnchForItem(int idK, String fiche, List<Integer> IDenchSpec) throws DALException {
		String fiche2 = fiche;
		Connection con = null;
		Statement stmt = null;

		String constraints = "";
		constraints = "IDTags = " + IDenchSpec.get(0);

		for (int i = 1; i < IDenchSpec.size() ; i++) {
			constraints = constraints + " or IDTags = " + IDenchSpec.get(i);
		}

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();

			PreparedStatement query = con.prepareStatement("select * from (\r\n"
					+ "select * from Items \r\n"
					+ "	left join (\r\n"
					+ "		select ID as ID1 from Items \r\n"
					+ "		inner join ( select IDItems from ItemsTags where "+ constraints + ") as x\r\n"
					+ "		on x.IDItems = Items.ID) as w \r\n"
					+ "	on  Items.ID = w.ID1 where w.ID1 IS NULL and Items.Type = 'Enchainement') as x2\r\n"
					+ "	inner join (select ID as ID2 from Items \r\n"
					+ "inner join ( select IDItems1 from ItemsItems where IDItems2 = ?) as w2 \r\n"
					+ "on w2.IDItems1 = Items.ID ) as w3 on  w3.ID2 = x2.ID order by Niveau, Nom ASC; ");


			query.setInt(1, idK);
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						int IDench = listItemsReq.getInt("ID");
						String Nom = listItemsReq.getString("Nom");
						String Descriptif = listItemsReq.getString("Descriptif");
						int Niveau = listItemsReq.getInt("Niveau");
						String fileName = listItemsReq.getString("Filename");
						String Nombre = listItemsReq.getString("Nombre");
						String Pratiquant = listItemsReq.getString("Pratiquants");
						String Dispositif = listItemsReq.getString("Dispositif");
						List<String> etapes = ((ItemDAOJdbcImpl) ItemDAO).getEtapesFromDB(IDench);
						List<String> details = ((ItemDAOJdbcImpl) ItemDAO).getDetailsFromDB(IDench);
						//String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif
						Enchainement it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details, Nombre, Pratiquant, Dispositif);

						//List<Tags> tags = new ArrayList();

						String display = it.toString();
						fiche2 = fiche2 + display + "\n##########\n";	
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}




		return fiche2;

	}

	public static String fetchEnchForItemWithTags(int idK, String fiche, List<String> allTagsToInclude) throws DALException {
		String fiche2 = fiche;
		Connection con = null;
		Statement stmt = null;

		String constraints = "Nom = '" + allTagsToInclude.get(0) + "'";
		int lTags = allTagsToInclude.size();
		for (int i = 1; i <allTagsToInclude.size() ; i++) {
			constraints = constraints + " or Nom = '" + allTagsToInclude.get(i) + "'";
		}

		System.out.println(constraints);

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from (\r\n"
					+ "	select * from ( \r\n"
					+ "		select * from ItemsItems where IDItems2 = ?)as x1 \r\n"
					+ "	inner join Items on Items.ID = x1.IDItems1 \r\n"
					+ "	) as w\r\n"
					+ "inner join (\r\n"
					+ "	select * from ItemsTags \r\n"
					+ "	inner join (select ID from Tags where " + constraints  + ") as x2 \r\n"
					+ "	on x2.ID = ItemsTags.IDTags \r\n"
					+ "	) as z \r\n"
					+ "on w.ID = z.IDItems order by Niveau, Nom ASC;");

			query.setInt(1, idK);


			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						int IDench = listItemsReq.getInt("ID");
						String Nom = listItemsReq.getString("Nom");
						String Descriptif = listItemsReq.getString("Descriptif");
						int Niveau = listItemsReq.getInt("Niveau");
						String fileName = listItemsReq.getString("Filename");
						String Nombre = listItemsReq.getString("Nombre");
						String Pratiquant = listItemsReq.getString("Pratiquants");
						String Dispositif = listItemsReq.getString("Dispositif");
						List<String> etapes = ((ItemDAOJdbcImpl) ItemDAO).getEtapesFromDB(IDench);
						List<String> details = ((ItemDAOJdbcImpl) ItemDAO).getDetailsFromDB(IDench);
						//String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif
						Enchainement it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details, Nombre, Pratiquant, Dispositif);
						String display = it.toString();
						fiche2 = fiche2 + display + "\n##########\n";	
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}




		return fiche2;

	}


	public static String addType(int idK, String fiche, String type) throws DALException {
		String fiche2 = fiche;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from Items\r\n"
					+ "inner join (select * from ItemsItems where IDItems2 = ?) as y \r\n"
					+ "on y.IDItems1 = Items.ID and Items.Type = ? order by Niveau, Nom ASC;");
			query.setInt(1, idK);
			query.setString(2, type);
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						if (!type.equals("Enchainement")) {
							// type, Nom, Descriptif,Tags, Niveau, Filename, Nombre, Public, Dispositif
							String Nom = listItemsReq.getString("Nom");
							String Descriptif = listItemsReq.getString("Descriptif");
							int Niveau = listItemsReq.getInt("Niveau");
							String fileName = listItemsReq.getString("Filename");
							String Nombre = listItemsReq.getString("Nombre");
							String Pratiquant = listItemsReq.getString("Pratiquants");
							String Dispositif = listItemsReq.getString("Dispositif");
							Technique it = new Technique( type, Nom ,Descriptif, null , Niveau, fileName, Nombre, Pratiquant, Dispositif  );
							String display = it.toStringMini();
							fiche2 = fiche2 + display + "\n##########\n";						
						}
						if (type.equals("Enchainement")) {
							// Requetes pour chercher les étapes !	
							String Nom = listItemsReq.getString("Nom");
							String Descriptif = listItemsReq.getString("Descriptif");
							int Niveau = listItemsReq.getInt("Niveau");
							String fileName = listItemsReq.getString("Filename");
							String Nombre = listItemsReq.getString("Nombre");
							String Pratiquant = listItemsReq.getString("Pratiquants");
							String Dispositif = listItemsReq.getString("Dispositif");
							int IDEnch = listItemsReq.getInt("ID");
							List<String> etapes = ((ItemDAOJdbcImpl) ItemDAO).getEtapesFromDB(IDEnch);
							List<String> details = ((ItemDAOJdbcImpl) ItemDAO).getDetailsFromDB(IDEnch);

							//String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif
							Enchainement it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details, Nombre, Pratiquant, Dispositif);
							String display = it.toString();
							fiche2 = fiche2 + display + "\n##########\n";	
						}
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}



		return fiche2;
	}

	public static List<String> getStringReq(String nomMainTag, List<String> allTags, List<Item> allItems) {
		List<String> allres = new ArrayList();
		String strReq = "";
		String req = ""; // Inutile finalement dans le requestManager;
		int numTags = allTags.size() + 1;
		String constraints = "Nom = '" + nomMainTag + "' " ;
		// Attention si allTags est vide, faire une autre requête (maybe ?)
		if (numTags > 1) {
			for (int i = 0; i < numTags-1; i++) {
				if (!allTags.get(i).equals(nomMainTag)) {
					constraints = constraints + "or Nom = '" + allTags.get(i) + "' " ;
				}
				else {
					numTags = numTags-1;
				}
			}
		}
		String strReqStem = "select * from Items \r\n"
				+ "inner join ( select IDItems from (\r\n"
				+ "select IDItems, count(*) as countItem from \r\n"
				+ "	(select * from ItemsTags inner join ( select ID from Tags where (" + constraints + ") ) as y on y.ID = ItemsTags.IDTags) as x\r\n"
				+ "	group by x.IDItems) as y\r\n"
				+ "	where countItem = " + numTags + "  ) as z\r\n"
				+ "	on Items.ID = z.IDItems ";
		if (!allItems.isEmpty()) {

			String constraints2 = "IDItems2 = " + allItems.get(0).getId();
			int numItems = allItems.size();
			for (int i = 1; i < numItems; i++) {
				constraints2 = constraints2 + " or IDItems2 = " + allItems.get(i).getId()  ;
			}

			String strReq2 = "select * from Items inner join (\r\n"
					+ "select * from (\r\n"
					+ "select ID, count(*) as countItems2 from (" + strReqStem + ") as w\r\n"
					+ "	inner join (select IDItems1 from ItemsItems where "+ constraints2 +") as w2\r\n"
					+ "	on w2.IDItems1 = w.ID group by ID) as w3 where countItems2 = "+ numItems + ") as w3 on w3.ID = Items.ID order by Niveau, Nom ASC;";
			strReq = strReq2;
		}
		else {
			strReq = strReqStem + " order by Niveau, Nom ASC;";
		}

		allres.add(req);
		allres.add(strReq);
		return allres;
	}

	public static List<Item> removeDuplicates(List<Item> allItems ) {
		List<Item> noDup = new ArrayList();
		Set<String> set = new HashSet<>();
		String Nom_cur = "";

		for (Item it : allItems) {
			Nom_cur = it.getNom();
			if (set.add(Nom_cur)) {
				noDup.add(it);
			}
		}

		return noDup;
	}


	public static List<Item> processTagRequest(Tag mainTag,List<Item> allItems, List<String> AllTags ) throws DALException {
		// getting all items fitting the tag + constraints

		Set<String> set = new HashSet<>(AllTags);
		AllTags.clear();
		AllTags.addAll(set);

		allItems = removeDuplicates(allItems);

		List<Item> resList = new ArrayList();
		int idTag = mainTag.getId();
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			List<String> allres = getStringReq(mainTag.getNom(), AllTags, allItems);
			String req = allres.get(1);
			String reqDisplay = allres.get(0);
			//System.out.println(req);
			PreparedStatement query = con.prepareStatement(req); 
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {	
						String Type = listItemsReq.getString("Type");
						if (Type.equals("Enchainement")) {
							int IDench = listItemsReq.getInt("ID");
							String Nom = listItemsReq.getString("Nom");
							//System.out.println("Nom de l'enchainement trouvé : " + Nom);
							String Descriptif = listItemsReq.getString("Descriptif");
							int Niveau = listItemsReq.getInt("Niveau");
							String fileName = listItemsReq.getString("Filename");
							String Nombre = listItemsReq.getString("Nombre");
							String Pratiquant = listItemsReq.getString("Pratiquants");
							String Dispositif = listItemsReq.getString("Dispositif");
							List<String> etapes = ((ItemDAOJdbcImpl) ItemDAO).getEtapesFromDB(IDench);
							List<String> details2 = ((ItemDAOJdbcImpl) ItemDAO).getDetailsFromDB(IDench);
							Enchainement it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details2, Nombre, Pratiquant, Dispositif);
							resList.add(it);
						}
						else {
							String Nom = listItemsReq.getString("Nom");
							String Descriptif = listItemsReq.getString("Descriptif");
							int Niveau = listItemsReq.getInt("Niveau");
							String fileName = listItemsReq.getString("Filename");
							String Nombre = listItemsReq.getString("Nombre");
							String Pratiquant = listItemsReq.getString("Pratiquants");
							String Dispositif = listItemsReq.getString("Dispositif");

							Technique it = new Technique( Type, Nom ,Descriptif, null , Niveau, fileName, Nombre, Pratiquant, Dispositif  );
							resList.add(it);
						}
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}
		return resList;
	}

	public static List<String> processItemRequest(Item mainItem, List<Item> allItems, List<String> AllTags, String fiche ) throws DALException {
		List<String> allres = new ArrayList();

		List<String> nomEnchSpec = new ArrayList();
		nomEnchSpec.add("Poomsae");
		nomEnchSpec.add("Gyeorugi");
		nomEnchSpec.add("Hohinsul");

		String fiche2 = fiche;
		String type2KeyWord = mainItem.getType();
		//System.out.println(type2KeyWord);
		int idKeyWord = mainItem.getId();
		//System.out.println("Main ID : " + idKeyWord);
		String keyword = mainItem.getNom();
		List<String> b = new ArrayList();
		b.add("Enchainement");
		b.add("Echauffement");
		b.add("Technique");
		b.add("Educatif");
		b.add("Etirement");
		b.add("Poomsae");
		b.add("Gyeorugi");
		b.add("Hoshinsul");

		List<String> c = new ArrayList<>(AllTags);
		c.removeAll(b); // Getting tags outside the authorized ones for items

		List<String> inters = new ArrayList<>(AllTags);
		inters.retainAll(b);

		String req = "Affiche pour la technique " + mainItem.getNom();
		if (!inters.isEmpty()) {
			req = req + " les éléments complémentaires suivants : " + inters;
		}
		System.out.println(req);

		if(!c.isEmpty()) {
			System.out.println("Issue : Unused tags with main Item : " + mainItem.getNom());
		}

		if (type2KeyWord.equals("Enchainement")) {
			fiche2 = fiche2 + "#####" + keyword + "######\n\n";
			fiche2 = addType(idKeyWord, fiche2, "Enchainement");
		}

		if (AllTags.contains("Echauffement") ) {
			fiche2 = "##### Echauffements ######\n\n" + fiche2;
			fiche2 = addType(idKeyWord, fiche2, "Echauffement");
		}

		if (type2KeyWord.equals("Technique")) {
			fiche2 = fiche2 + "##### Technique ######\n\n";
			fiche2 = addType(idKeyWord, fiche2, "Technique");
		}

		if (AllTags.contains("Educatif")) {
			fiche2 =  fiche2 + "##### Educatif ######\n\n";
			fiche2 = addType(idKeyWord, fiche2, "Educatif");
		}
		if (AllTags.contains("Technique") && !type2KeyWord.equals("Technique")) {
			fiche2 = fiche2 + "##### Technique ######\n\n";
			fiche2 = addType(idKeyWord, fiche2, "Technique");
		}

		if (AllTags.contains("Enchainement") && !type2KeyWord.equals("Enchainement")) {
			// To complete
			fiche2 = fiche2 + "##### Enchainement ######\n\n";

			List<Integer> IDEnchSpec = ((ItemDAOJdbcImpl) ItemDAO).getIDEnchSpec(nomEnchSpec);
			fiche2 = fetchEnchForItem(idKeyWord, fiche2, IDEnchSpec);
		}

		if ((AllTags.contains("Poomsae") || AllTags.contains("Gyeorugi") || AllTags.contains("Hoshinsul") )&& !type2KeyWord.equals("Enchainement")) {
			// To complete
			fiche2 = fiche2 + "##### Enchainements spéciaux ######\n\n";

			List<String> AllTagsToInclude = new ArrayList();
			if (AllTags.contains("Poomsae")) {
				AllTagsToInclude.add("Poomsae");
			}
			if (AllTags.contains("Gyeorugi")) {
				AllTagsToInclude.add("Gyeorugi");
			}
			if (AllTags.contains("Hoshinsul")) {
				AllTagsToInclude.add("Hoshinsul");
			}

			fiche2 = fetchEnchForItemWithTags(idKeyWord, fiche2, AllTagsToInclude);
		}

		if (AllTags.contains("Etirement")) {
			fiche2 = fiche2 + "##### Etirement ######\n\n";
			fiche2 = addType(idKeyWord, fiche2, "Etirement");
		}

		allres.add(req);
		allres.add(fiche2);
		return allres;
	}

	public static List<String> excecuteRequestManager(String keyword, List<String> details, String path) throws DALException, FileNotFoundException {
		List<String> allres = new ArrayList();

		String fiche = "";
		String requete = "Test de l'affichage de la requête";
		// Type du mot-clef : Item ou Tag
		String typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(keyword.trim());
		// ID du mot clef
		int idKeyWord = genericRetrieveId(keyword.trim());
		Item mainItem = null;
		Tag mainTag = null;
		String exportName = "";

		// Si Item, on récupère son Type plus particulier
		if (typeKeyWord.equals("Item")) {
			mainItem = ((ItemDAOJdbcImpl) ItemDAO).selectById(idKeyWord);
		}
		if (typeKeyWord.equals("Tag")) {
			mainTag = ((TagDAOJdbcImpl) TagDAO).selectById(idKeyWord);
		}


		// Liste des Items qu'il faudra afficher
		List<Item> itemsToDisplay = new ArrayList();

		// Pour découper la liste des détails 
		List<String> allTagsString = new ArrayList();
		List<Item> allItems = new ArrayList();

		for (String str_temp : details) {
			String cur_type = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(str_temp.trim());
			if (cur_type.equals("Item")) {
				Item it = ((ItemDAOJdbcImpl) ItemDAO).selectByNom(str_temp);
				allItems.add(it);
			}
			else {
				allTagsString.add(str_temp);
			}
		}

		if (typeKeyWord.equals("Item")) {
			// Si le keyWord est un item
			//			for (String disp : allTagsString ) {
			//				System.out.println("all tags from details : " + disp + "\n");
			//			}
			exportName = mainItem.getNom();
			List<String> allres2 = processItemRequest(mainItem, allItems, allTagsString, fiche); // On regroupe finalement requetes et affichage
			fiche = allres2.get(1);
			requete = allres2.get(0);
		}
		else if (typeKeyWord.equals("Tag")){
			// Si le keyWord est un Tag
			exportName = mainTag.getNom();
			List<Item> resList = processTagRequest(mainTag, allItems, allTagsString);

			//List<String> mainAllTagsDisplay = new ArrayList(allTagsString);
			//mailAllTagsDisplay.add(mainTag.getNom());

			requete = "<html> Requete sur les items ayant le tag principal : " + mainTag.getNom(); 

			if (!allTagsString.isEmpty()) {
				requete = requete +   " et tous les tags secondaires : " + allTagsString;
			}

			if (!allItems.isEmpty()) {
				String allItemsDisplay = "[";
				for (Item it : allItems) {
					allItemsDisplay = allItemsDisplay + it.getNom() + ", ";
				}
				allItemsDisplay = allItemsDisplay + "]";
				requete = requete + " <br> et contenant également toutes les techniques suivantes : " + allItemsDisplay;
			}

			requete = requete + "</html>";

			for (Item it : resList) {
				fiche = fiche + "##########\n" + it + "\n";
			}
		}
		else {
			fiche = "Incorrect request";
			allres.add(requete);
			allres.add(fiche);
			return allres;
		}

		// Export 

		try (PrintWriter out = new PrintWriter(path + exportName + ".txt")) {
			out.println(fiche);
		}

		allres.add(requete);
		allres.add(fiche);
		return allres;
	}

	//	public static String excecuteRequest(String keyword, List<String> details, String typeSelected, String path) throws DALException, FileNotFoundException {
	//		// generates a .txt file with the result of the request
	//		String typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(keyword.trim());
	//		int idKeyWord = genericRetrieveId(keyword.trim());
	//		String type2KeyWord =((ItemDAOJdbcImpl) ItemDAO).retrieveType2(idKeyWord);
	//		System.out.println(type2KeyWord);
	//		String fiche = "";
	//
	//		if ( typeSelected.equals("Choisir un type") ){
	//			fiche = "Merci de choisir un type pour la requête";
	//			return fiche;
	//		}
	//		
	//		String typeKeyWord2 = type2KeyWord;
	//		if (typeKeyWord2.equals("Echauffement")) {
	//			typeKeyWord2 = "Technique";
	//		}
	//		if (typeKeyWord2.equals("Etirement")) {
	//			typeKeyWord2 = "Technique";
	//		}
	//		if (typeKeyWord2.equals("Educatif")) {
	//			typeKeyWord2 = "Technique";
	//		}
	//
	//		if (   !typeSelected.equals(typeKeyWord2)  ){
	//			fiche = "Type sélectionné incorrect";
	//			return fiche;
	//		}
	//		
	//		if (idKeyWord == 0) {
	//			System.out.println("KeyWord not found");
	//		}
	//		else {		
	//			if (typeKeyWord.equals("Item")) {	
	//				
	//				if (type2KeyWord.equals("Enchainement")) {
	//					fiche = fiche + "#####" + keyword + "######\n\n";
	//					fiche = addType(idKeyWord, fiche, "Enchainement");
	//				}
	//							
	//				if (details.contains("Echauffement") ) {
	//					fiche = "##### Echauffements ######\n\n" + fiche;
	//					fiche = addType(idKeyWord, fiche, "Echauffement");
	//				}
	//				
	//				if (type2KeyWord.equals("Technique")) {
	//					fiche = fiche + "##### Technique ######\n\n";
	//					fiche = addType(idKeyWord, fiche, "Technique");
	//				}
	//				
	//				if (details.contains("Educatif")) {
	//					fiche =  fiche + "##### Educatif ######\n\n";
	//					fiche = addType(idKeyWord, fiche, "Educatif");
	//				}
	//				if (details.contains("Technique") && !type2KeyWord.equals("Technique")) {
	//					fiche = fiche + "##### Technique ######\n\n";
	//					fiche = addType(idKeyWord, fiche, "Technique");
	//				}
	//				
	//				if (details.contains("Enchainement") && !type2KeyWord.equals("Enchainement")) {
	//					// To complete
	//					fiche = fiche + "##### Enchainement ######\n\n";
	//					fiche = fetchEnchForItem(idKeyWord, fiche);
	//				}
	//				if (details.contains("Etirement")) {
	//					fiche = fiche + "##### Etirement ######\n\n";
	//					fiche = addType(idKeyWord, fiche, "Etirement");
	//				}
	//
	//			}
	//			// Si le keyword est un tag
	//			else if (typeKeyWord.equals("Tag")) {
	//				// Get Items with the tag + the types in details
	//				
	//				
	//				// 
	//				
	//				
	//			}
	//		}
	//		System.out.println(fiche);
	//
	//		// Export 
	//				String exportName = mainItem.getNom();
	//	try (PrintWriter out = new PrintWriter(path + exportName + ".txt")) {
	//		out.println(fiche);
	//		}
	//	
	//	return fiche;
	//}



	//			out.println(fiche);
	//		}
	//		return fiche;
	//	}

	public static void processDB() throws DALException {
		// generate a table with all possible keywords and Tagwords 

		Connection con = null;
		Statement stmt = null;


		// Adding all the items 
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID, Nom FROM Items ;");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						((ItemDAOJdbcImpl) ItemDAO).InsertItemsTagsList( listItemsReq.getInt("ID") , listItemsReq.getString("Nom"), "Item");
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}

		/// Adding all the tags
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID, Nom FROM Tags ;");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						((ItemDAOJdbcImpl) ItemDAO).InsertItemsTagsList( listItemsReq.getInt("ID") , listItemsReq.getString("Nom"), "Tag");
					}
					try {
						listItemsReq.close();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DALException("Erreur closeResult");
					}
				}
				else {
					if(query.getUpdateCount() == -1) {
						break;
					}
				}
				isResultSet = query.getMoreResults();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
				throw new DALException("Erreur fermeture");
			}
		}


	}

}