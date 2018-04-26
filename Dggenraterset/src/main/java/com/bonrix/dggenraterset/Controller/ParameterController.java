package com.bonrix.dggenraterset.Controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bonrix.dggenraterset.Model.Parameter;
import com.bonrix.dggenraterset.Model.SpringException;
import com.bonrix.dggenraterset.Service.ParameterServices;

@Transactional
@RestController
public class ParameterController {

	@Autowired
	ParameterServices Parameterservices;
	
	@RequestMapping(method = RequestMethod.POST, value = "/admin/parameter")
	@ExceptionHandler(SpringException.class)
	public String savedata(@RequestBody Parameter bs) {
		System.out.println("jsxkaxbkz");
		Parameterservices.save(bs);
		return new SpringException(true, "Parameter Sucessfully Added").toString();
	}

		@RequestMapping(value="/admin/parameter" ,produces={"application/json"})
		public List<Parameter> getparameterlist()
		{
			return Parameterservices.getlist();
		}
	
		@RequestMapping(value="/admin/parameter/{id}",method=RequestMethod.GET)
		public Parameter getparameter(@PathVariable long id) {
			return Parameterservices.get(id);
		}
		@RequestMapping(method=RequestMethod.PUT,value="admin/parameter/{id}")
		public String updateparameter(@RequestBody Parameter bs,@PathVariable long id)
		{
			bs.setId(id);
			return Parameterservices.update(bs);
		}
		
		@RequestMapping(method=RequestMethod.DELETE,value="/admin/parameter/{id}")
		public String deleteparameter(@PathVariable long id)
		{
			return Parameterservices.delete(id);
		}
		
		@RequestMapping(value="/admin/parameters/{prmtype}" ,produces={"application/json"},method=RequestMethod.GET)
		public List<Parameter> getparameterlistByprmtype(@PathVariable String prmtype)
		{
			return Parameterservices.getlistByprmtype(prmtype);
		}
}
