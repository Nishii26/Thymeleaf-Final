package com.nish.thymeleafCRUD.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nish.thymeleafCRUD.Pojo.Employee;
import com.nish.thymeleafCRUD.Pojo.LoginCredentials;
import com.nish.thymeleafCRUD.Pojo.LoginResponse;
import com.nish.thymeleafCRUD.Utils.Helper;


@Controller
public class EmployeeController {
	
	Helper helper = new Helper();
	static String TOKEN = null;
	
	@RequestMapping(value="/")
    public String Index(Model model) {
		LoginCredentials oLoginCredentials = new LoginCredentials();
		model.addAttribute("loginForm",oLoginCredentials);
        return "index";
    }
	@RequestMapping(value="/loginAs" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String LoginAs(@ModelAttribute("loginForm")LoginCredentials oLoginCredentials) {			
			Gson gson = new Gson();
			String json = gson.toJson(oLoginCredentials);
			try {
				LoginResponse loginResponse = helper.ApiCallPost("http://localhost:1234/login", json,LoginResponse.class);
				TOKEN = loginResponse.getToken();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(TOKEN == null)
			return "index";
		else
		 	return "redirect:/get/all";	
	}

	@RequestMapping(value="/get/all",method= {RequestMethod.GET})
	public String getAllEmployees(Model model)
	{
		try {
			List<Employee> empList = helper.ApiCallGet("http://localhost:1234/employee/get/all",TOKEN,Employee.class);
			model.addAttribute("list",empList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "empDetails";	
	}
	
	@RequestMapping(value="/add",method= {RequestMethod.POST})
	public String AddEmployee() {
		return "addEmp";
	}
	
	@RequestMapping(value="/delete/id",method= {RequestMethod.POST})
	public String deleteEmployeeById(@PathVariable("id") int id) {
		HttpPost postRequest = new HttpPost("http://localhost:1234/employee/delete/"+id);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(postRequest);
			System.out.println(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "empDetails";
	}
	
}