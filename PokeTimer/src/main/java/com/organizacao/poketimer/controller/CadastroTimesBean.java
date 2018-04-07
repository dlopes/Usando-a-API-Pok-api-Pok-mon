/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.controller;

import com.google.gson.Gson;
import com.organizacao.poketimer.model.PokemTrans;
import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.model.Result;
import com.organizacao.poketimer.model.Timer;
import com.organizacao.poketimer.repository.Pokemons;
import com.organizacao.poketimer.security.Seguranca;
import com.organizacao.poketimer.service.CadastroTimerService;
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
public class CadastroTimesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroTimerService cadastroTimerService;
    
    private List<Pokemon> pokemonsDisponiveis;
    private List<Pokemon> addPokes;
    
    private Timer timer;
    
    @Inject
    private Pokemons pokemons;
    private Pokemon novoPokemon;
    private Pokemon novo;
    private Pokemon removePokemon;
    
    private String jsona;
    
    public CadastroTimesBean() {
        limpar();
    }

    public Pokemon getNovoPokemon() {
        return novoPokemon;
    }

    public void setNovoPokemon(Pokemon novoPokemon) {
        this.novoPokemon = novoPokemon;
    }

    public Pokemon getRemovePokemon() {
        return removePokemon;
    }

    public void setRemovePokemon(Pokemon removePokemon) {
        this.removePokemon = removePokemon;
    }

    public List<Pokemon> getPokemonsDisponiveis() {
        return pokemonsDisponiveis;
        
    }

    public Pokemon getNovo() {
        return novo;
    }

    public void setNovo(Pokemon novo) {
        this.novo = novo;
    }

    public String getJsona() {
        return jsona;
    }

    public void setJsona(String jsona) {
        this.jsona = jsona;
    }
    
    
    
    public void setPokemonsDisponiveis(List<Pokemon> pokemonsDisponiveis) {
        this.pokemonsDisponiveis = pokemonsDisponiveis;
    }
    
    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public List<Pokemon> getAddPokes() {
        return addPokes;
    }

    public void setAddPokes(List<Pokemon> addPokes) {
        this.addPokes = addPokes;
    }
    
    public void inicializar() {
        if (this.timer == null) {
            limpar();
        }
         this.addPokes = this.timer.getPokemons();
        pokemonsDisponiveis = this.todosPokemons();

    }

    private void limpar() {
        timer = new Timer();

    }

    public void salvar() {
        try {
             
            Seguranca seg = new Seguranca();
            timer.setTreinador(seg.getUsuarioLogado().getTreinador());
            System.out.println("Pokemon addPokes "+ this.addPokes.size());
            this.timer = cadastroTimerService.salvar(this.timer);
            
            for( Pokemon p: this.addPokes) {
                p.setTimer(this.timer);
                this.timer.getPokemons().add(p);
            }

            this.timer = cadastroTimerService.salvar(this.timer);
            
            this.addPokes.clear();
            limpar();
            FacesUtil.addInfoMessage("Timer salvo com sucesso!");
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
    
    public void adicionarPokemon() {
        if (this.addPokes.size() < 6) {
            //if (!this.timer.getPokemons().contains(novo)) {
                if (novo != null) {
                    this.addPokes.add(novo);
                    System.out.println(novo.getNome());
                } else {
                    FacesUtil.addErrorMessage("Selecione um Pokemon!");
                }
           /* } else {
                FacesUtil.addErrorMessage("Pokemon já foi adicionado!");
            }*/
        } else {
            FacesUtil.addErrorMessage("Voce já selecionou 6 Pokemons!");
        }

    }
     
    public List<Pokemon> todosPokemons() {
       
        Client client = Client.create();
        WebResource webResource = client.resource("https://pokeapi.co/api/v2/pokemon/");
        ClientResponse response = webResource.accept("application/json")
            .header("user-agent", "")
            .get(ClientResponse.class);
        if(response.getStatus() != 200) {
            throw new RuntimeException("Erro no acesso http error codeio :" + response.getStatus());
        }
        String output = response.getEntity(String.class);
        PokemTrans pokemTrans = new Gson().fromJson(output, PokemTrans.class);
        List<Pokemon> retorno = new ArrayList<Pokemon>();
        for( Result p: pokemTrans.getResults()) {
            Pokemon poke = new Pokemon();
            poke.setNome(p.getName());
            retorno.add(poke);
        }
        return retorno;
    }

    public void removerPokemon() { // remove da lista
        //this.timer.getPokemons().remove(removePokemon);
        this.addPokes.remove(removePokemon);
    }

    public boolean isEditando() {
        return this.timer.getId() != null;
    }
    
}
