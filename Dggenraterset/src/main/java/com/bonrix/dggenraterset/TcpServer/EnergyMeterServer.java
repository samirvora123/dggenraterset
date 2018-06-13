package com.bonrix.dggenraterset.TcpServer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bonrix.common.exception.BonrixException;
import com.bonrix.common.utils.GoogleMapsApi;
import com.bonrix.dggenraterset.Model.DeviceProfile;
import com.bonrix.dggenraterset.Model.Devicemaster;
import com.bonrix.dggenraterset.Model.History;
import com.bonrix.dggenraterset.Model.Lasttrack;
import com.bonrix.dggenraterset.Repository.DevicemasterRepository;
import com.bonrix.dggenraterset.Repository.HistoryRepository;
import com.bonrix.dggenraterset.Repository.LasttrackRepository;
import com.bonrix.dggenraterset.Tools.StringTools;
import com.bonrix.dggenraterset.Utility.ApplicationContextHolder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;



public class EnergyMeterServer{
	public static class HandlerEnergyMeter extends SimpleChannelUpstreamHandler {
	private Logger log = Logger.getLogger(HandlerEnergyMeter.class);

	LasttrackRepository lasttrackrepository=ApplicationContextHolder.getContext().getBean(LasttrackRepository.class);

	
	DevicemasterRepository devicemasterRepository=ApplicationContextHolder.getContext().getBean(DevicemasterRepository.class);

	
	HistoryRepository histroyrepository=ApplicationContextHolder.getContext().getBean(HistoryRepository.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
	 
	 
	@SuppressWarnings("unchecked")
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws ParseException, JsonParseException, JsonMappingException, IOException, BonrixException {
		//ATL861693031524309,$GPRMC,000028.021,V,,,,,0000,0.00,060180,,,N*55,#01111010000100,0,0,0,2.13,0,3.931,20,404,24,3a9d,3f5,0,,1.0_enr_mtr,,INTERNET,00000113FFFFFFFF,ATL
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
	        	   Devicemaster device = devicemasterRepository.findByImei(imei);
	        	   if(device!=null)
	        	   {
	        	   String datestr = msgary[10]+msgary[2].substring(0,6) ;
	        	   log.info("SAMEnargymeter:: Isvalid" +msgary[3]);
	        	   boolean isvalid=Boolean.valueOf("A".equals(msgary[3]));
	        	  
	        	   log.info("SAMEnargymeter:: Is Live" +DatatypeConverter.printHexBinary(strmsg.substring(0, 2).getBytes()));
	        	   log.info("SAMEnargymeter:: Checksumvalue: " +DatatypeConverter.printHexBinary(msgary[msgary.length-1].split("ATL")[1].getBytes()));
	        	   boolean islive = DatatypeConverter.printHexBinary(strmsg.substring(0, 2).getBytes()).equals("2001");
	        
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
	              String ennargymeterdata=msgary[31];
	              log.info("ennargymeterdata::: "+ennargymeterdata+" Serial Port 1 Data:: "+ennargymeterdata.substring(0, 8)+" Serial Port 2 Data:: "+ennargymeterdata.substring(8, 15));
	              
	            
	              // 404,24,3a9d,3f5,0,,1.0_enr_mtr,,INTERNET,00000113FFFFFFFF,ATL
	              
	              
	          	DeviceProfile dp = device.getDp();
				JSONObject jo = new JSONObject();
				JSONArray digitaljsonarr = new JSONArray();
				ObjectMapper mapper=new ObjectMapper();
				mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
				mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
			
				JSONObject parameters = new JSONObject(dp.getParameters());
				String digitalldata=msgary[14];
			//	#11011001001010
				log.info("digianalogdata::: "+digitalldata);
				JSONArray digitals =parameters.getJSONArray("Digital");
				for (int i = 0; i < digitals.length(); i++) {
					JSONObject obj = (JSONObject) digitals.get(i);
					JSONObject digiobj = new JSONObject();
						boolean dig1 = false;
						int ioindex=obj.getInt("dioindex");
						log.info("DIGI "+(i+1)+" :: "+digitalldata.substring(ioindex, ioindex+1));
						if (digitalldata.substring(ioindex,ioindex+1).equals("1")) {
							dig1 = !((Boolean) obj.get("reverse"));
						} else {
							dig1 = ((Boolean) obj.get("reverse"));
						}
				    digiobj.put("digitalname", obj.get("parametername").toString());
					digiobj.put("digitalvalue", dig1);
					digiobj.put("digitalindex", obj.getInt("dioindex"));
					digitaljsonarr.put(digiobj);
				}

				JSONArray analogjsonarr = new JSONArray();
				JSONArray analogs =  parameters.getJSONArray("Analog");
				for (int i = 0; i < analogs.length(); i++) {
					JSONObject obj = (JSONObject) analogs.get(i);
					JSONObject analogobj = new JSONObject();
					int ioindex=obj.getInt("analogioindex");
					log.info("Analog "+(i+1)+" :: "+msgary[14+ioindex]);
					analogobj.put("analogname", obj.get("analogname").toString());
					analogobj.put("analogvalue", msgary[14+ioindex].toString());
					analogobj.put("analogindex", ioindex);
					analogjsonarr.put(analogobj);
				}
	              log.info("serial Port 1::"+ennargymeterdata.substring(0, 8)+"serial Port 2::"+ennargymeterdata.substring(8,16));
				JSONArray rs232arr = new JSONArray();
				JSONArray rs232 = parameters.getJSONArray("Rs232");
				for (int i = 0; i < rs232.length(); i++) {
					JSONObject obj = (JSONObject) rs232.get(i);
					JSONObject rs232obj = new JSONObject();
					int ioindex=obj.getInt("rs232ioindex");
					String serialportdata=ennargymeterdata.substring((ioindex-1)*8, ((ioindex-1)*8)+8);
					if(!serialportdata.equals("FFFFFFFF")) {
					int serialport=Integer.parseInt(ennargymeterdata.substring((ioindex-1)*8, ((ioindex-1)*8)+8),16);
					 log.info("serial Port HEXA "+ioindex+" :: "+serialport);
					 rs232obj.put("rs232name", obj.get("parametername").toString());
					 rs232obj.put("rs232value", msgary[14+ioindex].toString());
					 rs232obj.put("rs232index", ioindex);
					rs232arr.put(rs232obj);
					}else {
						rs232obj.put("rs232name", obj.get("parametername").toString());
						 rs232obj.put("rs232value", "Not Applicable");
						 rs232obj.put("rs232index", ioindex);
					}
				}
				JSONObject obj=new JSONObject();
				obj.put("latitude", latitude);
				obj.put("longitude", Langitude);
				History hist = new History(device.getDeviceid(), device.getUserId(),sdf.parse(datestr),new Date(),new ObjectMapper().readValue(jo.toString(),Map.class),new ObjectMapper().readValue(obj.toString(),Map.class));
				Lasttrack lTrack = new Lasttrack(device.getDeviceid(), device.getUserId(),sdf.parse(datestr),new Date(),new ObjectMapper().readValue(jo.toString(),Map.class),new ObjectMapper().readValue(obj.toString(),Map.class));
				histroyrepository.saveAndFlush(hist);
				if(islive)
				lasttrackrepository.saveAndFlush(lTrack);
				
	        	   }else{
	        		   log.info("Unknown Device Found:::::: "+imei);
	        	   }
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
	
	@SuppressWarnings("unused")
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
