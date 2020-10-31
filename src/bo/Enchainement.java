package bo;

import java.util.ArrayList;
import java.util.List;

public class Enchainement extends Item {
	ArrayList<String> etapes = new ArrayList<String>();
	
	public Enchainement(String Type, String Descriptif, List<String> Tags, ArrayList<String> etapes) {
		super(Type, Descriptif,Tags);
		this.etapes = etapes;
	}

	public String toString() {
		String output = "Détail de l'enchainement : '" + this.Descriptif + "'\n";
		int i = 1; 		
		for (String temp : this.etapes) {
			output  = output + i + "- " + temp + "\n";
			i++;
		}
		
		return output;
	}
	
}
