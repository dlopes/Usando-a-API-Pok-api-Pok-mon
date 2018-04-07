/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.controller;


import com.organizacao.poketimer.model.Grupo;
import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.Grupos;
import com.organizacao.poketimer.service.CadastroTreinadorService;
import com.organizacao.poketimer.service.NegocioException;
import com.organizacao.poketimer.util.jsf.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
//import javax.faces.bean.ViewScoped;
import javax.faces.view.ViewScoped;//Bean cdi
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

/**
 *
 * @author Dilson
 */
@Named
@ViewScoped
public class CadastroTreinadorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroTreinadorService cadastroTreinadorService;
    
    @Inject
    private Grupos grupos;
    
    @Inject
    private LoginBean loginBean;
    
    private Treinador treinador;

    public CadastroTreinadorBean() {
        limpar();
    }
    
    
    public void inicializar() {
        if (this.treinador == null) {
            limpar();
        }

    }

    private void limpar() {
        treinador = new Treinador();

    }

    public void salvar() {
        try {
            Grupo gTreinador = new Grupo();
            gTreinador = grupos.grupoPorId(Long.valueOf(2));
            this.treinador.getGrupos().add(gTreinador);
            this.treinador = cadastroTreinadorService.salvar(this.treinador);
            limpar();
            FacesUtil.addInfoMessage("Treinador salvo com sucesso!");
            
        } catch (NegocioException ne) {
            FacesUtil.addErrorMessage(ne.getMessage());
        }
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }
    
    public boolean isEditando() {
		return this.treinador.getId() != null;
	}
}
