package br.com.tcc.musicsocial.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.GostoMusicalDAO;
import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.UsuarioGostoMusical;

@Repository
public class GostoMusicalDAOImpl extends BaseDAOImpl<UsuarioGostoMusical> implements GostoMusicalDAO {
	
	@SuppressWarnings("unchecked")
	public List<GostoMusical> findAllGostos() {
		return getEntityManager().createQuery("from GostoMusical").getResultList();
	}

	@Override
	public GostoMusical findGostoById(Integer idGosto) {
		return (GostoMusical) getEntityManager().createQuery("from GostoMusical where codigo = :codigo").setParameter("codigo", idGosto).getSingleResult();
	}
}
