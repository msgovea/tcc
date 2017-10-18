package br.com.tcc.musicsocial.daoImpl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.util.SituacaoConta;

@Repository
public class PublicacaoDAOImpl extends BaseDAOImpl<Publicacao> implements PublicacaoDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Publicacao> getPublicacoes(Integer idUsuario) {
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Publicacao p ");
		hql.append("where p.usuario.codigoUsuario = :codigo ");
		hql.append("and p.ativa = :ativa ");
		hql.append("and p.usuario.situacaoConta.codigoSituacaoConta <> :codigoSituacaoConta ");
		hql.append("order by p.codigo desc ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("codigo", idUsuario);
		query.setParameter("ativa", true);
		query.setParameter("codigoSituacaoConta", SituacaoConta.BANIDA.getValue());
		return query.getResultList();
	}
	
	@Override
	public Publicacao getPublicacao(Long idPublicacao) {
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Publicacao p ");
		hql.append("where p.codigo = :codigo ");
		hql.append("and p.ativa = :ativa ");
		hql.append("and p.usuario.situacaoConta.codigoSituacaoConta <> :codigoSituacaoConta ");
		hql.append("order by p.codigo desc ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("codigo", idPublicacao);
		query.setParameter("ativa", true);
		query.setParameter("codigoSituacaoConta", SituacaoConta.BANIDA.getValue());
		return (Publicacao) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Publicacao> getPublicacoesDeAmigos(Integer idUsuario) {
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Publicacao p ");
		hql.append("where (p.usuario.codigoUsuario in (select a.seguido.codigoUsuario from Amigo a where a.segue.codigoUsuario = :idUsuario) ");
		hql.append("or p.usuario.codigoUsuario = :idUsuario) ");
		hql.append("and p.ativa = :ativa ");
		hql.append("and p.usuario.situacaoConta.codigoSituacaoConta <> :codigoSituacaoConta ");
		hql.append("order by p.codigo desc ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("ativa", true);
		query.setParameter("codigoSituacaoConta", SituacaoConta.BANIDA.getValue());
		return query.getResultList();
	}
	
	@Override
	public Integer consultarQtdComentarios(Publicacao publicacao) {
		String hql = "select count(1) from Comentario c where c.codigoPublicacao = :codigo";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("codigo", publicacao.getCodigo());
		return ((Long) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Publicacao> getPublicacoesEmAlta() {
		StringBuilder hql = new StringBuilder();
		hql.append("select p from Publicacao p ");
		hql.append("where p.impulsionada = :impulsionada ");
		hql.append("and p.ativa = :ativa ");
		hql.append("order by p.codigo desc ");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("impulsionada", true);
		query.setParameter("ativa", true);
		return query.getResultList();
	}
}
