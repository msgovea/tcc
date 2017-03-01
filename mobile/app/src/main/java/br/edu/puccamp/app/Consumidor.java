package br.edu.puccamp.app;

public class Consumidor {

    private Long id_consumidor;
    private String nome;
    private String email;
    private String senha;
    private String id_tipo_acesso;
    private String key_acesso;

    public Consumidor(Long id_consumidor, String nome, String email, String senha, String id_tipo_acesso) {
        this.id_consumidor = id_consumidor;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.id_tipo_acesso = id_tipo_acesso;
    }

    public Long getId_consumidor() {
        return id_consumidor;
    }

    public void setId_consumidor(Long id_consumidor) {
        this.id_consumidor = id_consumidor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getId_tipo_acesso() {
        return id_tipo_acesso;
    }

    public void setId_tipo_acesso(String id_tipo_acesso) {
        this.id_tipo_acesso = id_tipo_acesso;
    }

    public String getKey_acesso() {
        return key_acesso;
    }

    public void setKey_acesso(String key_acesso) {
        this.key_acesso = key_acesso;
    }
}
