package br.com.tcc.musicsocial.daoImpl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.DenunciaDAO;
import br.com.tcc.musicsocial.entity.Denuncia;
import br.com.tcc.musicsocial.util.SituacaoConta;
import br.com.tcc.musicsocial.util.StatusDenuncia;

@Repository
public class DenunciaDAOImpl extends BaseDAOImpl<Denuncia> implements DenunciaDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Denuncia> getDenuncias() {
		StringBuilder hql = new StringBuilder();
		hql.append("select d from Denuncia d ");
		hql.append("where d.status = :status ");
		hql.append("and d.publicacao.ativa = :publicacaoAtiva ");
		hql.append("and d.denunciado.situacaoConta.codigoSituacaoConta = :codigoSituacaoConta ");
		hql.append("order by d.data desc ");
		
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("status", StatusDenuncia.INICIADA.getValue());
		query.setParameter("publicacaoAtiva", true);
		query.setParameter("codigoSituacaoConta", SituacaoConta.ATIVA.getValue());
		
		return query.getResultList();
	}
}
