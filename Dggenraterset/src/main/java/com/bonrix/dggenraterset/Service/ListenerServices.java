package com.bonrix.dggenraterset.Service;


public interface ListenerServices {
	
	void startGT06(String ipaddress, int port);
	void startTk103(String ipaddress, int port);
	void startEnergyMeterServer(String ipaddress, int port);
	void test(String ipaddress, int port);

}
