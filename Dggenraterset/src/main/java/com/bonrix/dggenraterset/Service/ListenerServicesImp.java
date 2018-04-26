package com.bonrix.dggenraterset.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.util.CharsetUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bonrix.MQTTtest.Subscriber;
import com.bonrix.dggenraterset.TcpServer.GT06Server;
import com.bonrix.dggenraterset.TcpServer.TK103Server;
import com.bonrix.dggenraterset.TcpServer.Testserver;

@Service("ListenerServices")
public class ListenerServicesImp implements ListenerServices {
	
	
	@Override
	@Async
	public void startGT06(String ipaddress, int port) {
		
		System.out.println("Starting GT06");
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(()-> {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder(CharsetUtil.ISO_8859_1));
				pipeline.addLast("encoder", new StringEncoder(CharsetUtil.ISO_8859_1));
				pipeline.addLast("handler", new GT06Server());
				return pipeline;
		});

		
		bootstrap.bind(new InetSocketAddress(ipaddress,port));
			}

	@Override
	@Async
	public void startTk103(String ipaddress, int port) {
		System.out.println("Starting Tk103");
		 ServerBootstrap bootstrap = new ServerBootstrap(
	  		      new NioServerSocketChannelFactory(
	  		      Executors.newCachedThreadPool(), 
	  		      Executors.newCachedThreadPool()));
	  		    bootstrap.setPipelineFactory(new ChannelPipelineFactory()
	  		    {
	  		      public ChannelPipeline getPipeline()
	  		        throws Exception
	  		      {
	  		        ChannelPipeline pipeline = Channels.pipeline();
	  		        pipeline.addLast("decoder", new StringDecoder());
	  		        pipeline.addLast("encoder", new StringEncoder());
	  		        pipeline.addLast("handler", new TK103Server.HandlerTk103());
	  		        return pipeline;
	  		      }
	  		    });
	  		   
	  		    bootstrap.bind(new InetSocketAddress(ipaddress,port));
		
	}

	@Override
	@Async
	public void test(String ipaddress, int port) {
		System.out.println("Starting Test");
		 ServerBootstrap bootstrap = new ServerBootstrap(
	  		      new NioServerSocketChannelFactory(
	  		      Executors.newCachedThreadPool(), 
	  		      Executors.newCachedThreadPool()));
	  		    bootstrap.setPipelineFactory(new ChannelPipelineFactory()
	  		    {
	  		      public ChannelPipeline getPipeline()
	  		        throws Exception
	  		      {
	  		        ChannelPipeline pipeline = Channels.pipeline();
	  		        pipeline.addLast("decoder", new StringDecoder());
	  		        pipeline.addLast("encoder", new StringEncoder());
	  		        pipeline.addLast("handler", new Testserver.HandlerTest());
	  		        return pipeline;
	  		      }
	  		    });
	  		   
	  		    bootstrap.bind(new InetSocketAddress(ipaddress,port));
		
	}
	
	@Override
	@Async
	public void startMQTT(String ipaddress, int port) throws MqttException {
		System.out.println("Starting MQTT");
		new Subscriber().subscribermqtt(ipaddress, port);
	}
	

	



	

}
