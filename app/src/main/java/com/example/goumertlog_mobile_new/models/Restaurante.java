package com.example.goumertlog_mobile_new.models;

import java.util.ArrayList;

public class Restaurante {

    private String id,nomeRest ,descricao ,imagem ;


    public static ArrayList<Restaurante> restaurantes;

    public static ArrayList<Restaurante> getRestaurantes(){
        restaurantes = new ArrayList<>();
        return restaurantes;
    }

    public Restaurante(){
    }

    public Restaurante(String id , String nomeRest, String descricao, String imagem){
        this.id = id;
        this.nomeRest = nomeRest;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeRest() {
        return nomeRest;
    }

    public void setNomeRest(String nomeRest) {
        this.nomeRest = nomeRest;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
