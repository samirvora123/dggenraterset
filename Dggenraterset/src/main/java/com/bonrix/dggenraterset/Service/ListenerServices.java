package com.bonrix.dggenraterset.Service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface ListenerServices {
	
	void startGT06(String ipaddress, int port);
	void startTk103(String ipaddress, int port);
	void test(String ipaddress, int port);
	public void startMQTT(String ipaddress, int port) throws MqttException;

}
