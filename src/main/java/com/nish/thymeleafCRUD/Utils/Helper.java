package com.nish.thymeleafCRUD.Utils;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Helper {
	
	public <T> T ApiCallPost(String url, String json, Class<T> genericClass) throws IOException
	{
		StringEntity input = null;
		String entity = null;
		try {
			input = new StringEntity(json);
			input.setContentType("application/json");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpPost postRequest = new HttpPost(url);
		DefaultHttpClient httpClient = new DefaultHttpClient();
	    postRequest.setEntity(input);
	    try {
			HttpResponse response = httpClient.execute(postRequest);
			 if (response.getStatusLine().getStatusCode() == 200) {
			      entity = EntityUtils.toString(response.getEntity());
			 }
			 else {
				 entity = EntityUtils.toString(response.getEntity());
			 }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.readValue(entity, genericClass);
	}
	
	public <T, genericClass> List<genericClass>  ApiCallGet(String url, String token,Class<T> genericClass) throws IOException
	{
		
		HttpGet getRequest = new HttpGet(url);
		getRequest.setHeader("Authorization", "Bearer "+token);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(getRequest);
		String contents = EntityUtils.toString(response.getEntity());
		ObjectMapper mapper = new ObjectMapper();
		List<genericClass> list = mapper.readValue(contents, new TypeReference<List<genericClass>>() {});
		return list;
	}
	

}
