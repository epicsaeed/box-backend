package com.boxcinemas;

import java.util.UUID;

public class Crew {

	private String fname;
	private String lname;
	private String DOB;
	private char role;
	private String crewID;
	
	//Default constructors
	public Crew() {
		generateID();
	}
	
	public Crew(String fname, String lname, String DOB, char role) {
		this.fname = fname;
		this.lname = lname;
		this.DOB = DOB;
		this.role = role;
		generateID();
	}
	
	public Crew(Crew that) {
		this.fname = that.fname;
		this.lname = that.lname;
		this.DOB = that.DOB;
		this.role = that.role;
		this.crewID = that.crewID;
	}
	
	//Generates a random unique ID
	private void generateID() {
		UUID id = UUID.randomUUID();
		String short_id = id.toString().substring(0,9);
		this.crewID = "C"+short_id;
		//System.out.println("Generated Crew ID: " + this.crewID);
	}
	
	//Prints all object attributes
	public void Print() {
		System.out.println("Firstname: " + fname);
		System.out.println("Lastname: " + lname);
		System.out.println("CrewID: " + crewID);
		System.out.println("Date of birth: " + DOB);
		String fullrole = (role == 'A') ? "Actor/Actress" : "Director";
		System.out.println("Role: " + fullrole);
	}
	
	public void setFirstname(String first) {
		this.fname = first;
	}
	
	public String getFirstname() {
		return fname;
	}
	
	public void setLastname(String last) {
		this.lname = last;
	}
	
	public String getLastname() {
		return lname;
	}
	
	public void setDOB(String dob) {
		this.DOB = dob;
	}
	
	public String getDOB() {
		return DOB;
	}
	
	public String getCrewID() {
		return crewID;
	}
	
	public void setRole(char role) {
		this.role = role;
	}
	
	public char getRole() {
		return role;
	}
	
	public void setCrewID(String ID) {
		this.crewID = ID;
	}
	
	public boolean isEmpty() {
		if(fname == null) return true;
		else return false;
	}
}
