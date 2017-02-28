/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.musicsocial;

import br.com.tcc.musicsocial.dao.ClienteDAO;
import br.com.tcc.musicsocial.entity.Cliente;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wagnerjl
 */
@RestController
public class ClienteController {
    
    @Autowired
    private ClienteDAO clienteDAO;
    
    @RequestMapping("/cliente/{id}+{nome}")
    @Transactional
    public ResponseEntity<Cliente> inserirCliente(@PathVariable("id") Integer id, @PathVariable("nome") String nome) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome);
        clienteDAO.save(cliente);
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    } 
    
    @RequestMapping("/teste")
    public ResponseEntity<Cliente> teste() {
        return new ResponseEntity<Cliente>(new Cliente(), HttpStatus.OK);
    }
    
    @RequestMapping("/cliente/consulta/{id}")
    public ResponseEntity<Cliente> consultarCliente(@PathVariable("id") Integer id) {
        return new ResponseEntity<Cliente>(clienteDAO.find(id), HttpStatus.FOUND);
    }
}
