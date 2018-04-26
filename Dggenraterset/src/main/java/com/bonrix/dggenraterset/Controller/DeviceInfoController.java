package com.bonrix.dggenraterset.Controller;


import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bonrix.dggenraterset.Model.BonrixUser;
import com.bonrix.dggenraterset.Model.Devicemaster;
import com.bonrix.dggenraterset.Model.SpringException;
import com.bonrix.dggenraterset.Model.UserRole;
import com.bonrix.dggenraterset.Service.DevicemasterServices;
import com.bonrix.dggenraterset.Service.DeviceProfileServices;

@Transactional
@RestController
public class DeviceInfoController {
	 private static final Logger log = Logger.getLogger(DeviceInfoController.class);
	@Autowired
	DeviceProfileServices DeviceProfileservices;
	
	@Autowired
	DevicemasterServices Deviceinfoservices;
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/savedeviceinfo/{pId}")
	@ExceptionHandler(SpringException.class)
	public String savedeviceinfo(@RequestBody Devicemaster bs,@PathVariable("pId")Long pId,
			Authentication auth) {
		BonrixUser currentUser = (BonrixUser) auth.getPrincipal();
		Set<UserRole> liss= currentUser.getUserRole();
			System.out.println(liss);
		
		if(currentUser.getUserRole().stream().anyMatch(u->u.getRole().equalsIgnoreCase("ROLE_ADMIN")))
		{
		bs.setDp(DeviceProfileservices.get(pId));
		bs.setUserId(currentUser.getUserId());
		Deviceinfoservices.save(bs);
		log.info("DeviceInfo Sucessfully Added");
		return new SpringException(true, "DeviceInfo Sucessfully Added").toString();
		}else {
			return new SpringException(false, "you have no access to add device").toString();	
		}
	}

	@RequestMapping(value="/admin/deviceinfolist", method=RequestMethod.GET)
	public String getdeviceinfolist()
	{
		return Deviceinfoservices.getlist();
	}
	
		@RequestMapping(value="/admin/deviceinfolist/{id}",method=RequestMethod.GET)
		public Devicemaster getdeviceinfolist(@PathVariable long id) {
			return Deviceinfoservices.get(id);
		}
		@RequestMapping(method=RequestMethod.PUT,value="/admin/updatedeviceinfo/{id}")
		public String updatedeviceinfo(@RequestBody Devicemaster bs,@PathVariable long id)
		{
			bs.setDeviceid(id);
			return Deviceinfoservices.update(bs);
		}
		
		@RequestMapping(method=RequestMethod.DELETE,value="/admin/deviceinfo/{id}")
		public String deleteparameter(@PathVariable Long id)
		{
			return Deviceinfoservices.delete(id);
		}
		
		@RequestMapping(value="/admin/deviceinfo/{deviceid}" ,produces={"application/json"},method=RequestMethod.GET)
		public List<Devicemaster> getlistBydeviceinfo_id(@PathVariable int deviceid)
		{
			return Deviceinfoservices.getlistBydeviceinfo_id(deviceid);
		}

		
		@RequestMapping(value="admin/devicetree", method=RequestMethod.GET)
		public String getdevicetree()
		{
			return Deviceinfoservices.getlist();
		}
}
