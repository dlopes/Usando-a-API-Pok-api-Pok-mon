package com.organizacao.poketimer.security;

import com.organizacao.poketimer.model.Treinador;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

        private Treinador treinador;
	
	
        public UsuarioSistema(Treinador treinador, Collection<? extends GrantedAuthority> authorities) {
		super(treinador.getEmail(), treinador.getSenha(), authorities);
		this.treinador = treinador;
	}

	public Treinador getTreinador() {
		return treinador;
	}

}
