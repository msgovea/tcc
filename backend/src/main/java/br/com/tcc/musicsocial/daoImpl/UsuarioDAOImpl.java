package br.com.tcc.musicsocial.daoImpl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.Usuario;

@Repository
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO {

	@Override
	public Usuario consultarPorEmail(String email) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select u from Usuario u ");	
			sql.append("where u.email = :email ");
			sql.append("and upper(u.situacaoConta.tipo) = upper('ativa') ");
			
			Query query = getEntityManager().createQuery(sql.toString());
			query.setParameter("email", email);
			
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
