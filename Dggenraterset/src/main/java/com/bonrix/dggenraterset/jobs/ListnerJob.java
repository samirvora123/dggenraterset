package com.bonrix.dggenraterset.jobs;


import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bonrix.dggenraterset.Service.ListenerServices;
import com.bonrix.dggenraterset.TcpServer.EnergyMeterServer.HandlerEnergyMeter;



public class ListnerJob {
	private static ResourceBundle rb = ResourceBundle.getBundle("socket");
	private Logger log = Logger.getLogger(ListnerJob.class);
	@Autowired
	ListenerServices ls;

	@PreDestroy
	public void Destroy() {
		log.info("Destroy ListnerJob");
	}
	@PostConstruct
	public void init() {
		String Ipaddress=rb.getString("ipaddress");
		log.info("Start ListnerJob");
		if(rb.containsKey("GT06"))
		{
		ls.startGT06(Ipaddress,Integer.parseInt(rb.getString("GT06")));
		}
		
		
		if(rb.containsKey("tk103"))
		{
		ls.startTk103(Ipaddress,Integer.parseInt(rb.getString("tk103")));
		}
		
		if(rb.containsKey("EnagryMeter"))
		{
		ls.startEnergyMeterServer(Ipaddress,Integer.parseInt(rb.getString("EnagryMeter")));
		}
		
	
		
		log.info("ListnerJob ConstructEd");
		
	}

}
