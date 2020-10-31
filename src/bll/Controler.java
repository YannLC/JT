package bll;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

//import bo.Item; 
import bo.Technique;
import bo.Enchainement;

import java.sql.* ;  // for standard JDBC programs

public class Controler {

	
	public static void main(String[] args) throws SQLException {
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
			   System.exit(1);
			}
		
		List<String> Tags = new ArrayList<>();
		Tags.add("Poomsae 6");
		Tags.add("Coup de Pied");
		
		Technique dollyo = new Technique("Dollyo", "Coup de pied circulaire", Tags);
		System.out.println(dollyo);
		
		List<String> Tags2 = new ArrayList<>();
		Tags2.add("Poings");
		Tags2.add("Coup de Pied");
		ArrayList<String> Etapes = new ArrayList<>();
		Etapes.add(0, "Crochet du gauche");
		Etapes.add(1, "Crochet du droit");
		Etapes.add(2, "Uppercut du droit");
		Etapes.add(3, "Murup du droit");
		Etapes.add(4, "Pause devant, coup de coude du gauche");
		Etapes.add(5, "Donne le dos en avançant, coup de coude du droit");
				
		Enchainement exo1 = new Enchainement("Exercice 1", "Travail de poings au sol", Tags2, Etapes);
		System.out.println(exo1);
		
		String dbURL = "jdbc:sqlserver://127.0.0.1\\JTDB;databaseName=TutorialDB;Integrated Security=true;";  
		Connection conn = DriverManager.getConnection(dbURL, "User", "ISTS1985");
		if (conn != null) {
		    System.out.println("Connected");
		}
				
		
		 if (conn != null) {
		        try {
		            conn.close();
		            System.out.println("Connection properly closed !");
		        } catch (SQLException e) { /* ignored */}
		    }
		
	}

}
