package com.bonrix.dggenraterset.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bonrix.dggenraterset.Model.Parameter;

public interface ParameterRepository extends BaseRepository<Parameter,Long>{

	@Query("from Parameter where prmtype=:prmtype")
	public List<Parameter> findByPrmtype(@Param("prmtype") String prmtype);
	
}
