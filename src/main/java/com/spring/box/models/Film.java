package com.spring.box.models;

import java.util.UUID;

public class Film {

	private String title;
	private String filmID;
	private String directorID;
	private String genre;
	private String year;
	private int runtime;
	private String poster;
	
	//Empty default constructor
	public Film() {
		generateID();
	}
	
	//Filled out default constructor
	public Film(String title, String genre, String year, int runtime) {
		this.title = title;
		this.genre = genre;
		this.year = year;
		this.runtime = runtime;
		this.directorID = "N/A";
		this.poster = "";
		generateID();
	}
	
	//Method to clone a film into another
	public Film(Film another) {
		this.title = another.title;
		this.filmID = another.filmID;
		this.directorID = another.directorID;
		this.genre = another.genre;
		this.year = another.year;
		this.runtime = another.runtime;
		this.poster = another.poster;
	}
	
	//Generates a random unique ID
	private void generateID() {
		UUID id = UUID.randomUUID();
		String short_id = id.toString().substring(0,9);
		this.filmID = "F"+short_id;
		//System.out.println("Generated FilmID: " + this.filmID);
	}
	
	//Prints all object attributes
	public void Print() {
		System.out.println("Title: " + title);
		System.out.println("FilmID: " + filmID);
		System.out.println("Director ID: " + directorID);
		System.out.println("Release Year: " + year);
		System.out.println("Genre: " + genre);
		System.out.println("Runtime: " + runtime + " minutes.");
		System.out.println("Poster URL: " + poster);
		
	}
	
	//Returns a boolean if the film object is empty
	public boolean isEmpty() {
		if(title == null) return true;
		else return false;
	}
	
	//Sets film title to passed string
	public void setTitle(String title) {
		this.title = title;
	}
	
	//Returns film title as string
	public String getTitle() {
		return this.title;
	}

	//Sets director ID to passed string
	public void setDirectorID(String ID) {
		this.directorID = ID;
	}

	//Returns director ID as string
	public String getDirectorID() {
		return this.directorID;
	}
	
	//Returns film ID as string
	public String getFilmID() {
		return this.filmID;
	}
	
	public void setFilmID(String filmid) {
		this.filmID = filmid;
	}
	
	//Sets genre to passed string
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	//Returns genre as string
	public String getGenre() {
		return this.genre;
	}
	
	//Sets film release year to passed integer
	public void setYear(String year) {
		this.year = year;
	}
	
	//Returns film year as integer
	public String getYear() {
		return this.year;
	}
	
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public void setPoster(String url) { 
		this.poster = url;
	}
	
	public String getPoster() {
		return poster;
	}
	
	
}
