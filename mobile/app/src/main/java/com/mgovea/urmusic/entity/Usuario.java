package com.mgovea.urmusic.entity;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    public Usuario() {
        nivelUsuario = new NivelUsuario();
        situacaoConta = new SituacaoConta();
        situacaoConta.setCodigoSituacaoConta(0);
        nivelUsuario.setCodigoNivel(Integer.valueOf(1));
    }

    public Usuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Usuario(String nome, Long codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }

    public Long getQtdSeguidos() {
        return this.qtdSeguidos;
    }

    public void setQtdSeguidos(Long qtdSeguidos) {
        this.qtdSeguidos = qtdSeguidos;
    }

    private ArrayList<Long> codigoSeguidores;

    public ArrayList<Long> getSeguidores() {
        return this.codigoSeguidores;
    }

    public void setSeguidores(ArrayList<Long> seguidores) {
        this.codigoSeguidores = seguidores;
    }

    private Long qtdSeguidos;

    private Long codigoUsuario;

    private String email;

    private String senha;

    private NivelUsuario nivelUsuario;

    private SituacaoConta situacaoConta;

    private List<GostosMusicai> gostosMusicais;

    public List<GostosMusicai> getGostosMusicais() {
        return gostosMusicais;
    }

    public void setGostosMusicais(List<GostosMusicai> gostosMusicais) {
        this.gostosMusicais = gostosMusicais;
    }

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public NivelUsuario getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(NivelUsuario nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public SituacaoConta getSituacaoConta() {
        return situacaoConta;
    }

    public void setSituacaoConta(SituacaoConta situacaoConta) {
        this.situacaoConta = situacaoConta;
    }

    private String nome;

    private String apelido;

    private String dataNascimento;

    private String cidade;

    private String estado;

    private String pais;

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getApelido() {
        return apelido;
    }


    public void setApelido(String apelido) {
        this.apelido = apelido;
    }


    public String getDataNascimento() {
        return dataNascimento;
    }


    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public String getCidade() {
        return cidade;
    }


    public void setCidade(String cidade) {
        this.cidade = cidade;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getPais() {
        return pais;
    }


    public void setPais(String pais) {
        this.pais = pais;
    }


}
