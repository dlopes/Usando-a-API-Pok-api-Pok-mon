/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.organizacao.poketimer.repository;

import com.organizacao.poketimer.model.Poder;
import com.organizacao.poketimer.model.Treinador;
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
public class Poderes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntityManager manager;

    public Poder poderPorId(Long id) {
        return manager.find(Poder.class, id);
    }

    public Poder poderPorNome(String nome) {
        try {
            return manager.createQuery("from Poder where upper(nome) = :nome", Poder.class)
                    .setParameter("nome", nome.toUpperCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Poder> todos() {
        Session session = manager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Poder.class);

        return criteria.addOrder(Order.asc("nome")).list();

    }
    
     public Poder guardar(Poder poder) {
        return manager.merge(poder);
    }
     
    @Transactional
    public void remover(Poder poder) throws NegocioException {
        try {
            poder = poderPorId(poder.getId());
            manager.remove(poder);
            manager.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Poder não pode ser excluído.");
        }
    }

    public Poder porNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
