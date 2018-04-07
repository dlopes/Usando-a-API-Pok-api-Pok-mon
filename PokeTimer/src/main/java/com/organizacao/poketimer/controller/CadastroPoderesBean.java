/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.controller;


import com.google.gson.Gson;
import com.organizacao.poketimer.model.Poder;
import com.organizacao.poketimer.model.PokemTrans;
import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.model.Result;
import com.organizacao.poketimer.repository.Poderes;
import com.organizacao.poketimer.repository.Pokemons;
import com.organizacao.poketimer.service.CadastroPokemonService;
import com.organizacao.poketimer.service.NegocioException;
import com.organizacao.poketimer.util.jsf.FacesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import javax.faces.bean.ViewScoped;
import javax.faces.view.ViewScoped;//Bean cdi
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Dilson
 */
@Named
@ViewScoped
public class CadastroPoderesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroPokemonService cadastroPokemonService;
    
    private List<Poder> poderesDisponiveis;
    
    private Pokemon pokemon;
    
    @Inject
    Pokemons pokemons;
    
    @Inject
    private Poderes Poderes;
    private Poder novoPoder;
    private Poder novo;
    private Poder removePoder;
    
    public CadastroPoderesBean() {
        limpar();
    }

    public Poder getNovoPoder() {
        return novoPoder;
    }

    public void setNovoPokemon(Poder novoPoder) {
        this.novoPoder = novoPoder;
    }

    public Poder getRemovePoder() {
        return removePoder;
    }

    public void setRemovePokemon(Poder removePoder) {
        this.removePoder = removePoder;
    }

    public List<Poder> getPoderesDisponiveis() {
        return poderesDisponiveis;
        
    }

    public Poder getNovo() {
        return novo;
    }

    public void setNovo(Poder novo) {
        this.novo = novo;
    }

       
    public void setPoderesDisponiveis(List<Poder> poderesDisponiveis) {
        this.poderesDisponiveis = poderesDisponiveis;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
    
   

    public void inicializar() {
        if (this.pokemon == null) {
           limpar();
        } else {
          this.pokemon = this.pokemons.porId( Long.parseLong(this.pokemon.getNome()));
        }
        poderesDisponiveis = this.todosPoderes();

    }

   private void limpar() {
        pokemon = new Pokemon();

    }

    public void salvar() {
        try {
             
            this.pokemon = cadastroPokemonService.salvar(this.pokemon);
            limpar();

            FacesUtil.addInfoMessage("Habilidades salvas com sucesso!");
        } catch (NegocioException ne) {
            FacesUtil.addErrorMessage(ne.getMessage());
        }
    }   
    
    public String getJsonb() {
        
        Client client = Client.create();
        WebResource webResource = client.resource("https://pokeapi.co/api/v2/pokemon/");
        ClientResponse response = webResource.accept("application/json")
            .header("user-agent", "")
            .get(ClientResponse.class);
        if(response.getStatus() != 200) {
            throw new RuntimeException("Erro no acesso http error codeio :" + response.getStatus());
        }
        String output = response.getEntity(String.class);
        
            return output;
    }
    
    public void adicionarPoderes() {
        //if (!this.timer.getPokemons().contains(novo)) {
        if (this.pokemon.getPoder().size() < 4) {
            if (novo != null) {
                novo.setPokemon(this.pokemon);
                this.pokemon.getPoder().add(novo);
                System.out.println(novo.getNome());
            } else {
                System.out.println(novo == null);
                FacesUtil.addErrorMessage("Selecione um Poder!");
            }
       /* } else {
            FacesUtil.addErrorMessage("Poder já foi adicionado!");
        }*/
        } else {
            FacesUtil.addErrorMessage("Voce já selecionou 4 habilidade!");
        }

    }
     
    public List<Poder> todosPoderes() {
       
        Client client = Client.create();
        WebResource webResource = client.resource("https://pokeapi.co/api/v2/ability/");
        ClientResponse response = webResource.accept("application/json")
            .header("user-agent", "")
            .get(ClientResponse.class);
        if(response.getStatus() != 200) {
            throw new RuntimeException("Erro no acesso http error codeio :" + response.getStatus());
        }
        String output = response.getEntity(String.class);

        PokemTrans pokemTrans = new Gson().fromJson(output, PokemTrans.class);
    
        List<Poder> retorno = new ArrayList<Poder>();
        for( Result p: pokemTrans.getResults()) {
            Poder poder = new Poder();
            poder.setNome(p.getName());
            retorno.add(poder);
        }
        return retorno;
    }

    public void removerPoder() { // remove da lista 
        this.pokemon.getPoder().remove(removePoder);
    }

    public boolean isEditando() {
        return this.pokemon.getId() != null;
    }
    
}
