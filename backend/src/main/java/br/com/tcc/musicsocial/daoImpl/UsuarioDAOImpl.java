package br.com.tcc.musicsocial.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;

@Repository
public class UsuarioDAOImpl extends BaseDAOImpl<UsuarioDetalhe> implements UsuarioDAO {

	@Override
	public UsuarioDetalhe consultarPorEmail(String email) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select u from UsuarioDetalhe u ");	
			sql.append("where u.email = :email ");
			
			Query query = getEntityManager().createQuery(sql.toString());
			query.setParameter("email", email);
			
			return (UsuarioDetalhe) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioDetalhe> consultarPorNome(String nome) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select u from UsuarioDetalhe u ");	
			sql.append("where upper(u.nome) like :nome ");
			
			Query query = getEntityManager().createQuery(sql.toString());
			query.setParameter("nome", "%" + nome.toUpperCase() + "%");
			
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<UsuarioDetalhe>();
		}
	}

}
