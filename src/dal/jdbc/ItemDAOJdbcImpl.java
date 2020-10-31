package dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import bo.Item;
import dal.ConnectionProvider;
import dal.DAO;
import dal.exceptions.DALException;


public class ItemDAOJdbcImpl implements DAO<Item>{

	@Override
	public void insert(Item item) throws DALException {
//		Connection con = null;
//		PreparedStatement stmt = null;
//		try {
//			con = ConnectionProvider.getConnection();
//			stmt = con.prepareStatement("insert into retraits (no_vente, rue, code_postal, ville) values (?,?,?,?)");
//			stmt.setInt(1, retrait.getVente().getNumero());
//			stmt.setString(2, retrait.getRue());
//			stmt.setString(3, retrait.getCp());
//			stmt.setString(4, retrait.getVille());
//			
//			int rows = stmt.executeUpdate();
//
//			if (rows != 1) {
//				throw new DALException("Erreur insert");
//			} 
//		} catch (SQLException throwables) {
//			throwables.printStackTrace();
//			throw new DALException("Erreur insert");
//		} finally {
//			try {
//				con.close();
//				stmt.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new DALException("Erreur close");
//			}
//		}
	}

	@Override
	public void update(Item obj) throws DALException {
		
	}

	@Override
	public Item selectById(int noItem) throws DALException {
//		Retrait retrait = null;
//		Connection con = null;
//		Statement stmt = null;
//		try {
//			con = ConnectionProvider.getConnection();
//			stmt = con.createStatement();
//			PreparedStatement query = con.prepareStatement(
//					"select * from retraits join ventes on retraits.no_vente=ventes.no_vente "
//					+ "join categories on categories.no_categorie = ventes.no_categorie "
//					+ "join utilisateurs on ventes.no_utilisateur = utilisateurs.no_utilisateur "
//					+ "where ventes.no_vente = ?");
//			query.setInt(1, noVente);
//			ResultSet rs = query.executeQuery();
//			if (rs.next()) {
//				retrait = new Retrait(
//						new Vente(rs.getInt("no_vente"), rs.getString("nom_article"), rs.getString("description"), rs.getDate("date_fin_encheres"), rs.getInt("prix_initial"), rs.getInt("prix_vente"), 
//								new Utilisateur(rs.getInt("no_utilisateur"),rs.getString("pseudo"),rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),
//										rs.getString("tel"), rs.getString("rue"), rs.getString("cp"), rs.getString("ville"), rs.getString("mdp"), rs.getInt("credit")), 
//								new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"))),
//						rs.getString("rue"), rs.getString("ville"),rs.getString("code_postal"));
//			}
//			try {
//				rs.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw new DALException("Erreur closeResult");
//			}
//		} catch (SQLException throwables) {
//			throwables.printStackTrace();
//		} finally {
//			try {
//				con.close();
//				stmt.close();
//			} catch (Exception e) {
//				throw new DALException("Erreur fermeture");
//			}
//		}
		return null;
	}

	@Override
	public List<Item> selectAll() throws DALException {		
		return null;
	}

	@Override
	public void delete(int idItem) throws DALException {
	}


}
