package br.com.tcc.musicsocial.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.BaseDAO;

@Repository
public class BaseDAOImpl<T> implements BaseDAO<T> {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(T t) {
		em.persist(t);
	}

	@Override
	public T update(T t) {
		return em.merge(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return em.createQuery(" from " + getType().getName()).getResultList();
	}

	protected EntityManager getEntityManager() {
		return em;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<?> getType() {
		return (Class<T>) ((ParameterizedType) 
			      getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(Object pk) {
		return (T) em.find(getType(), pk);
	}
	
	@Override
	public void remove(T t) {
		em.remove(em.contains(t) ? t : em.merge(t));
	}
}
