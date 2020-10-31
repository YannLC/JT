package dal;

import dal.jdbc.ItemDAOJdbcImpl;


public class Factory {
	
	public static DAO getItemDAO() {
		return new ItemDAOJdbcImpl();
	}
}
