package com.spring.box.models;

import java.util.ArrayList;

public class Production {

	private Film film;
	private ArrayList <Crew> cast;
	
	
	public Production() {
		super();
		film = null;
		cast = new ArrayList<Crew>(4);
	}
	
	public void addCrew(Crew crew) {
		cast.add(crew);
	}

	public Film getFilm() {
		return film;
	}
	
	public void addFilm(Film film) {
		this.film = film;
	}
	
	public ArrayList<Crew> getCrew_members() {
		return cast;
	}
	
	public Crew getDirector() {
		Crew dir = new Crew();
		for(int i=0; i < cast.size()-1;i++)
			if(cast.get(i).getRole() == 'D') dir = cast.get(i);
		return dir;
	}
	
	public ArrayList<Crew> getActors(){
		ArrayList<Crew> actors = new ArrayList<Crew>();
		for(int i =0; i<cast.size();i++)
			if(cast.get(i).getRole() == 'A') actors.add(cast.get(i));
		return actors;
	}
	
	public boolean isEmpty() {
		if(film == null && cast == null) return true;
		else return false;
	}
	
	public void Print() {
		System.out.println("Film: " + film.getTitle());
		//Crew director;
		for(int i=0;i<cast.size();i++) 
			cast.get(i).Print();

	}
	
}
