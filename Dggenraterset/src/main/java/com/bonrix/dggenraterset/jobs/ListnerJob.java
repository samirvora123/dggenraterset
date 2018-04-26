package com.bonrix.dggenraterset.jobs;


import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bonrix.dggenraterset.Service.ListenerServices;



public class ListnerJob {
	private static ResourceBundle rb = ResourceBundle.getBundle("socket");
	
	@Autowired
	ListenerServices ls;

	@PreDestroy
	public void Destroy() {
		System.out.println("Destroy ListnerJob");
	}
	@PostConstruct
	public void init() {
		String Ipaddress=rb.getString("ipaddress");
		System.out.println("Start ListnerJob");
		if(rb.containsKey("GT06"))
		{
		ls.startGT06(Ipaddress,Integer.parseInt(rb.getString("GT06")));
		}
		
		
		if(rb.containsKey("tk103"))
		{
		ls.startTk103(Ipaddress,Integer.parseInt(rb.getString("tk103")));
		}
		
		try {
			ls.startMQTT(Ipaddress, 1883);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		System.out.println("ListnerJob ConstructEd");
		
	}

}
