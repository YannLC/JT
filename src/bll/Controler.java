package bll;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.*;
import java.nio.charset.StandardCharsets;

import bo.Item;
import bo.Tag;
import bo.Technique;
import bo.Enchainement;
import dal.ConnectionProvider;
import dal.DAO;
import dal.exceptions.DALException;
import dal.jdbc.ItemDAOJdbcImpl;
import dal.Factory;


public class Controler {
	private static DAO<Item> ItemDAO;
	private static DAO<Tag> TagDAO;

	public static void main(String[] args) throws SQLException, DALException, IOException {

		// Loading SQL drivers
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
			System.exit(1);
		}


		ItemDAO = Factory.getItemDAO();
		TagDAO =  Factory.getTagDAO();

		//// Reading Tags and filling the Tags table
		String fileTags = "D:\\Documents\\TKD\\Cahier technique\\Database\\InitTables\\Tags.txt";
		readFileTag(fileTags);
		//List<Tag> listTag = readFileTag(fileTags);

		//// Reading item files and filling the DB
		String stemItems = "D:\\Documents\\TKD\\Cahier technique\\Database\\Exercices\\";

		readAllItems(stemItems);
		readAllFilesItems(stemItems);
		processDB();
		// Testing the checking of exisiting tags
		// String test = "Ap chagi";
		// System.out.println(" Tag : " + test + " exists in a database : " + existingTag(test) );

		// Testing a request

		String stemSaves = "D:\\Documents\\TKD\\Cahier technique\\Database\\Fiches\\";

		String keyword = "Ap Chagi"; // "Ap Chagi" 
		List<String> Details = new ArrayList<String>(); 
		Details.add("Echauffement");
		Details.add("Educatif");
		Details.add("Technique");

		excecuteRequest(keyword, Details, stemSaves);
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
							System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Enchainement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Enchainement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Enchainement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							System.out.println(Nom);
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
							System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Etirement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Etirement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Etirement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							System.out.println(Nom);
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
							System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Educatif");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Educatif");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Educatif-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							System.out.println(Nom);
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
							System.out.println(Nom);
							((ItemDAOJdbcImpl) ItemDAO).insertMiniItem(Nom, "Echauffement");
							newItem = false;
						}
						else {
							iEnch = ((ItemDAOJdbcImpl) ItemDAO).getNewNameType("Echauffement");
							// System.out.println("xxxxxxxxxxx Feedback : " + st);
							Nom = "Echauffement-" + iEnch;
							line = "Nom : " + Nom + "\n" + line;
							System.out.println(Nom);
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
					System.out.println(Nom);
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
						it = new Technique(Type, Nom, Descriptif, Tags_temp, Niveau, filename, Nombre, Pratiquants, Dispositif);
						listIT.add(it);
						ItemDAO.insert(it);
						//System.out.println(it);
					}
					else if (Type.equals("Enchainement")) {
						List<String> Etapes_temp =  new ArrayList<String>(Etapes); 
						List<String> Details_temp =  new ArrayList<String>(Details);
						//String autoname = "Enchainement " + ((ItemDAOJdbcImpl) ItemDAO).getNewNameEnchainement();
						it = new Enchainement(Nom, Descriptif, Tags, Etapes_temp, Niveau, filename, Details_temp, Nombre, Pratiquants, Dispositif);
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
			System.out.println("############################## \n ###################### \n Starting to scan the list of Items \n ###########");
			for (Item it : listIT) {
				//				if (it.getNom().equals("Poomsae 1")) {
				//					System.out.println(it);
				//				}
				if (it instanceof Technique) {
					System.out.println("Technique read ");
				}
				if (it instanceof Enchainement) {
					System.out.println("Enchainement read");
				}
			}
			//System.out.println(pathname);
		}
	}

	public static boolean existingTag (String tag) throws DALException {
		boolean exists = false;
		Tag tag_temp = new Tag(tag);
		exists = !(((ItemDAOJdbcImpl) ItemDAO).checkInsertNom(tag) && TagDAO.checkInsert(tag_temp, "Tags")) ;

		return exists;
	}


	public static void createTables(String pathToSQLFile) {

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


	public static String addType(int idK, String fiche, String type) throws DALException {
		String fiche2 = fiche;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from (select IDITems1 from ( select ID from Items where ID = ? ) as x inner join ItemsItems on ItemsItems.IDItems2 = x.ID ) as y inner join Items on Items.ID = y.IDItems1 and Items.Type = ?;");
			query.setInt(1, idK);
			query.setString(2, type);
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					ResultSet listItemsReq = query.getResultSet() ;
					while(listItemsReq.next()) {
						//	((ItemDAOJdbcImpl) ItemDAO).InsertItemsTagsList( listItemsReq.getInt("ID") , listItemsReq.getString("Nom"), "Item");
						//System.out.println(listItemsReq.getString("Nom"));
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
							//display = display.replace(".", ".\n");
							fiche2 = fiche2 + display + "\n##########\n";						
						}
						if (type.equals("Enchainement")) {
							// Requetes pour chercher les étapes !		
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

	public static void excecuteRequest(String keyword, List<String> details, String path) throws DALException, FileNotFoundException {
		// generates a .txt file with the result of the request
		String typeKeyWord = ((ItemDAOJdbcImpl) ItemDAO).retrieveType(keyword.trim());
		int idKeyWord = genericRetrieveId(keyword.trim());
		String type2KeyWord =((ItemDAOJdbcImpl) ItemDAO).retrieveType2(idKeyWord);;
		String fiche = "";
		if (idKeyWord == 0) {
			System.out.println("KeyWord not found");
		}
		else {		
			if (typeKeyWord.equals("Item")) {	
				if (details.contains("Echauffement")) {
					fiche = "##### Echauffements ######\n\n" + fiche;
					fiche = addType(idKeyWord, fiche, "Echauffement");
				}
				
				if (type2KeyWord.equals("Technique")) {
					fiche = fiche + "##### Technique ######\n\n";
					fiche = addType(idKeyWord, fiche, "Technique");
				}
				
				if (details.contains("Educatif")) {
					fiche =  fiche + "##### Educatif ######\n\n";
					fiche = addType(idKeyWord, fiche, "Educatif");
				}
				if (details.contains("Technique") && !type2KeyWord.equals("Technique")) {
					fiche = fiche + "##### Technique ######\n\n";
					fiche = addType(idKeyWord, fiche, "Technique");
				}
				
				if (details.contains("Enchainement")) {

				}
				if (details.contains("Etirement")) {
					fiche = fiche + "##### Etirement ######\n\n";
					fiche = addType(idKeyWord, fiche, "Etirement");
				}

			}
			// Si le keyword est un tag
			else if (typeKeyWord.equals("Tag")) {

			}
		}
		System.out.println(fiche);

		// Export 
		try (PrintWriter out = new PrintWriter(path + "requete.txt")) {
			out.println(fiche);
		}

	}

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