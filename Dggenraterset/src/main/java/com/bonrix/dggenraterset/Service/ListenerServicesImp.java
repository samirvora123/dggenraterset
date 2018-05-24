package com.bonrix.dggenraterset.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

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

import com.bonrix.dggenraterset.TcpServer.EnergyMeterServer;
import com.bonrix.dggenraterset.TcpServer.GT06Server;
import com.bonrix.dggenraterset.TcpServer.TK103Server;
import com.bonrix.dggenraterset.TcpServer.Testserver;
import com.sun.istack.internal.logging.Logger;

@Service("ListenerServices")
public class ListenerServicesImp implements ListenerServices {
	
	private Logger log=Logger.getLogger(ListenerServicesImp.class);
	
	@Override
	@Async
	public void startGT06(String ipaddress, int port) {
		
		log.info("Starting GT06");
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
		log.info("Starting Tk103");
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
		log.info("Starting Test");
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
	public void startEnergyMeterServer(String ipaddress, int port) {
		log.info("Starting EnergyMeterServer On IP "+ipaddress+" Port "+port);
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
	  		        pipeline.addLast("handler", new EnergyMeterServer.HandlerEnergyMeter());
	  		        return pipeline;
	  		      }
	  		    });
	  		   
	  		    bootstrap.bind(new InetSocketAddress(ipaddress,port));
		
	}
	

	



	

}
