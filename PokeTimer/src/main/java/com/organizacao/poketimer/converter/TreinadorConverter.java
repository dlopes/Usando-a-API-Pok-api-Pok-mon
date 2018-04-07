/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.converter;


import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.Treinadores;
import  com.organizacao.poketimer.util.cdi.CDIServiceLocator;
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
@FacesConverter(forClass = Treinador.class)
public class TreinadorConverter implements Converter {

    @Inject
    private Treinadores treinadores;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Treinador retorno = null;

        if (StringUtils.isNotEmpty(value)) {
            retorno = this.treinadores.porId(new Long(value));
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Treinador treinador = (Treinador) value;
            return treinador.getId() == null ? null : treinador.getId().toString();
        }
        return "";
    }

}
