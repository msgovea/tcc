package br.com.tcc.musicsocial.daoImpl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Publicacao;

@Repository
public class PublicacaoDAOImpl extends BaseDAOImpl<Publicacao> implements PublicacaoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Publicacao> getPublicacoes(Integer idUsuario) {
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Publicacao p ");
		hql.append("where p.usuario.codigoUsuario = :codigo ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("codigo", idUsuario);
		return query.getResultList();
	}

}
