/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.converter;

import com.organizacao.poketimer.model.Grupo;
import com.organizacao.poketimer.repository.Grupos;
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
@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {

    @Inject //Inject não funciona com conversores
    private Grupos grupos;

    /*public GrupoConverter() {
        grupos = CDIServiceLocator.getBean(Grupos.class);
    }*/
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Grupo retorno = null;

        if (StringUtils.isNotEmpty(value)) {
            String nome = new String(value);
            retorno = grupos.grupoPorNome(nome);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Grupo grupo = (Grupo) value;
            //return grupo.getNome().toString();
            return grupo.getNome() != null ? grupo.getNome().toString() : null;
        }
        return "";
    }
}
