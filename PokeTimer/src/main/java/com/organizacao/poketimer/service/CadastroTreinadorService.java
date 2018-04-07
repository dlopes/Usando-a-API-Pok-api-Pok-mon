package com.organizacao.poketimer.service;

import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.Treinadores;
import java.io.Serializable;

import javax.inject.Inject;
import com.organizacao.poketimer.util.jpa.Transactional;

public class CadastroTreinadorService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Treinadores treinadores;

    @Transactional
    public Treinador salvar(Treinador treinador) throws NegocioException {
        
        Treinador usuarioExistente = treinadores.porEmail(treinador.getEmail());
        treinador.setCpf(treinador.getCpf().replaceAll("[ ./-]", ""));
        if (usuarioExistente != null && !usuarioExistente.equals(treinador)) {
            throw new NegocioException("Já existe um treinador com o email informado");
        } else {
            return treinadores.guardar(treinador);
        }
    }
    
   
}