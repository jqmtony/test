package com.ssm.demo.webservice.service.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssm.demo.entity.User;
import com.ssm.demo.service.IUserService;
import com.ssm.demo.webservice.service.IWebServiceService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
@Component("WebServiceServiceImpl")
@WebService(endpointInterface = "com.ssm.demo.webservice.service.IWebServiceService", serviceName = "WebService")
public class WebServiceServiceImpl implements IWebServiceService{
	@Autowired
	private IUserService userService;
	
	@Override
	public String testWebService(boolean isTest) {
		Map<String, List> map = new HashMap<String, List>();
		List<User> userList = new ArrayList<User>();
		if(isTest){
			userList = userService.selectAllUser();
		}else{
			userList = userService.selectAllUser();
		}
		map.put("userList", userList);
		// 将map变为xml
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true); //启用注解驱动,便于对字段重命名
	    // xml数据取消格式化,以便压缩数据
	    StringWriter sw = new StringWriter();
	    xstream.marshal(map, new CompactWriter(sw));
	    String xml = sw.toString();
	    
	    return xml;
	}

	//测试地址： http://localhost:8080/ssm.demo/webservice/testWebService?wsdl
}
