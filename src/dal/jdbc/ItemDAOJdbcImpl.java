package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Enchainement;
import bo.Item;
import bo.Tag;
import bo.Technique;
import dal.ConnectionProvider;
import dal.DAO;
import dal.Factory;
import dal.exceptions.DALException;


public class ItemDAOJdbcImpl implements DAO<Item>{

	public void insertMiniItem(String Nom, String Type) throws DALException {
		if (checkInsertNom(Nom)) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionProvider.getConnection();
				stmt = con.prepareStatement("insert into ItemsID (Nom, Type) values (?,?)");
				stmt.setString(1, Nom);
				stmt.setString(2, Type);
				int rows = stmt.executeUpdate();

				if (rows != 1) {
					throw new DALException("Erreur insert");
				}
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
		//else {System.out.println("Duplicated item");}

	}


	@Override
	public void insert(Item item) throws DALException {

		if (checkInsert(item, "Items")) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionProvider.getConnection();
				stmt = con.prepareStatement("insert into Items (ID, Type, Nom, Descriptif, niveau, Filename, Nombre, Pratiquants, Dispositif) values (?,?,?,?,?,?,?,?,?)");
				stmt.setInt(1, retrieveId(item.getNom()));
				stmt.setString(2, item.getType());
				stmt.setString(3, item.getNom());
				stmt.setString(4, item.getDescriptif());
				stmt.setInt(5, item.getNiveau());
				stmt.setString(6, item.getFilename());
				stmt.setString(7, item.getNombre());
				stmt.setString(8, item.getPratiquants());
				stmt.setString(9, item.getDispositif());
				int rows = stmt.executeUpdate();

				if (rows != 1) {
					throw new DALException("Erreur insert");
				}
				else {
					insertTags(item);
					insertTagsItems(item);
				}
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
		else {System.out.println("Duplicated item");}
	}

	public void insertEnchainement(Enchainement item) throws DALException {
		String db = "Enchainements";
		if (checkInsert(item, db)) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionProvider.getConnection();
				if (!item.getDetails().isEmpty()) {
					stmt = con.prepareStatement("insert into Enchainements (ID, Etapes, Details) values (?,?,?)");
					stmt.setInt(1, retrieveId(item.getNom()));
					stmt.setString(2, item.getEtapesString(item.getEtapes()));
					stmt.setString(3, item.getEtapesString(item.getDetails()));
				}
				else {
					stmt = con.prepareStatement("insert into Enchainements (ID, Etapes) values (?,?)");
					stmt.setInt(1, retrieveId(item.getNom()));
					stmt.setString(2, item.getEtapesString(item.getEtapes()));
				}
				int rows = stmt.executeUpdate();

				if (rows != 1) {
					throw new DALException("Erreur insert");
				}
				else {
					insertTags(item);
				}
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
		else {System.out.println("Duplicated item");}
	}

