package bo;

import java.util.List;

public abstract class Item {
	int id;
	int Niveau = 0;
	String Nom;
	String Type;
	String Descriptif;
	List<String> Tags;
	String Filename;
	String Nombre;
	String Pratiquants;
	String Dispositif;
	
	public Item( String Type, String Nom, String Descriptif, List<String> Tags, int Niveau, String Filename, String Nombre, String Pratiquants, String Dispositif) {
		this.id = 0;
		this.Nom = Nom;
		this.Type = Type;
		this.Descriptif = Descriptif;
		this.Niveau = Niveau;
		this.Tags = Tags;
		this.Filename = Filename;
		this.Nombre = Nombre;
		this.Pratiquants = Pratiquants;
		this.Dispositif = Dispositif;
	}

	public Item( String Type, String Nom, String Descriptif, List<String> Tags, int Niveau, String Filename) {
		this.id = 0;
		this.Nom = Nom;
		this.Type = Type;
		this.Descriptif = Descriptif;
		this.Niveau = Niveau;
		this.Tags = Tags;
		this.Filename = Filename;
		this.Nombre = "Seul";
		this.Pratiquants = "Non-spécifique";
		this.Dispositif = "Rien";
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDescriptif() {
		return Descriptif;
	}

	public void setDescriptif(String descriptif) {
		Descriptif = descriptif;
	}

	public List<String> getTags() {
		return Tags;
	}

	public void setTags(List<String> tags) {
		Tags = tags;
	}

	public int getNiveau() {
		return Niveau;
	}

	public void setNiveau(int niveau) {
		Niveau = niveau;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		Filename = filename;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getPratiquants() {
		return Pratiquants;
	}

	public void setPratiquants(String pratiquants) {
		Pratiquants = pratiquants;
	}

	public String getDispositif() {
		return Dispositif;
	}

	public void setDispositif(String dispositif) {
		Dispositif = dispositif;
	}
	
}
