package example.spring.restcrud.database.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;

public interface IOperations<T extends Serializable> {

	T findOneById(final int id) throws Throwable;
	
	List<T> findAll() throws Throwable;
	
	int create(final T entity) throws Throwable;
	
	T update(final T entity) throws Throwable;
	
	void delete(final T entity) throws Throwable;
	
	void deleteById(final int entityId) throws Throwable;
	
	List<T> findByCriteria(final Map<String, Object> eqMap, final Map<String, Object> neqMap, 
							final Map<String, Object> likMap, final MatchMode matchMode) throws Throwable;
}
