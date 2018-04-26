package com.bonrix.dggenraterset.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonrix.dggenraterset.Model.History;
import com.bonrix.dggenraterset.Repository.HistoryRepository;

@Service("historyServices")
public class HistoryServicesImp implements HistoryServices {

	@Autowired
	HistoryRepository repository;
	
	@Override
	public void saveAndFlush(History hist) {
		repository.saveAndFlush(hist);
	}

}
