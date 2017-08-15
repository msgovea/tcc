package br.com.tcc.musicsocial.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.ComentarioDAO;
import br.com.tcc.musicsocial.entity.Comentario;

@Repository
public class ComentarioDAOImpl extends BaseDAOImpl<Comentario> implements ComentarioDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Comentario> listarComentarios(Long codigoPublicacao) {
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select c from Comentario c ");
			hql.append("where c.codigoPublicacao = :codigoPublicacao ");
			Query query = getEntityManager().createQuery(hql.toString());
			query.setParameter("codigoPublicacao", codigoPublicacao);
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Comentario>();
		}
	}
}
