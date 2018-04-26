package com.bonrix.dggenraterset.Service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bonrix.dggenraterset.Model.Devicemaster;
import com.bonrix.dggenraterset.Model.SpringException;
import com.bonrix.dggenraterset.Repository.DevicemasterRepository;

@Service("Deviceinfoservices")
public class DevicemasterServicesImpl  implements DevicemasterServices {
	@Autowired
	DevicemasterRepository  repository;
	public void save(Devicemaster bs) {
		repository.save(bs);
	}
	public String getlist() {
		JSONArray arry=new JSONArray();
		List<Object[]> listt= repository.joinlist();
		listt.forEach((Object[] o)->{
			JSONObject obj=new JSONObject();
			obj.put("profilename", o[0].toString());
			obj.put("deviceinfo_id", o[1].toString());
			obj.put("devicename", o[2].toString());
			obj.put("prid", o[3].toString());
			arry.put(obj);
		});
		 return arry.toString();
	}

	@Override
	public List<Devicemaster> getlistdeviceinfo() {
		return repository.findAll();
		
				//repository.getlistBydeviceinfo_id(deviceinfo_id);
	}
	
	
	public String delete(Long id) {
		 repository.delete(id);
		 return new SpringException(true, "Deviceinfo sucessfully Deleted").toString();
	}
	@Override
	public List<Devicemaster> getlistBydeviceinfo_id(int deviceinfo_id) {
		System.out.println("the device info id"+deviceinfo_id);
		return null;
				//repository.getlistBydeviceinfo_id(deviceinfo_id);
	}
	
	@Override
	public Devicemaster get(Long id) {
	
		return repository.findOne(id);
	}
	@Override
	public Devicemaster findOne(Long id) {
		return repository.findOne(id);
	}

	@Override
	public String update(Devicemaster bs) {
		repository.saveAndFlush(bs);
		return new SpringException(true, "Deviceinfo sucessfully Updated").toString();
	}
	@Override
	public Devicemaster findByImei(String imei) {
		return repository.findByImei(imei);
	}
}
