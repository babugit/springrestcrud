package example.spring.restcrud.database.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import example.spring.restcrud.business.config.Utils;

@Transactional
public abstract class AbstractHibernateManager<T extends Serializable> implements IOperations<T> {

	private Class<T> clazz;

	@Autowired
	private LocalSessionFactoryBean sessionFactory;

	protected final void setClazz(final Class<T> clazzToSet) {
		clazz = Preconditions.checkNotNull(clazzToSet);
	}

	@Override
	public final T findOneById(final int id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public final List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	@Override
	public final int create(final T entity) {
		Preconditions.checkNotNull(entity);
		return ((Integer) getCurrentSession().save(entity)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T update(final T entity) {
		Preconditions.checkNotNull(entity);
		return (T) getCurrentSession().merge(entity);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public final List<T> findByCriteria(final Map<String, Object> eqMap, final Map<String, Object> neqMap,
			final Map<String, Object> likMap, final MatchMode matchMode) throws Throwable {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria = addPropertyFromMap(criteria, eqMap, null);
		criteria = addPropertyFromMap(criteria, neqMap, null);
		criteria = addPropertyFromMap(criteria, likMap, (matchMode == null ? MatchMode.ANYWHERE : matchMode));
		
		return criteria.list();
	}

	@Override
	public final void delete(final T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().delete(entity);
	}

	@Override
	public final void deleteById(final int entityId) {
		final T entity = findOneById(entityId);
		Preconditions.checkState(entity != null);
		delete(entity);

	}

	private Criteria addPropertyFromMap(Criteria criteria, Map<String, Object> map, MatchMode matchMode) {
		if (criteria != null && map != null && map.size() > 0) {
			map.entrySet().stream().filter(entry -> !Utils.isEmpty(entry.getKey()) && entry.getValue() != null)
					.forEach(entry -> {

						if (matchMode != null) {
							criteria.add(Restrictions.ilike(entry.getKey(), "%" + entry.getValue().toString() + "%", matchMode));
						} else {
							criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
						}
					});
		}

		return criteria;
	}
	
	protected final Session getCurrentSession() {
		return sessionFactory.getObject().getCurrentSession();
	}
}
