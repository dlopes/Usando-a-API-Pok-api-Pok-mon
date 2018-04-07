package com.organizacao.poketimer.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.organizacao.poketimer.model.Grupo;
import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.Treinadores;
import com.organizacao.poketimer.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

        @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Treinadores treinadores = CDIServiceLocator.getBean(Treinadores.class);
		Treinador treinador = treinadores.porEmail(email);
		
		UsuarioSistema user = null;
		
		if (treinador != null) {
			user = new UsuarioSistema(treinador, getGrupos(treinador));
		} else {
			throw new UsernameNotFoundException("Treinador não encontrado.");
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Treinador treinador) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Grupo grupo : treinador.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}

}
