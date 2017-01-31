package com.virtualpairprogrammers.dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.virtualpairprogrammers.domain.Action;

public class ActionDaoJpaImpl implements ActionDao {
	
	@PersistenceContext
	private EntityManager em;

	public void create(Action newAction) {
		em.persist(newAction);
	}

	@SuppressWarnings("unchecked")
	public List<Action> getIncompleteActions(String userId) {
		return em.createQuery("select action from Action as action where action.owningUser=:user and action.complete=false")
				.setParameter("user", userId)
				.getResultList();
	}

	public void update(Action actionToUpdate) throws RecordNotFoundException {
		em.merge(actionToUpdate);
	}

	public void delete(Action oldAction) throws RecordNotFoundException {
		Action persistentAction = em.merge(oldAction);
		em.remove(persistentAction);
	}

}
