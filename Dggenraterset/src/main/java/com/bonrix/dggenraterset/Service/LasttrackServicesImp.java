package com.bonrix.dggenraterset.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonrix.dggenraterset.Model.Lasttrack;
import com.bonrix.dggenraterset.Repository.LasttrackRepository;

@Service("lasttrackServices")
public class LasttrackServicesImp implements LasttrackServices {

	@Autowired
	LasttrackRepository repository;
	@Override
	public void saveAndFlush(Lasttrack lTrack) {
		repository.saveAndFlush(lTrack);
		
	}
	@Override
	public Lasttrack findOne(Long deviceid) {
		return repository.findOne(deviceid);
	}

}
