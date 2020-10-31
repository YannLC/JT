package bo;

import java.util.List;

public abstract class Item {
	int id;
	String Type;
	String Descriptif;
	List<String> Tags;
	
	public Item( String Type, String Descriptif, List<String> Tags) {
		this.id = 0;
		this.Type = Type;
		this.Descriptif = Descriptif;
		this.Tags = Tags;
	}
	
}
