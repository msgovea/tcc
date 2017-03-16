package br.com.tcc.musicsocial.daoImpl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.util.SituacaoConta;

@Repository
public class UsuarioDAOImpl extends BaseDAOImpl<UsuarioDetalhe> implements UsuarioDAO {

	@Override
	public UsuarioDetalhe consultarPorEmail(String email) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select u from UsuarioDetalhe u ");	
			sql.append("where u.email = :email ");
			//sql.append("and u.situacaoConta.codigoSituacaoConta = :situacao ");
			
			Query query = getEntityManager().createQuery(sql.toString());
			query.setParameter("email", email);
			//query.setParameter("situacao", SituacaoConta.ATIVA.getValue());
			
			return (UsuarioDetalhe) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
