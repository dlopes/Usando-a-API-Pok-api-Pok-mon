package com.organizacao.poketimer.controller;

import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.repository.Pokemons;
import com.organizacao.poketimer.repository.filter.PokemonFilter;
import com.organizacao.poketimer.security.Seguranca;
import com.organizacao.poketimer.service.NegocioException;
import com.organizacao.poketimer.util.jsf.FacesUtil;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ViewScoped
public class PesquisaPokemonsBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private  Pokemons pokemons;
    
    private PokemonFilter filtro;
    private List<Pokemon> pokemonsFiltrados;
    private Pokemon pokemonSelecionado;
    
     public PesquisaPokemonsBean() {
        filtro = new PokemonFilter();
    }

    public void pesquisar() {
         Seguranca seg = new Seguranca();
        filtro.setTreinadorId(seg.getUsuarioLogado().getTreinador().getId());
        pokemonsFiltrados = pokemons.filtrados(filtro);
  
    }
    
      public void excluir() {
        try {
            pokemons.remover(pokemonSelecionado);
            pokemonsFiltrados.remove(pokemonSelecionado);

            FacesUtil.addInfoMessage("Produto " + pokemonSelecionado.getNome()
                    + " excluído com sucesso.");
        } catch (NegocioException e) {
            FacesUtil.addInfoMessage(e.getMessage());
        }
    }

    public PokemonFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(PokemonFilter filtro) {
        this.filtro = filtro;
    }

    public List<Pokemon> getPokemonsFiltrados() {
        return pokemonsFiltrados;
    }

    public void setUsuariosFiltrados(List<Pokemon> usuariosFiltrados) {
        this.pokemonsFiltrados = pokemonsFiltrados;
    }

    public Pokemon getPokemonSelecionado() {
        return pokemonSelecionado;
    }

    public void setUsuarioSelecionado(Pokemon pokemonSelecionado) {
        this.pokemonSelecionado = pokemonSelecionado;
    }

   
}