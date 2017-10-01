package com.mgovea.urmusic.entity;

import java.util.ArrayList;
import java.util.List;

public class UsuarioByte {

    public UsuarioByte(Usuario u) {
        codigoUsuario = u.getCodigoUsuario();
        email = u.getEmail();
        senha = u.getSenha();
        nivelUsuario = u.getNivelUsuario();
        situacaoConta = u.getSituacaoConta();
        gostosMusicais = u.getGostosMusicais();
        //TODO IMAGEM
//        try {
//            imagemPerfil = Base64.decode(u.getImagemPerfil(), Base64.DEFAULT);
//        } catch (Exception e) {
//
//        }
        nome = u.getNome();
        apelido = u.getApelido();
        dataNascimento = u.getDataNascimento();
        cidade = u.getCidade();
        estado = u.getEstado();
        pais = u.getPais();
        qtdSeguidos = u.getQtdSeguidos();
        seguidores = u.getSeguidores();
    }

    private ArrayList<Usuario> seguidores;

    public ArrayList<Usuario> getSeguidores() {
        return this.seguidores;
    }

    public void setSeguidores(ArrayList<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public Long getQtdSeguidos() {
        return this.qtdSeguidos;
    }

    public void setQtdSeguidos(Long qtdSeguidos) {
        this.qtdSeguidos = qtdSeguidos;
    }

    private Long qtdSeguidos;

    public UsuarioByte() {
        nivelUsuario = new NivelUsuario();
        situacaoConta = new SituacaoConta();
        situacaoConta.setCodigoSituacaoConta(0);
        nivelUsuario.setCodigoNivel(Integer.valueOf(1));
    }

    public UsuarioByte(String nome, Long codigoUsuario) {
        this.nome = nome;
        this.codigoUsuario = codigoUsuario;
    }

    private Long codigoUsuario;

    private String email;

    private String senha;

    private NivelUsuario nivelUsuario;

    private SituacaoConta situacaoConta;

    private List<GostosMusicai> gostosMusicais;
//TODO IMAGEM
//    private byte[] imagemPerfil = null;
//
//    public void setImagemPerfil (byte[] imagem) {
//        this.imagemPerfil = imagem;
//    }
//
//
//    public byte[] getImagemPerfil () {
//        return this.imagemPerfil;
//    }

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
