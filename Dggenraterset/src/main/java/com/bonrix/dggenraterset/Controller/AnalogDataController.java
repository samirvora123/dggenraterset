package com.bonrix.dggenraterset.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bonrix.dggenraterset.Model.Analogdata;
import com.bonrix.dggenraterset.Model.SpringException;
import com.bonrix.dggenraterset.Service.AnalogDataServices;
import com.bonrix.dggenraterset.Service.DevicemasterServices;
import com.bonrix.dggenraterset.Service.ParameterServices;


@Transactional
@RestController
public class AnalogDataController {
	@Autowired
	AnalogDataServices AnalogDataservice;
	@Autowired 
	DevicemasterServices Deviceinfoservices;
	@Autowired 
	ParameterServices Parameterservices;
	
	@RequestMapping(method=RequestMethod.POST,value="/admin/analogdata/{did}/{analog_id}")@ExceptionHandler({SpringException.class})
	public String savedata(@RequestBody Analogdata bs,@PathVariable("did") Long did,@PathVariable("analog_id") Long analog_id )
	{ 
		bs.setDevice(Deviceinfoservices.findOne(did));
		bs.setAnalogdigi(Parameterservices.get(analog_id));
		AnalogDataservice.save(bs);
		return new SpringException(true, "Analogdata Sucessfully Added").toString();
	}
	
	@RequestMapping(value="/admin/analogdata" ,produces={"application/json"})
	public List<Analogdata> getcomponetlist()
	{
		return AnalogDataservice.getlist();
	}
	
		@RequestMapping(value="/admin/analogdata/{id}",method=RequestMethod.GET)
		public Analogdata getcomponet(@PathVariable int id) {
			return AnalogDataservice.get(id);
		}
	
		//set Content-Type=application/json ANd set raw=raw={"macaddress":"asuiags", "scannerName":"asuiags","latlang":"asuiags", "cordinate":"asuiags" }(here put key and value and pass arry of it)
		@RequestMapping(method=RequestMethod.PUT,value="/admin/analogdata/{id}")
		public String updatecomponet(@RequestBody Analogdata bs,@PathVariable int id)
		{
			bs.setId(id);
			return AnalogDataservice.update(bs);
		}
		
		@RequestMapping(method=RequestMethod.DELETE,value="/admin/analogdata/{id}")
		public String deletecomponet(@PathVariable int id)
		{
			return AnalogDataservice.delete(id);
		}
}
