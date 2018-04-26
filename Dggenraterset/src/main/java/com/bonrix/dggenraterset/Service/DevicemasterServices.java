package com.bonrix.dggenraterset.Service;

import java.util.List;

import com.bonrix.dggenraterset.Model.Devicemaster;



public interface DevicemasterServices {
	void save(Devicemaster bs);
	
	String getlist();
	List<Devicemaster> getlistdeviceinfo();
	String delete(Long id);
	
	Devicemaster get(Long id);
	
	String update(Devicemaster bs);
	
	Devicemaster findOne(Long id);
	
	List<Devicemaster> getlistBydeviceinfo_id(int deviceid);
	
	Devicemaster findByImei(String imei);
}
