/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.service;


import com.organizacao.poketimer.model.Poder;
import com.organizacao.poketimer.util.jpa.Transactional;
import com.organizacao.poketimer.repository.Poderes;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Anjo_Grabiel
 */
public class CadastroPoderService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Poderes poderes;

    @Transactional
    public Poder salvar(Poder poder) throws NegocioException {
        

        return poderes.guardar(poder);
 
    }
    
}
