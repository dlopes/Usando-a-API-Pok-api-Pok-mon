/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.converter;

import com.organizacao.poketimer.model.Grupo;
import com.organizacao.poketimer.model.Poder;
import com.organizacao.poketimer.repository.Grupos;
import com.organizacao.poketimer.repository.Poderes;
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
@FacesConverter(forClass = Poder.class)
public class PoderConverter implements Converter {

    @Inject //Inject não funciona com conversores
    private Poderes poderes;

  
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       /* Poder retorno = null;

        if (StringUtils.isNotEmpty(value)) {
            String nome = new String(value);
            retorno = poderes.poderPorNome(nome);
        }*/
        
       Poder retorno = new Poder();
       retorno.setNome(value);

       return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Poder poder = (Poder) value;
       
            return poder.getNome() != null ? poder.getNome().toString() : null;
        }
        return "";
    }
}
