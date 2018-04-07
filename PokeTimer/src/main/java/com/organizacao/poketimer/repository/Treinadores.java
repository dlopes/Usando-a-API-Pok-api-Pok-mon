/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.repository;

import com.organizacao.poketimer.model.Treinador;
import com.organizacao.poketimer.repository.filter.TreinadorFilter;
import com.organizacao.poketimer.service.NegocioException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Dilson
 */
public class Treinadores implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private EntityManager manager;

    public Treinador porId(Long id) {
        try {
            return manager.createQuery("from Treinador where id = :id", Treinador.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Treinador guardar(Treinador treinador) {
        return manager.merge(treinador);
    }

    public Treinador porNomeOuEmail(String nome, String email) {
        try {
            return manager.createQuery("from Treinador where upper(nome) = :nome and email = :email", Treinador.class)
                    .setParameter("nome", nome.toUpperCase())
                    .setParameter("email", email.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Treinador> porNome(String nome) {
        return this.manager.createQuery("from Treinador "
                + "where upper(nome) like :nome", Treinador.class)
                .setParameter("nome", nome.toUpperCase() + "%")
                .getResultList();
    }

    public Treinador porEmail(String email) {
        try {
            return manager.createQuery("from Treinador where upper(email) = :email", Treinador.class)
                    .setParameter("email", email.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void remover(Treinador treinador) throws NegocioException {
        try {
            treinador = porId(treinador.getId());
            manager.remove(treinador);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Treinador não pode ser excluído.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<Treinador> filtrados(TreinadorFilter filtro) {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Treinador.class);

        if (StringUtils.isNotBlank(filtro.getCpf())) {
            criteria.add(Restrictions.eq("cpf", filtro.getCpf()));
        }

        if (StringUtils.isNotBlank(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }

        return criteria.addOrder(Order.asc("nome")).list();
    }
    
}
