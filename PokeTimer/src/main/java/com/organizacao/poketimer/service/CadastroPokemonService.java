/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.service;

import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.util.jpa.Transactional;
import com.organizacao.poketimer.repository.Pokemons;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Anjo_Grabiel
 */
public class CadastroPokemonService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Pokemons pokemons;

    @Transactional
    public Pokemon salvar(Pokemon pokemon) throws NegocioException {
        

        return pokemons.guardar(pokemon);
 
    }
    
}
