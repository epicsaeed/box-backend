package com.spring.box.helpers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;



public class Helpers {

	//returns full image poster path of the passed film title
	public String getPosterPath(String filmtitle, String size) {
		String url = "https://api.themoviedb.org/3/search/movie";
		String baseurl = "https://image.tmdb.org/t/p/"+size+"/";
		String poster_path = "";
		try {
			
			HttpResponse <JsonNode> jsonResponse = Unirest.get(url)
												.queryString("api_key","61be1c6ff66df6779bc88faf76a3fcd6")
												.queryString("query", filmtitle)
												.asJson();
			String jsonString = jsonResponse.getBody().toString();
			JSONObject obj = new JSONObject(jsonString);
			JSONArray arr = obj.getJSONArray("results");
			try{
				String poster = arr.getJSONObject(0).getString("poster_path");
				poster_path = baseurl + poster;
			}catch( JSONException e) { e.printStackTrace(); }
			
			
		}catch( Exception e) { e.printStackTrace(); }
		return poster_path; 
	}
	
	//returns film details
	public Map<String,String> getFilmDetails(String title){
		Map<String,String> response = new HashMap();
		String url = "https://api.themoviedb.org/3/search/movie";
		
		try{
			HttpResponse <JsonNode> jsonResponse = Unirest.get(url)
					.queryString("api_key","61be1c6ff66df6779bc88faf76a3fcd6")
					.queryString("query", title)
					.asJson();
			String jsonString = jsonResponse.getBody().toString();
			JSONObject obj = new JSONObject(jsonString);
			JSONArray arr = obj.getJSONArray("results");
			try {
				String film_title = arr.getJSONObject(0).getString("title");
				String film_release_date = arr.getJSONObject(0).getString("release_date");
			}catch( JSONException e) { e.printStackTrace(); }
					
		}catch( Exception e) { e.printStackTrace(); }
		
		return response;
	}
}
