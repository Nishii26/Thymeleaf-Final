package com.nish.thymeleafCRUD.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
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
import com.google.gson.GsonBuilder;
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
		Employee oEmployee = new Employee();
		try {
			List<Employee> empList = helper.ApiCallGet("http://localhost:1234/employee/get/all",TOKEN,Employee.class);
			model.addAttribute("list",empList);
			model.addAttribute("createEmployee",oEmployee);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "empDetails";	
	}
	
	@RequestMapping(value="/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String AddEmployee(@ModelAttribute("createEmployee")Employee oEmployee) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		String json  = gson.toJson(oEmployee);
		try {
			helper.ApiCallPostWithToken("http://localhost:1234/employee/add", TOKEN, json, Employee.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/get/all";
	}
	
	@RequestMapping(value="/saveEdit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String SaveEditEmployee(@ModelAttribute("editEmployee")Employee oEmployee) {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		String json  = gson.toJson(oEmployee);
		try {
			helper.ApiCallPostWithToken("http://localhost:1234/employee/update", TOKEN, json, Employee.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/get/all";
	}
	
	@RequestMapping(value="/edit/{id}",method= {RequestMethod.GET})
	public String editEmployeeById(@PathVariable("id") int id,Model model) {
		try {
			Employee emp = helper.ApiCallGetById("http://localhost:1234/employee/get/"+id, TOKEN, Employee.class);
			model.addAttribute("editEmployee",emp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "editEmp";
	}
	
	@RequestMapping(value="/delete/{id}",method= {RequestMethod.GET})
	public String deleteEmployeeById(@PathVariable("id") int id) {
		HttpGet getRequest = new HttpGet("http://localhost:1234/employee/delete/"+id);
		getRequest.setHeader("Authorization", "Bearer "+TOKEN);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse response = httpClient.execute(getRequest);
			System.out.println(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/get/all";
	}
	
}