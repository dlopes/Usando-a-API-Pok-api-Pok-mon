package com.organizacao.poketimer.repository.filter;

import java.io.Serializable;

public class PokemonFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    
    private Long treinadorId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTreinadorId() {
        return treinadorId;
    }

    public void setTreinadorId(Long treinadorId) {
        this.treinadorId = treinadorId;
    }
    
}