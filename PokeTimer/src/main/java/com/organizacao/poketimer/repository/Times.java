/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.repository;

import com.organizacao.poketimer.model.Timer;
import com.organizacao.poketimer.service.NegocioException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Anjo_Grabiel
 */
public class Times implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntityManager manager;

    public Timer porId(Long id) {
        return manager.find(Timer.class, id);
    }

    public Timer porNome(String nome) {
        try {
            return manager.createQuery("from Timer where upper(nome) = :nome", Timer.class)
                    .setParameter("nome", nome.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Timer> todos() {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Timer.class);

        return criteria.addOrder(Order.asc("nome")).list();

    }
    
     public Timer guardar(Timer timer) {
        
        return manager.merge(timer);
    }
     
    @Transactional
    public void remover(Timer timer) throws NegocioException {
        try {
            timer = porId(timer.getId());
            manager.remove(timer);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Timer não pode ser excluído.");
        }
    }
}
