package com.bonrix.MQTTtest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {


  public void Publishermqtt() throws MqttException {

    String messageString = "Hello Server from Publisher!";
  
    System.out.println("== START PUBLISHER ==");

    MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
    client.connect();
    MqttMessage message = new MqttMessage();
    message.setPayload(messageString.getBytes());
    client.publish("iot_data", message);
    System.out.println("\tMessage '"+ messageString +"' to 'iot_data'");
    client.disconnect();
    System.out.println("== END PUBLISHER ==");

  }


}
