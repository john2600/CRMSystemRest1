package com.virtualpairprogrammers.services.calls;

import java.util.Collection;

//import javax.inject.Inject;
//import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.virtualpairprogrammers.domain.Action;
import com.virtualpairprogrammers.domain.Call;
import com.virtualpairprogrammers.services.customers.CustomerManagementService;
import com.virtualpairprogrammers.services.customers.CustomerNotFoundException;
import com.virtualpairprogrammers.services.diary.DiaryManagementService;

@Transactional
@Component
// @Named
public class CallHandlingServiceImpl implements CallHandlingService {
	@Autowired
	private CustomerManagementService customerService;

	// @Inject
	@Autowired
	private DiaryManagementService diaryService;

	public CallHandlingServiceImpl(CustomerManagementService customerService, DiaryManagementService diaryService) {
		this.customerService = customerService;
		this.diaryService = diaryService;

	}

	@Transactional
	public void recordCall(String customerId, Call newCall, Collection<Action> actions)
			throws CustomerNotFoundException {
		// 1: call the customer service to record the call
		customerService.recordCall(customerId, newCall);

		// 2: call the diary service to record the actions
		try {
			for (Action nextAction : actions) {
				diaryService.recordAction(nextAction);
			}

		} catch (CustomerNotFoundException err) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw err;

		}
		// throw new NullPointerException();

	}

}
