package com.bonrix.dggenraterset.Service;

import com.bonrix.dggenraterset.Model.Lasttrack;

public interface LasttrackServices {

	void saveAndFlush(Lasttrack lTrack);

	Lasttrack findOne(Long deviceid);

}
