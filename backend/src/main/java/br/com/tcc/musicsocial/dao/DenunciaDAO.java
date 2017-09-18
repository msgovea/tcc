package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Denuncia;

public interface DenunciaDAO extends BaseDAO<Denuncia> {

	List<Denuncia> getDenuncias();

}
