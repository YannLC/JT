package dal;

import dal.exceptions.DALException;
import java.util.List;

/**
 * @author Edouard
 */

public interface DAO<T> {
	
	void insert(T obj) throws DALException;
    void update(T obj) throws DALException;
    T selectById(int idObj) throws DALException;
    List<T> selectAll() throws DALException;
    void delete(int idObj) throws DALException;

}
