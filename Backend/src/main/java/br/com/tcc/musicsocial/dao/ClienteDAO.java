/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Cliente;

/**
 *
 * @author wagnerjl
 */
public interface ClienteDAO {
    
    void save(Cliente cliente);
    
    Cliente update(Cliente cliente);
    
    Cliente find(Integer id);
    
    List<Cliente> findAll();
}
