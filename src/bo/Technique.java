package bo;

import java.util.List;

public class Technique extends Item {


	public Technique(String type, String Nom, String Descriptif, List<String> Tags, int Niveau, String Filename, String Nombre, String Public, String Dispositif) {
		// TODO Auto-generated constructor stub
		super(type, Nom, Descriptif,Tags, Niveau, Filename, Nombre, Public, Dispositif);
		this.Nom = Nom;
	}

	
	
	public String toString() {
		String output = "";
		output = "Technique : " + Type + "\nNom : " + Nom + "\nNiveau : " + Niveau + "\nDecriptif : " + Descriptif + "\nTags : " + Tags + "\n";
		return output;
	}

	public String toStringMini() {
		String output = "";
		output = "Nom : " + Nom + "\nNiveau : " + Niveau + "\nDecriptif : " + Descriptif;
		return output;
	}


	public String getNom() {
		return Nom;
	}



	public void setNom(String nom) {
		Nom = nom;
	}
}
