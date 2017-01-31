package com.virtualpairprogrammers.services.diary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.dataaccess.ActionDao;
import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;

@Transactional
@Component
public class DiaryManagementServiceProductionImpl implements DiaryManagementService 
{
	@Autowired
	private ActionDao dao;
	
	// when constructor is commented cause it's using autowiring.
	
	
	public DiaryManagementServiceProductionImpl(ActionDao dao){
		this.dao = dao;
	}
	
	
	public void recordAction(Action action) throws CustomerNotFoundException 
	{
			//throw new CustomerNotFoundException();
			dao.create(action);
			
		
	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) 
	{
		return dao.getIncompleteActions(requiredUser);
	}

}
