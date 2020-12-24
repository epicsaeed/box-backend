package com.boxcinemas;

import java.util.UUID;

public class User {
	
	private String fname;
	private String lname;
	private String username;
	private String email;
	private String pwdhash;
	private char role;
	private int iban = 0;
	private String UID;
	
	//Default constructor
	public User(){
		generateID();
	}
	
	public User(String fname, String lname, String username, String pwdhash,String email, char role){
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.pwdhash = pwdhash;
		this.role = role;
		this.email = email;
		generateID();
	}
	
	//Generates a random unique ID
	private void generateID() {
		UUID id = UUID.randomUUID();
		String short_id = id.toString().substring(0,10);
		this.UID = short_id;
	}
	
	//Prints all objects attributes
	public void Print() {
		System.out.println("Firstname: " + fname);
		System.out.println("Lastname: " + lname);
		System.out.println("UID: " + UID);
		System.out.println("Username: " + username);
		System.out.println("Email: " + email);
		System.out.println("Password Hash: " + pwdhash);
		String full_role = (role == 'E') ? "Employee" : "Customer";
		System.out.println("Role: " + full_role);
		System.out.println("IBAN: " + iban);
	}
	
	public boolean isCustomer() {
		return (role == 'C');
	}
	
	public boolean isEmployee() {
		return (role == 'E');
	}
	
	public void setFirstname(String fname) {
		this.fname = fname;
	}
	
	public String getFirstname() {
		return fname;
	}
	
	public void setLastname(String lname) {
		this.lname = lname;
	}
	
	public String getLastname() {
		return lname;
	}
	
	public void setUsername(String usr) {
		this.username = usr;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPwdhash(String hash) {
		this.pwdhash = hash;
	}
	
	public String getPwdhash() {
		return pwdhash;
	}
	
	public void setRole(char newrole) {
		this.role = newrole;
	}
	
	public char getRole() {
		return role;
	}
	
	public void setIBAN(int iban) {
		this.iban = iban;
	}
	
	public int getIBAN() {
		return iban;
	}
	
	public String getID() {
		return UID;
	}
	
}
















