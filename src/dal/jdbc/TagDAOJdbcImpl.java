package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import bo.Enchainement;
import bo.Item;
import bo.Tag;
import bo.Technique;
import dal.ConnectionProvider;
import dal.DAO;
import dal.Factory;
import dal.exceptions.DALException;

public class TagDAOJdbcImpl  implements DAO<Tag> {

	public void insert(Tag tag) throws DALException {

		if (checkInsert(tag, "Tags")) {
			Connection con = null;
			PreparedStatement stmt = null;
			try {
				con = ConnectionProvider.getConnection();
				stmt = con.prepareStatement("insert into Tags (Nom) values (?)");
				stmt.setString(1,tag.getNom());

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
		else { 
			System.out.println("Duplicated Tag");
		}
	}

	public Tag selectById(int i) throws DALException {Connection con = null;
	Statement stmt = null;
	Tag tag = null ;
	
	try {
		con = ConnectionProvider.getConnection();
		stmt = con.createStatement();
		PreparedStatement query = con.prepareStatement("select * from Tags where ID = ?");
		query.setInt(1, i);
		ResultSet listItemsReq = query.executeQuery();
		if (listItemsReq.next()) {
			tag = new Tag(listItemsReq.getString("Nom"));
			tag.setId(i);		
		}
		try {
			listItemsReq.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DALException("Erreur closeResult");
		}
	} catch (SQLException throwables) {
	} finally {
		try {
			con.close();
			stmt.close();
		} catch (Exception e) {
			throw new DALException("Erreur fermeture");
		}
	}

	//System.out.println("Retrieved Id for item " + Nom + " : " + idItem);	
	
	return tag;}

	public void delete(int i) {}

	public List<Tag> selectAll() {return null;}

	public void update(Tag tag) {}
	
	public void insertMiniItem(String Nom) {}

	public int retrieveId(String Nom) throws DALException {
		int idTag = -1;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID from Tags where Nom = ?");
			query.setString(1, Nom);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				idTag = rs.getInt("ID");
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

		//System.out.println("Retrieved Id for tag " +Nom + " : " + idTag);
		return idTag;
	}

	public boolean checkInsert(Tag tag, String db)throws DALException {
		boolean checked = true;
		Connection con = null;
		Statement stmt = null;
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select ID from " + db + " where Nom = ?");
			query.setString(1, tag.getNom());
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
	
	public Tag selectByNom(String Nom) throws DALException {
		Connection con = null;
		Statement stmt = null;
		Tag tag = null ;
		
		try {
			con = ConnectionProvider.getConnection();
			stmt = con.createStatement();
			PreparedStatement query = con.prepareStatement("select * from Tags where Nom = ?");
			query.setString(1, Nom);
			ResultSet listItemsReq = query.executeQuery();
			if (listItemsReq.next()) {
				int ID = listItemsReq.getInt("ID");
				tag = new Tag(Nom);
				tag.setId(ID);		
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
		
		return tag;
	}
	
	
}
