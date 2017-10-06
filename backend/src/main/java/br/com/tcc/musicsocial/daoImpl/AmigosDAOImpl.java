package br.com.tcc.musicsocial.daoImpl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.AmigosDAO;
import br.com.tcc.musicsocial.entity.Amigo;

@Repository
public class AmigosDAOImpl extends BaseDAOImpl<Amigo> implements AmigosDAO {
	
	@Override
	public Amigo findByPk(Amigo amigo) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select a from Amigo a ");
			hql.append("where a.segue.codigoUsuario = :idSegue ");
			hql.append("and a.seguido.codigoUsuario = :idSeguido");

			Query query = getEntityManager().createQuery(hql.toString());
			query.setParameter("idSegue", amigo.getSegue().getCodigoUsuario());
			query.setParameter("idSeguido", amigo.getSeguido().getCodigoUsuario());
			return (Amigo) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