	public void insertTagsItems(Item item) throws DALException {

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.prepareStatement("insert into ItemsItems (IDItems1, IDItems2) values (?,?)");
			int idItem = retrieveId(item.getNom());
			stmt.setInt(1, idItem);

			List<String> Tags = item.getTags();
			for (String temp : Tags) {
				stmt.setInt(1, idItem);
				//Tag temp_tag = new Tag(temp);
				int idtag = retrieveId(temp); // au lieu de : int idtag = retrieveId(temp_tag.getNom());
				stmt.setInt(2,idtag);

				if (idtag != -1 && checkInsertCouple(idItem, idtag, "ItemsItems") ) {
					int rows = stmt.executeUpdate();
					if (rows != 1) {
						throw new DALException("Erreur insert");
					}
				}

				// Also making ItemsItems table symmetric (good idea ?)
				if (idtag != -1 && checkInsertCouple(idtag, idItem, "ItemsItems") ) {
					stmt.setInt(1,idtag);
					stmt.setInt(2,idItem);
					int rows = stmt.executeUpdate();
					if (rows != 1) {
						throw new DALException("Erreur insert");
					} 
				}

			}
			// Also inserting Items pointing to himself (good idea ?)
			stmt.setInt(2,idItem);
			stmt.setInt(2,idItem);
			if (checkInsertCouple(idItem, idItem, "ItemsItems") ) {
				int rows = stmt.executeUpdate();
				if (rows != 1) {
					throw new DALException("Erreur insert");
				} 
			}

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

	public void insertTags(Item item) throws DALException {

		DAO<Tag> TagDAO;
		TagDAO = Factory.getTagDAO();

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.prepareStatement("insert into ItemsTags (IDItems, IDTags) values (?,?)");
			int idItem = retrieveId(item.getNom());
			stmt.setInt(1, idItem );

			List<String> Tags = item.getTags();
			for (String temp : Tags) {
				Tag temp_tag = new Tag(temp);
				int idtag = TagDAO.retrieveId(temp_tag.getNom());
				stmt.setInt(2,idtag);

				if (idtag != -1 && checkInsertCouple(idItem, idtag, "ItemsTags" ) ) {
					int rows = stmt.executeUpdate();
					if (rows != 1) {
						throw new DALException("Erreur insert");
					} 
				}
			}


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

	@Override
	public void update(Item obj) throws DALException {

	}

	@Override
	public Item selectById(int noItem) throws DALException {

		Connection con = null;
		Statement stmt = null;
		Item it = null;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from Items where ID = ?");
			query.setInt(1, noItem);
			ResultSet listItemsReq = query.executeQuery();
			if (listItemsReq.next()) {
				String type = listItemsReq.getString("Type");
				if (!type.equals("Enchainement")) {
					// type, Nom, Descriptif,Tags, Niveau, Filename, Nombre, Public, Dispositif
					String Nom = listItemsReq.getString("Nom");
					String Descriptif = listItemsReq.getString("Descriptif");
					int Niveau = listItemsReq.getInt("Niveau");
					String fileName = listItemsReq.getString("Filename");
					String Nombre = listItemsReq.getString("Nombre");
					String Pratiquant = listItemsReq.getString("Pratiquants");
					String Dispositif = listItemsReq.getString("Dispositif");
					it = new Technique( type, Nom ,Descriptif, null , Niveau, fileName, Nombre, Pratiquant, Dispositif  );	
					it.setId(noItem);
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
					List<String> etapes = getEtapesFromDB(noItem);
					List<String> details = getDetailsFromDB(noItem);
					//String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif
					it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details, Nombre, Pratiquant, Dispositif);
					it.setId(noItem);
				}
			}
			try {
				listItemsReq.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		//System.out.println("Retrieved Id for item " + Nom + " : " + idItem);	

		return it;
	}

	public Item selectByNom(String Nom) throws DALException {
		Connection con = null;
		Statement stmt = null;
		Item it = null ;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from Items where Nom = ?");
			query.setString(1, Nom);
			ResultSet listItemsReq = query.executeQuery();
			if (listItemsReq.next()) {
				String type = listItemsReq.getString("Type");
				if (!type.equals("Enchainement")) {
					// type, Nom, Descriptif,Tags, Niveau, Filename, Nombre, Public, Dispositif
					int ID = listItemsReq.getInt("ID");
					String Descriptif = listItemsReq.getString("Descriptif");
					int Niveau = listItemsReq.getInt("Niveau");
					String fileName = listItemsReq.getString("Filename");
					String Nombre = listItemsReq.getString("Nombre");
					String Pratiquant = listItemsReq.getString("Pratiquants");
					String Dispositif = listItemsReq.getString("Dispositif");
					it = new Technique( type, Nom ,Descriptif, null , Niveau, fileName, Nombre, Pratiquant, Dispositif  );
					it.setId(ID);
				}
				if (type.equals("Enchainement")) {
					// Requetes pour chercher les étapes !	
					int ID = listItemsReq.getInt("ID");
					String Descriptif = listItemsReq.getString("Descriptif");
					int Niveau = listItemsReq.getInt("Niveau");
					String fileName = listItemsReq.getString("Filename");
					String Nombre = listItemsReq.getString("Nombre");
					String Pratiquant = listItemsReq.getString("Pratiquants");
					String Dispositif = listItemsReq.getString("Dispositif");
					List<String> etapes = getEtapesFromDB(ID);
					List<String> details = getDetailsFromDB(ID);
					//String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif
					it = new Enchainement(Nom, Descriptif, null, etapes, Niveau, fileName, details, Nombre, Pratiquant, Dispositif);
					it.setId(ID);
				}
			}
			try {
				listItemsReq.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		//System.out.println("Retrieved Id for item " + Nom + " : " + idItem);	

		return it;
	}

	@Override
	public List<Item> selectAll() throws DALException {		
		return null;
	}

	@Override
	public void delete(int idItem) throws DALException {
	}

	public int retrieveId(String  Nom) throws DALException {
		int idItem = -1;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID from ItemsID where Nom = ?");
			query.setString(1, Nom);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				idItem = rs.getInt("ID");
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		//System.out.println("Retrieved Id for item " + Nom + " : " + idItem);
		return idItem;
	}

	public String retrieveType2(int  ID) throws DALException {
		String type = "";
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Type from Items where ID = ?");
			query.setInt(1, ID);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				type = rs.getString("Type");
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		//System.out.println("Retrieved Id for item " + Nom + " : " + idItem);
		return type;
	}


	public boolean checkInsertCouple( int ID1 ,int ID2, String db) throws DALException {
		boolean checked = true;
		Connection con = null;
		Statement stmt = null;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = null ;

			if (db.equals("ItemsItems"))  {
				query = con.prepareStatement("select * from " + db +" where IDItems1 = ? AND  IDItems2 = ?;");
				query.setInt(1,  ID1 );
				query.setInt(2,  ID2 );
			}

			if (db.equals("ItemsTags"))  {
				query = con.prepareStatement("select * from " + db + " where IDItems = ? AND  IDTags = ? ;");
				query.setInt(1,  ID1 );
				query.setInt(2,  ID2 );
			}

			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				checked = false;
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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


		return checked;

	}

	public boolean checkInsert(Item item, String db)throws DALException {
		boolean checked = true;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = null ;
			if (db.equals("Items"))  {
				query = con.prepareStatement("select Nom from " + db +" where Nom = ?");
				query.setString(1, item.getNom());
			}
			if (db.equals("Enchainements"))  {
				query = con.prepareStatement("select ID from " + db +" where ID = ?");
				query.setInt(1,  retrieveId( item.getNom() ) );
			}

			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				checked = false;
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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
		return checked;
	}

	public boolean checkInsertNom(String Nom)throws DALException {
		boolean checked = true;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Nom from ItemsID where Nom = ?");
			query.setString(1, Nom);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				checked = false;
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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
		return checked;
	}

	public List<String> getAllTypes(String types) throws DALException {
		ResultSet listEnchreq = null ;
		List<String> listEnch = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Nom FROM ItemsID WHERE Type = '" + types + "' ;");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					listEnchreq = query.getResultSet() ;
					while(listEnchreq.next()) {
						listEnch.add(listEnchreq.getString("Nom"));
					}
					try {
						listEnchreq.close();
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

		return listEnch;

	}

	public int getNewNameType(String types) throws DALException {

		List<String> listEnch = getAllTypes(types);
		int maxint = 1;
		int curint = 0;
		String curintString = null; 
		if (!listEnch.isEmpty()) {
			for (String nameI : listEnch) {
				// System.out.println("Namei : " + nameI);
				String[] splitted = nameI.split("-");
				if (splitted.length > 1 ) {
					curintString = splitted[1];
				}
				curint = Integer.parseInt(curintString);
				if (curint > maxint) {
					maxint = curint;
				}
			}
		}
		return curint + 1;

	}

	public void sqlItemRequest(int IDitem, int IDTag) {

	}

	public boolean checkInsertItemsTagsList(String Nom) throws DALException {
		boolean checked = true;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Nom from ItemsTagsList where Nom = ?");
			query.setString(1, Nom);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				checked = false;
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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
		return checked;
	}

	public void InsertItemsTagsList(int ID, String Nom, String type) throws DALException {
		if (checkInsertItemsTagsList(Nom)) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionProvider.getConnection();
				stmt = con.prepareStatement("insert into ItemsTagsList (ID, Nom, Type) values (?,?,?);");
				stmt.setInt(1, ID);
				stmt.setString(2, Nom);
				stmt.setString(3, type);
				int rows = stmt.executeUpdate();

				if (rows != 1) {
					throw new DALException("Erreur insert");
				}
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
	}

	public List<String> getAllNames() throws DALException {
		ResultSet listEnchreq = null ;
		List<String> listEnch = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Nom FROM ItemsTagsList ;");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					listEnchreq = query.getResultSet() ;
					while(listEnchreq.next()) {
						listEnch.add(listEnchreq.getString("Nom"));
					}
					try {
						listEnchreq.close();
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

		return listEnch;

	}

	public List<String> getAllNamesTags() throws DALException {
		ResultSet listEnchreq = null ;
		List<String> listEnch = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Nom FROM ItemsTagsList where Type = 'Tag';");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					listEnchreq = query.getResultSet() ;
					while(listEnchreq.next()) {
						listEnch.add(listEnchreq.getString("Nom"));
					}
					try {
						listEnchreq.close();
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

		return listEnch;

	}


	public String retrieveType(String det) throws DALException {
		String type = "";
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Type from ItemsTagsList where Nom = ?");
			query.setString(1, det);
			ResultSet rs = query.executeQuery();
			if (rs.next() && !type.equals("Item")) {
				type = rs.getString("Type");
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		return type;
	}

	public List<String> getEtapesFromDB(int ID) throws DALException {
		List<String> etapes = new ArrayList();
		String allEtapes = "";

		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Etapes from Enchainements where ID = ?");
			query.setInt(1, ID);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				allEtapes = rs.getString("Etapes");
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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


		String[] splAllEtapes = allEtapes.split(";");
		for (String temp : splAllEtapes) {
			etapes.add(temp);
		}
		return etapes;	
	}

	public List<String> getDetailsFromDB(int ID) throws DALException {
		List<String> details = new ArrayList();
		String allEtapes = "";

		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select Details from Enchainements where ID = ?");
			query.setInt(1, ID);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				allEtapes = rs.getString("Details");
			}
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DALException("Erreur closeResult");
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

		if (allEtapes!=null) {
			String[] splAllEtapes = allEtapes.split(";");
			for (String temp : splAllEtapes) {
				details.add(temp);
			}
		}
		return details;	
	}

	public List<Integer> getIDEnchSpec(List<String> nomEnchSpec) throws DALException {
		List<Integer> results = new ArrayList<Integer>();
		Connection con = null;
		Statement stmt = null;
		ResultSet listEnchreq = null ;

		String constraints = "Nom = '" + nomEnchSpec.get(0) + "'";

		for (int i = 1 ; i < nomEnchSpec.size(); i++ ) {
			constraints = constraints + "or Nom = '" + nomEnchSpec.get(i) + "'";
		}

		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID FROM Tags where " + constraints + ";");
			boolean isResultSet = query.execute();

			while (true) {
				if (isResultSet) {
					listEnchreq = query.getResultSet() ;
					while(listEnchreq.next()) {
						results.add(listEnchreq.getInt("ID"));
					}
					try {
						listEnchreq.close();
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


		return results;
	}


}
