package com.bonrix.dggenraterset.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonrix.dggenraterset.Model.Analogdata;
import com.bonrix.dggenraterset.Model.SpringException;
import com.bonrix.dggenraterset.Repository.AnalogDataRepository;

@Service("AnalogDataservice")
public class AnalogDataServicesImpl implements AnalogDataServices{
	
	@Autowired
	AnalogDataRepository repository;

	@Override
	public List<Analogdata> getlist() {
		return repository.findAll();
	}

	@Override
	public void save(Analogdata bs) {
		repository.save(bs);
		
	}

	@Override
	public Analogdata get(int id) {
		return repository.findOne(id);
	}

	@Override
	public String delete(int id) {
		 repository.delete(id);
		 return new SpringException(true, "AnalogData sucessfully Deleted").toString();
	}

	@Override
	public String update(Analogdata bs) {
		repository.saveAndFlush(bs);
		return new SpringException(true, "AnalogData sucessfully Updated").toString();
	}

}
