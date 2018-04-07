/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.service;


import com.organizacao.poketimer.util.jpa.Transactional;
import com.organizacao.poketimer.model.Timer;
import com.organizacao.poketimer.repository.Times;
import com.organizacao.poketimer.security.Seguranca;
import com.organizacao.poketimer.security.UsuarioSistema;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Anjo_Grabiel
 */
public class CadastroTimerService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Times times;

    @Transactional
    public Timer salvar(Timer timer) throws NegocioException {
        Timer novoTimer = times.porNome(timer.getNome());
        
        if (novoTimer != null && !novoTimer.equals(timer)) {
            throw new NegocioException("Já existe um Timer com o E-mail Cadastrado.");
        }
     
        return times.guardar(timer);
    }
    
}
