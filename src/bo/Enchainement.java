package bo;

import java.util.List;

public class Enchainement extends Item {
	List<String> etapes;
	List<String> details;

	public Enchainement(String Nom, String Descriptif, List<String> Tags, List<String> etapes, int Niveau, String Filename, List<String> details, String Nombre, String Pratiquants, String Dispositif) {
		super("Enchainement", Nom, Descriptif,Tags, Niveau, Filename, Nombre, Pratiquants, Dispositif);
		this.etapes = etapes;
		this.details = details;
	}

	public String toString() {
		String output = "Détail de l'enchainement : '" + this.Descriptif + "'\nNiveau : " + this.Niveau + "\n";
		int i = 1; 
		int l_detail = this.details.size();
		for (String temp : this.etapes) {
			if (i-1 < l_detail) {
				String detail_temp =  this.details.get(i-1);
				output  = output + i + "- " + temp.trim() + " || " + detail_temp + "\n";
			}
			else {
				output  = output + i + "- " + temp.trim() + "\n";
			}
			i++;
		}

		return output;
	}

	public List<String> getEtapes() {
		return etapes;
	}

	public List<String> getDetails() {
		return details;
	}

	
	public void setEtapes(List<String> etapes) {
		this.etapes = etapes;
	}

	public String getEtapesString(List<String> etapes) {
		String s = "";
		for (String et : etapes) {
			s = s + ";" + et.trim();
		}
		return s.substring(1);
	}
}
