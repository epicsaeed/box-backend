package com.spring.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.box.database.DatabaseController;
import com.spring.box.helpers.Helpers;
import com.spring.box.models.Film;

@CrossOrigin
@RestController
public class RequestHandler {

	DatabaseController ctrl = new DatabaseController();
	
	@GetMapping("/films")
	public ArrayList<Film> getAllFilms(){
		return ctrl.getAllFilms();
	}
	
	@GetMapping("/films/{fid}")
	public Film getFilm(@PathVariable("fid") String FID) {
		return ctrl.getFilmByID(FID);
	}
	
	@PostMapping(value="/films/add", consumes="application/json", produces="application/json")
	public Map<String,String> insertFilm(@RequestBody Film film){
		Map<String,String> response = new HashMap();
		if(ctrl.addFilm(film)) {
			response.put("code", "success");
			response.put("message", "Film has been inserted successfully");
		}else {
			response.put("code", "fail");
			response.put("message", "Film was not inserted");	
		}
		return response;
	}
	
	@GetMapping("films/posters/{film_title}")
	public Map<String,String> getFilmPoster(@PathVariable("film_title")String film_title) {
		String poster = "";
		Map<String,String> response = new HashMap();
		Helpers fetcher = new Helpers();
		poster = fetcher.getPosterPath(film_title, "original");
		response.put("poster", poster);
		return response;
	}
	
	@GetMapping("films/delete/{film_title}")
	public Map<String,String> deleteFilm(@PathVariable("film_title") String title){
		System.out.println("Title: " + title);
		Map<String,String> response = new HashMap();
		if(ctrl.deleteFilm(title)) {
			response.put("code", "success");
			response.put("message", title + " was deleted successfully.");
		}else {
			response.put("code", "fail");
			response.put("message", "error occured when deleting " + title);
		}
		return response;
	}
	
	
}
