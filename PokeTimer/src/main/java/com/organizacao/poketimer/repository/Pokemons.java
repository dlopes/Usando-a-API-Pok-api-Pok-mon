/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.repository;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.organizacao.poketimer.model.Poder;
import com.organizacao.poketimer.model.PokemTrans;
import com.organizacao.poketimer.model.Pokemon;
import com.organizacao.poketimer.model.Timer;
import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.filter.PokemonFilter;
import com.organizacao.poketimer.service.NegocioException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 *
 * @author Anjo_Grabiel
 */
public class Pokemons implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntityManager manager;

    public Pokemon porId(Long id) {
        return manager.find(Pokemon.class, id);
    }

    public Pokemon porNome(String nome) {
        try {
            return manager.createQuery("from Pokemon where upper(nome) = :nome", Pokemon.class)
                    .setParameter("nome", nome.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Pokemon pokemonPorNome(String nome) {
        try {
            return manager.createQuery("from Pokemon where upper(nome) = :nome", Pokemon.class)
                    .setParameter("nome", nome.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Pokemon> todos() {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Poder.class);

        return criteria.addOrder(Order.asc("nome")).list();

    }
    
    @SuppressWarnings("unchecked")
    public List<Pokemon> filtrados(PokemonFilter filtro) {
        Session session = manager.unwrap(Session.class);
        //Criteria criteria = session.createCriteria(Pokemon.class);

        /*if (StringUtils.isNotBlank(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));

        }*/

        DetachedCriteria userSubquery = DetachedCriteria.forClass(Treinador.class, "a")
        // Filter the Subquery
        .add(Restrictions.eq("a.id", filtro.getTreinadorId()))
        // SELECT The Id  
        .setProjection( Projections.property("a.id") );
        Criteria criteria = session.createCriteria(Pokemon.class, "p");
        System.out.println("filtro.getTreinadorId()"+filtro.getTreinadorId());
        criteria.add(Restrictions.ilike("p.nome", filtro.getNome(), MatchMode.ANYWHERE));
        criteria.createAlias("timer","t");
        criteria.add(Subqueries.propertyIn("t.treinador", userSubquery));
        return criteria.addOrder(Order.asc("p.nome")).list();
       
    }
    
     public Pokemon guardar(Pokemon pokemon) {
        return manager.merge(pokemon);
    }
     
    @Transactional
    public void remover(Pokemon pokemon) throws NegocioException {
        try {
            pokemon = porId(pokemon.getId());
            manager.remove(pokemon);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Pokemon não pode ser excluído.");
        }
    }
    
    
}
