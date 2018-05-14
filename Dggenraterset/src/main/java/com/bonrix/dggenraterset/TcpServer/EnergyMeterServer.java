package com.bonrix.dggenraterset.TcpServer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bonrix.common.exception.BonrixException;
import com.bonrix.common.utils.CheckSum;
import com.bonrix.common.utils.GoogleMapsApi;
import com.bonrix.dggenraterset.DTO.EnergyMeterDTO;
import com.bonrix.dggenraterset.Repository.DevicemasterRepository;
import com.bonrix.dggenraterset.Repository.HistoryRepository;
import com.bonrix.dggenraterset.Repository.LasttrackRepository;
import com.bonrix.dggenraterset.Tools.StringTools;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;



public class EnergyMeterServer{
	public static class HandlerEnergyMeter extends SimpleChannelUpstreamHandler {
	private Logger log = Logger.getLogger(HandlerEnergyMeter.class);
	@Autowired
	@Qualifier("lasttrackrepository")
	LasttrackRepository lasttrackrepository;
	@Autowired
	@Qualifier("devicemasterRepository")
	DevicemasterRepository devicemasterRepository;
	@Autowired
	@Qualifier("histroyrepository")
	HistoryRepository histroyrepository;
	private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
	 
	 
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws ParseException, JsonParseException, JsonMappingException, IOException, BonrixException {
		//ATL861693031524309,$GPRMC,000028.021,V,,,,,0000,0.00,060180,,,N*55,#01111010000100,0,0,0,2.13,0,3.931,20,404,24,3a9d,3f5,0,,1.0_enr_mtr,,INTERNET,FFFFFFFFFFFFFFFF,ATL
		String strmsg = (String) e.getMessage();
		log.info("SAMEnargymeter:::" + strmsg);
		 String[] msgary = strmsg.split(",");
		  try
	      {
	/*	String strmsg1= strmsg.substring(2,strmsg.length()-3);
		String checksum=strmsg.substring(strmsg.length()-3,strmsg.length()-1);
		 
		log.info("SAMEnargymeter:: " +strmsg1);
		log.info("SAMEnargymeter:: checksum " +checksum);
		log.info("checksumForm Calculation"+new CheckSum().chksum_gprs(strmsg1.getBytes()));*/
	    
	        if (strmsg.length() > 10)
	        {
	        	   String imei=msgary[0].substring(5);
	        	   log.info("SAMEnargymeter:: IMEI" +imei);
	        	   String datestr = msgary[10]+msgary[2].substring(0,6) ;
	        	   log.info("SAMEnargymeter:: Isvalid" +msgary[3]);
	        	   boolean isvalid=Boolean.valueOf("A".equals(msgary[3]));
	        	  
	        	   log.info("SAMEnargymeter:: Is Live" +DatatypeConverter.printHexBinary(strmsg.substring(0, 2).getBytes()));
	        	   log.info("SAMEnargymeter:: Checksumvalue: " +DatatypeConverter.printHexBinary(msgary[msgary.length-1].split("ATL")[1].getBytes()));
	        	   boolean il = DatatypeConverter.printHexBinary(strmsg.substring(0, 2).getBytes()).equals("2001");
	        	 
	        	   Double spd = Double.valueOf(0.0D);
	               if ((msgary[8] == "") || (msgary[8] == null) || (msgary[8].isEmpty())) {
	                 spd = Double.valueOf(0.0D);
	               } else {
	                 spd = Double.valueOf(Double.parseDouble(msgary[8]) * 1.852D);
	               }
	               Double angle=0.0d;
	               Double latitude=0.0d;
	               Double Langitude=0.0d;
	               if(isvalid)
	        	   {
	            	   if ((msgary[4] == "") || (msgary[4] == null)) {
		            	   latitude=StringTools.parseLatitude("0.0", "N");
		               } else {
		            	   latitude=StringTools.parseLatitude(msgary[4], "N");
		               }
	            	   if ((msgary[6] == "") || (msgary[6] == null)) {
	  	                 Langitude=StringTools.parseLatitude("0.0", "E");
	  	               } else {
	  	                 Langitude=StringTools.parseLatitude(msgary[6], "E");
	  	               }
	            	   log.info("latitude:: "+latitude+"Langitude:: "+Langitude);
	        	   }else
	        	   {
	        		   log.info("Cell Id: "+msgary[22]+"");
					String[] latlog=GoogleMapsApi.GetLatLng(Integer.parseInt(msgary[22]),Integer.parseInt(msgary[23]),Integer.parseInt(msgary[24],16),Integer.parseInt(msgary[25],16));
					latitude=Double.parseDouble(latlog[0]);
					Langitude=Double.parseDouble(latlog[1]);
					log.info("***latitude:: "+latitude+"***Langitude:: "+Langitude);
	        	   }
	              
	            
	             
	              spd=StringTools.roundTwoDecimals(spd.doubleValue());
	               if ((msgary[9] != "") || (msgary[9] != null)) {
	            	   angle= Double.parseDouble(msgary[9]);
	               }
	               double odometer=Double.parseDouble(msgary[18]);
	                
	               BigDecimal bd=new BigDecimal(msgary[15]).multiply(new BigDecimal(12));
	               String  analog=bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	               //chksum_gprs(strmsg.get);

	               /*         String inputs = msgary[14];
	               ge.setDig2(Boolean.valueOf("1".equals(inputs.substring(1, 2))));
	               ge.setDig3(Boolean.valueOf("0".equals(inputs.substring(3, 4))));
	               ge.setDig1(Boolean.valueOf("1".equals(inputs.substring(8, 9))));
	               ge.setDig4(Boolean.valueOf("1".equals(inputs.substring(6, 7))));
	               ge.setDig5(Boolean.valueOf("0".equals(inputs.substring(2, 3))));*/
	               
	              Date devicedate=sdf.parse(datestr);
	        }
	      }catch (Exception ex) {
	    	  ex.printStackTrace();
	      }
	      
	      
		
		
		
	}

	public static String convertlatlang(String input) {

		String dgr = input.substring(0, 4);
		String dgr2 = input.substring(4, 8);

		String dg = Integer.parseInt(dgr, 16) + "";
		int dg2 = Integer.parseInt(dgr2, 16);

		String rdgr = dg.substring(0, 2);
		double rdgr2 = Float.parseFloat(dg.substring(2, 4)) / 60;

		double remaindgr = dg2 * 0.0001 / 60;
		double fnl = Integer.parseInt(rdgr) + rdgr2 + remaindgr;

		return fnl + "";
	}
	
	private  static char chksum_gprs(char ptr)
	{
		int i = 0;
		char chksum=0;
		for(i=0;i<1000 && ptr != 0x00;i++)
		{
		chksum ^= ptr++;
		
		}
		return chksum ;
	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		log.info("SAM::: Chanel Close" + e.getCause());
		e.getChannel().close();
	}

	}
}
