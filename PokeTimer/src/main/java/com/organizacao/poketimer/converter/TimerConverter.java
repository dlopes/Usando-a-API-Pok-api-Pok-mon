/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.converter;


import com.organizacao.poketimer.model.Timer;
import com.organizacao.poketimer.repository.Times;
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
@FacesConverter(forClass = Timer.class)
public class TimerConverter implements Converter {

    @Inject //Inject não funciona com conversores
    private Times times;

    /*public GrupoConverter() {
        grupos = CDIServiceLocator.getBean(Grupos.class);
    }*/
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Timer retorno = null;

        if (StringUtils.isNotEmpty(value)) {
            String nome = new String(value);
            retorno = times.porNome(nome);
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Timer timer = (Timer) value;
            //return grupo.getNome().toString();
            return timer.getNome() != null ? timer.getNome().toString() : null;
        }
        return "";
    }
}
