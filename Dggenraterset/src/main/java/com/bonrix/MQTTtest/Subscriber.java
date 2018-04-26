package com.bonrix.MQTTtest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


public class Subscriber {


	  public void  subscribermqtt(String ipaddress,int port) throws MqttException {
	    System.out.println("== START SUBSCRIBER =="+MqttClient.generateClientId());
	    MqttClient client=new MqttClient("tcp://localhost::1883", MqttClient.generateClientId());
	    client.setCallback(new SimpleMqttCallBack());
	    client.connect();
	    client.subscribe("iot_data");
	    System.out.println("== Conected ==");
	  }


}
