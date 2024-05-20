package com.example.goumertlog_mobile_new.models;

public class Usuario {
    private String cpf;
    private String dataNasc;
    private String email;
    private String nome;
    private String senha;
    private String imagem;

    public Usuario() {
    }

    public Usuario(String cpf, String dataNasc, String email, String nome, String senha, String imagem) {
        this.cpf = cpf;
        this.dataNasc = dataNasc;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.imagem = imagem;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {return nome; }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
