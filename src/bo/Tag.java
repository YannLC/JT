package bo;

public class Tag {
	String Nom;
	int Id; 
	public Tag(String Nom) {
		this.Nom = Nom;
		this.Id = 0;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String tag) {
		this.Nom = tag;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}

	public int retrieveId(){
		int idDB = 0;
		
		return idDB;
	}
	
}
