package com.bonrix.dggenraterset.Service;

import java.util.List;

import com.bonrix.dggenraterset.Model.Analogdata;
public interface AnalogDataServices {
	
	List<Analogdata> getlist();

	void save(Analogdata bs);

	Analogdata get(int id);

	String delete(int id);

	String update(Analogdata bs);

}
