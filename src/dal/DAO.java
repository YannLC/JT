package dal;

import dal.exceptions.DALException;
import java.util.List;


public interface DAO<T> {
	
	void insert(T obj) throws DALException;
    void update(T obj) throws DALException;
    T selectById(int idObj) throws DALException;
    List<T> selectAll() throws DALException;
    void delete(int idObj) throws DALException;
    int retrieveId(String Nom) throws DALException;
    boolean checkInsert(T obj, String s) throws DALException;

}
