package com.bonrix.dggenraterset.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bonrix.dggenraterset.Model.Devicemaster;

@Repository
public interface DevicemasterRepository extends  BaseRepository<Devicemaster,Long> {

	@Query(value="select deviceprofile.profilename,devicemaster.deviceid,devicemaster.devicename,deviceprofile.prid from deviceprofile join devicemaster on  deviceprofile.prid=devicemaster.prid_fk", nativeQuery=true)
	public abstract List<Object[]> joinlist();
	
	
	public  Devicemaster findByImei(String imei) ;
	
	/*@Query("from device_profile join device_info on  device_profile.prid=device_info.prid_fk and  device_info.deviceinfo_id=:deviceinfo_id")
	public List<Device_Info> getlistBydeviceinfo_id(@Param("deviceinfo_id") int deviceinfo_id);*/
}
