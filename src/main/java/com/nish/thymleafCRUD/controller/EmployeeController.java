package com.nish.thymleafCRUD.controller;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@RequestMapping(value="/get/all",produces ="application/json",method= {RequestMethod.GET})
	public String allEmp(Model model)
	{
		
		HttpGet getRequest = new HttpGet("http://localhost:1234/employee/get/all");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(getRequest);
			String contents = EntityUtils.toString(response.getEntity());
			System.out.print(contents);
			model.addAttribute("list",contents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
		
	}
	
}
