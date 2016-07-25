package com.ssm.demo.webservice.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IWebServiceService {

	  public String testWebService(@WebParam(name = "isTest") boolean isTest);
	  
}
