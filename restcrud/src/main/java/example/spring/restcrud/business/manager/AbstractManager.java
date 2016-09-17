package example.spring.restcrud.business.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.MatchMode;

import example.spring.restcrud.database.manager.IOperations;

public abstract class AbstractManager<T extends Serializable> implements IOperations<T> {

	@Override
	public T findOneById(final int id) throws Throwable {
		return getDbManager().findOneById(id);
	}
	
	@Override
	public List<T> findAll() throws Throwable {
		return getDbManager().findAll();
	}
	
	@Override
	public int create(final T entity) throws Throwable {
		return getDbManager().create(entity);
	}
	
	@Override
	public T update(final T entity) throws Throwable {
		return getDbManager().update(entity);
	}
	
	@Override
	public void delete(final T entity) throws Throwable {
		getDbManager().delete(entity);
	}
	
	@Override
	public void deleteById(final int entityId) throws Throwable {
		getDbManager().deleteById(entityId);
	}
	
	@Override
	public List<T> findByCriteria(final Map<String, Object> eqMap, final Map<String, Object> neqMap, 
									final Map<String, Object> likMap, final MatchMode matchMode) throws Throwable {
		return getDbManager().findByCriteria(eqMap, neqMap, likMap, matchMode);
	}
	
	protected abstract IOperations<T> getDbManager();
}
