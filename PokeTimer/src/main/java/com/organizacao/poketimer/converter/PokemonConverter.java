/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.converter;

import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.repository.Pokemons;
//import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Anjo_Grabiel
 */
@FacesConverter(forClass = Pokemon.class)
public class PokemonConverter implements Converter {

    @Inject //Inject não funciona com conversores
    private Pokemons pokemons;

  
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       /* Pokemon retorno = null;

        if (StringUtils.isNotEmpty(value)) {
            String nome = new String(value);
            retorno = pokemons.pokemonPorNome(nome);
        }
        return retorno;*/
       
       Pokemon retorno = new Pokemon();
       retorno.setNome(value);
       
       return retorno;
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Pokemon pokemon = (Pokemon) value;
            return pokemon.getNome() != null ? pokemon.getNome().toString() : null;
        }
        return "";
    }
}
