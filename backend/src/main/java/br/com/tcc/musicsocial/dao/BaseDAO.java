package br.com.tcc.musicsocial.dao;

import java.util.List;

public interface BaseDAO<T> {
	void save(T t);
	
	T update(T t);
	
	List<T> findAll();
	
	T find(Integer pk);
}
