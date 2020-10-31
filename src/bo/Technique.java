package bo;

import java.util.List;

public class Technique extends Item {

	public Technique(String Type, String Descriptif, List<String> Tags) {
		// TODO Auto-generated constructor stub
		super(Type, Descriptif,Tags);
	}

	public String toString() {
		String output = "";
		output = "Technique : " + Type + "\n Decriptif : " + Descriptif + "\n Tags : " + Tags;
		return output;
	}
}
