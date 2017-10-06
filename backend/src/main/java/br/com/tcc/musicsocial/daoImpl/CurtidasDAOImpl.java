package br.com.tcc.musicsocial.daoImpl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.CurtidasDAO;
import br.com.tcc.musicsocial.entity.Curtida;

@Repository
public class CurtidasDAOImpl extends BaseDAOImpl<Curtida> implements CurtidasDAO {

	@Override
	public Curtida findByPk(Integer idUsuario, Long idPublicacao) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Curtida c ");
			hql.append("where c.usuario.codigoUsuario = :idUsuario ");
			hql.append("and c.codigoPublicacao = :idPublicacao");

			Query query = getEntityManager().createQuery(hql.toString());
			query.setParameter("idUsuario", idUsuario);
			query.setParameter("idPublicacao", idPublicacao);
			return (Curtida) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
