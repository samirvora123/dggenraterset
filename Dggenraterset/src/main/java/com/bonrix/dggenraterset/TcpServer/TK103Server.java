package com.bonrix.dggenraterset.TcpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bonrix.common.exception.BonrixException;
import com.bonrix.common.utils.BonrixConstants;
import com.bonrix.dggenraterset.Model.DeviceProfile;
import com.bonrix.dggenraterset.Model.Devicemaster;
import com.bonrix.dggenraterset.Model.History;
import com.bonrix.dggenraterset.Model.Lasttrack;
import com.bonrix.dggenraterset.Repository.DevicemasterRepository;
import com.bonrix.dggenraterset.Repository.HistoryRepository;
import com.bonrix.dggenraterset.Repository.LasttrackRepository;
import com.bonrix.dggenraterset.Tools.StringTools;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TK103Server {
	private static ResourceBundle rb = ResourceBundle.getBundle("socket");

	public static class HandlerTk103 extends SimpleChannelUpstreamHandler {
		private Logger log = Logger.getLogger(HandlerTk103.class);
		@Autowired
		@Qualifier("lasttrackrepository")
		LasttrackRepository lasttrackrepository;
		@Autowired
		@Qualifier("devicemasterRepository")
		DevicemasterRepository devicemasterRepository;
		@Autowired
		@Qualifier("histroyrepository")
		HistoryRepository histroyrepository;

		@SuppressWarnings("unchecked")
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws ParseException, JsonParseException, JsonMappingException, IOException, BonrixException {
			Calendar cal = Calendar.getInstance();
			double speed = 0.0D;
			String speed1 = "0";
			// 00 9512037471
			// BR001 161201 A 2301.9985N07233.5379E000.00938530.000000000000L00000001)
			Devicemaster device = null;
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
			String strmsg = (String) e.getMessage();
			log.info("SAM:::msg:::" + strmsg);
			StringTokenizer st = new StringTokenizer(strmsg, ")(");
			while (st.hasMoreElements()) {
				String msg = st.nextToken().toString();
				if (!msg.trim().equals("")) {
					msg = msg.substring(2);
					// log.info("SAM:::MSG:::::::::::"+msg);
					String imeinumber = msg.substring(0, 10);
					// log.info("SAM:::imeinumber::::::::::: "+imeinumber);
					device = devicemasterRepository.findByImei(imeinumber);
					Lasttrack track = lasttrackrepository.findOne(device.getDeviceid());
					msg = msg.substring(10);
					String msgType = msg.substring(0, 5);
					msg = msg.substring(5);
					boolean dig4 = false;
					if ((!msgType.equals("BO012")) && (!msgType.equals("BR001"))) {
						msg = msg.substring(14);
					} else {
						dig4 = true;
					}
					if (msgType.equals("BR001")) {
						msg = "1" + msg;
					}
					String year = msg.substring(0, 2);
					String month = msg.substring(2, 4);
					String date = msg.substring(4, 6);

					msg = msg.substring(6);
					boolean dig5 = msg.substring(0, 1).equals("A");
					msg = msg.substring(1);
					String latitude = msg.substring(0, 9);

					msg = msg.substring(9);
					// String latDirection = msg.substring(0, 1);

					msg = msg.substring(1);
					String longitude = msg.substring(0, 10);

					msg = msg.substring(10);
					// String lonDirection = msg.substring(0, 1);
					msg = msg.substring(1);
					speed1 = msg.substring(0, 5);

					msg = msg.substring(5);
					String hour = msg.substring(0, 2);
					String min = msg.substring(2, 4);
					String sec = msg.substring(4, 6);

					String datetime = date + month + year + hour + min + sec;

					msg = msg.substring(6);
					String orientAngle = msg.substring(0, 6);

					Double angle = Double.valueOf(0.0D);
					try {
						angle = Double.valueOf(Double.parseDouble(orientAngle));
					} catch (Exception ex) {
						// log.error("invalid ANGLE:" + ex);
						angle = Double.valueOf(0.0D);
					}
					msg = msg.substring(6);

					String digitalInput = "";
					if (!msgType.equals("BO012")) {
						digitalInput = msg.substring(0, 8);
					} else {
						digitalInput = "00000000";
					}
					msg = msg.substring(8);
					msg = msg.substring(1);

					/* Long odometer = Long.valueOf(Long.valueOf(msg, 16).longValue()); */
					Long odometer = (long) 0.0;

					cal.setTime(sdf.parse(datetime));
					cal.add(12, 30);
					cal.add(10, 5);
					speed = Double.parseDouble(speed1);

					double finalodometer = 0.0D;
					try {
						finalodometer = odometer.doubleValue();
					} catch (NumberFormatException nf) {
						// log.error("NFEXEPTION" + nf);
						finalodometer = 0.0D;
					}

					DeviceProfile dp = device.getDp();
					JSONObject jo = new JSONObject();
					JSONArray digitaljsonarr = new JSONArray();
					JSONObject parameters = new ObjectMapper().readValue(dp.getParameters().toString(),
							JSONObject.class);
					JSONArray digitals =parameters.getJSONArray("Digital");
					for (int i = 0; i < digitals.length(); i++) {
						JSONObject obj = (JSONObject) digitals.get(i);
						JSONObject digiobj = new JSONObject();
						if (obj.get("parametername").toString().equalsIgnoreCase("power")) {
							boolean dig1 = false;
							if (digitalInput.substring(0, 1).equals("1")) {
								dig1 = !((Boolean) obj.get("reverse"));
							} else {
								dig1 = ((Boolean) obj.get("reverse"));
							}
							digiobj.put("power", dig1);
						} else if (obj.get("parametername").toString().equalsIgnoreCase("Ignition")) {
							boolean dig2 = false;
							if (digitalInput.substring(1, 2).equals("1")) {
								dig2 = !((Boolean) obj.get("reverse"));
							} else {
								dig2 = ((Boolean) obj.get("reverse"));
							}
							digiobj.put("Ignition", dig2);
						} else if (obj.get("parametername").toString().equalsIgnoreCase("AC")) {
							boolean dig3 = false;
							if (digitalInput.substring(2, 3).equals("1")) {
								dig3 = !((Boolean) obj.get("reverse"));
							} else {
								dig3 = ((Boolean) obj.get("reverse"));
							}
							digiobj.put("AC", dig3);
						} else if (obj.get("parametername").toString().equalsIgnoreCase("SOS")) {
							if (dig4) {
								dig4 = !((Boolean) obj.get("reverse"));
							} else {
								dig4 = ((Boolean) obj.get("reverse"));
							}
							digiobj.put("SOS", dig4);
						} else if (obj.get("parametername").toString().equalsIgnoreCase("GPS")) {
							if (dig5) {
								dig5 = !((Boolean) obj.get("reverse"));
							} else {
								dig5 = ((Boolean) obj.get("reverse"));
							}
							digiobj.put("GPS", dig5);
						}
						digitaljsonarr.put(digiobj);
					}

					JSONArray analogjsonarr = new JSONArray();
					JSONArray analogs =  parameters.getJSONArray("Analog");
					for (int i = 0; i < analogs.length(); i++) {

						JSONObject obj = (JSONObject) analogs.get(i);
						JSONObject analogobj = new JSONObject();
						if (obj.get("analogname").toString().equalsIgnoreCase("Fuel")) {
							String analogParam = digitalInput.substring(5, 8);
							double analogValue = 0.0D;
							try {
								analogValue = Integer.parseInt(analogParam.substring(0, 1)) * 16 * 16;
								analogValue += Integer.valueOf(analogParam.substring(1, 2), 16).intValue() * 16;
								analogValue += Integer.valueOf(analogParam.substring(2, 3), 16).intValue();
							} catch (Exception ex) {
								analogValue = 0.0D;
							}
							analogobj.put("Fuel", analogValue);
						}
						analogjsonarr.put(analogobj);
					}

					JSONArray rs232arr = new JSONArray();
					JSONArray rs232 = parameters.getJSONArray("Rs232");
					for (int i = 0; i < rs232.length(); i++) {
						JSONObject obj = (JSONObject) rs232.get(i);
						JSONObject rs232obj = new JSONObject();
						if (obj.get("parametername").toString().equalsIgnoreCase("rs232")) {
							boolean rs2321 = false;
							if (rs2321) {
								rs2321 = !((Boolean) obj.get("reverse"));
							} else {
								rs2321 = ((Boolean) obj.get("reverse"));
							}
							rs232obj.put("rs232", rs2321);
						}
						rs232arr.put(rs232obj);
					}
					jo.put("Digital", digitaljsonarr);
					jo.put("Analog", analogjsonarr);
					jo.put("Rs232", rs232arr);

					String latitudes = StringTools.parseLatitude(latitude, "N") + "";
					String longitudes = StringTools.parseLatitude(longitude, "E") + "";
					if ((StringTools.roundEightDecimals(Double.parseDouble(latitudes))).equals("0.0")
							|| (StringTools.roundEightDecimals(Double.parseDouble(longitude)).equals("0.0"))
							|| (StringTools.roundEightDecimals(Double.parseDouble(latitudes)).contains("0000"))
							|| (StringTools.roundEightDecimals(Double.parseDouble(longitudes)).contains("0000"))
							|| (StringTools.roundEightDecimals(Double.parseDouble(latitude)).equals("0"))
							|| (StringTools.roundEightDecimals(Double.parseDouble(longitudes)).equals("0"))) {
						log.info("SAM:::worng data" + device.getDevicename());
					} else {
						angle = Double.valueOf(Double.parseDouble(orientAngle));
						JSONObject obj=new JSONObject();
						obj.put("latitude", latitudes);
						obj.put("longitude", longitudes);
						obj.put("speed", speed);
						obj.put("angle", angle);
						obj.put("odometer", finalodometer);
						obj.put("lastmove",  speed > 5.0D ? new SimpleDateFormat(BonrixConstants.SQL_DATE_TIME_FORMATE).format(new Date()) : track.getGpsdata().get("lastmove")).toString();
						Date insertingdate = cal.getTime().compareTo(new Date()) > 0 ? new Date() : cal.getTime();
						History hist = new History(device.getDeviceid(), device.getUserId(),insertingdate,new Date(),new ObjectMapper().readValue(jo.toString(),Map.class),new ObjectMapper().readValue(obj.toString(),Map.class));
						Lasttrack lTrack = new Lasttrack(device.getDeviceid(), device.getUserId(),insertingdate,new Date(),new ObjectMapper().readValue(jo.toString(),Map.class),new ObjectMapper().readValue(obj.toString(),Map.class));
						log.info("SAM:::mSG: " + lTrack.toString());
						histroyrepository.saveAndFlush(hist);
							lasttrackrepository.saveAndFlush(lTrack);
							// log.info("SAM:::saved successfully of :::" + device.getDeviceName() +
							// "::Valid GPS:" + dig5);
					}
				}
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

		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
			log.info("SAM::: Chanel Close" + e.getCause());
			e.getChannel().close();
		}
	}

	public static void main(String[] args) {

		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("handler", new TK103Server.HandlerTk103());
				return pipeline;
			}
		});
		bootstrap.bind(new InetSocketAddress(Integer.parseInt(rb.getString("Tk103"))));
	}

}
