package dal;

import dal.jdbc.ItemDAOJdbcImpl;
import dal.jdbc.TagDAOJdbcImpl;


public class Factory {
	
	public static DAO getItemDAO() {
		return new ItemDAOJdbcImpl();
	}
		
	public static DAO getTagDAO() {
		return new TagDAOJdbcImpl();
	}
}
