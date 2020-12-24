package com.spring.box.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.spring.box.helpers.Helpers;
import com.spring.box.helpers.Logger;
import com.spring.box.models.*;

public class DatabaseController {

	private Logger event = new Logger();
	Helpers external = new Helpers();

	/*				USER RELATED FUNCTIONS 				*/
	
	//Adds user to database and returns boolean of success
	public boolean addUser(User usr) {
		event.log("addUser was called with User object of id = " + usr.getID() + ".");
		boolean added = false;
		
		try {
			// Initialize connection to database
			Connection conn = DatabaseConnection.initializeDatabase(); 			
			// Generate insertion query
			Statement stmt = conn.createStatement();
            String sql = String.format("INSERT INTO USERS VALUES('%s','%s','%s','%s','%s','%s','%s','%s')",
            		usr.getUsername(),usr.getID(),usr.getPwdhash(),usr.getEmail(),usr.getRole(),
            		usr.getFirstname(),usr.getLastname(),usr.getIBAN());  
            //Perform insertion
            int result = stmt.executeUpdate(sql);  
            //Check if insertion was successful
            if( result == 1 ) {
            	added = true;
            	event.log("New user created.");
            }
            //Close database connection
            conn.close();
		}catch( Exception e) {
			e.printStackTrace();
		}
		return added;
	}
	
	//Checks user credentials and returns boolean if the passed credentials are correct
	public boolean isAuthenticated(String username, String password) {
		boolean isValid = false;
		event.log("isAuthenticated was called with the following username:" + username + ".");

		try {
			//Establish connection to database
			Connection conn = DatabaseConnection.initializeDatabase();
			//Generate statement
			Statement stmt = conn.createStatement();
			//Prepare query
			String sql = String.format("SELECT USERNAME, PWD FROM USERS WHERE USERNAME='%s' AND PWD='%s'", username,password);
			//Initialize result set
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				isValid=true;
				event.log("User [" + username + "] has been authenticated.");
			}
			//Close connection
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isValid;
	}
	
	//Returns a string of the passed user role
	public String getUserRole(String username) {
		
		event.log("getUserRole was called with the following username:" + username + ".");
		String role = "";		
		
		try {
			//Establish connection to database
			Connection conn = DatabaseConnection.initializeDatabase();
			//Generate statement
			Statement stmt = conn.createStatement();
			//Prepare query
			String sql = String.format("SELECT ROLE FROM USERS WHERE USERNAME='%s'", username);
			//Initialize result set
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) role = rs.getString("ROLE");
			event.log("Returned user role for username: " + username + " is '" + role + "'.");
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return role;
	}
	
	//Returns a user object with all its details if the user exists
	public User getUser(String username) {
		event.log("getUser was called with the following username [" + username + "].");
		User user = new User();
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM USERS WHERE USERNAME = '%s'",username);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				user.setEmail(rs.getString("EMAIL"));
				user.setFirstname(rs.getString("FNAME"));
				user.setLastname(rs.getString("LNAME"));
				String role_s = rs.getString("ROLE");
				char role = role_s.charAt(0);
				user.setRole(role);
				user.setUsername(username);
				user.setIBAN(rs.getInt("IBAN"));
			}
			
			conn.close();
			
		}catch(Exception e) { e.printStackTrace(); }
		
		return user;
	}
	
	//Checks if passed username exists (used for registration)
	public boolean usernameExists(String username) {
		event.log("usernameExists was called with the following username:" + username + ".");
		boolean exists = false;
		
		try {
			//Establish connection to database
			Connection conn = DatabaseConnection.initializeDatabase();
			//Generate statement
			Statement stmt = conn.createStatement();
			//Prepare query
			String sql = String.format("SELECT USERNAME FROM USERS WHERE USERNAME='%s'", username);
			//Initialize result set
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				exists = true;
				event.log("Username [" + username + "] was found in the database");
			}
			
			//Close connection
			conn.close();
			
			event.log("Username [" + username + "] was not found in the database");

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return exists;
	}
	
	/*				FILM RELATED FUNCTIONS 				*/
	
	//Adds passed film object to database. Returns success of process
	public boolean addFilm(Film film) {
		event.log("addFilm was called with the following film ID and title (" + film.getFilmID() +", " + film.getTitle() +")");
		boolean added = false;
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO FILMS VALUES('%s','%s','%s','%s','%s','%s')",film.getFilmID(),film.getTitle(),film.getDirectorID(),film.getYear(),film.getGenre(),film.getRuntime() );
			int result = stmt.executeUpdate(sql);
            if(result==1) added = true;
            if(added) event.log("[" + film.getTitle() + "] was added to database.");
            conn.close();
		}catch(Exception e) {e.printStackTrace();}
		
		return added;
	}
	
	//Deletes passed film object from database. Returns success of process
	public boolean deleteFilm(String filmTitle) {
		event.log("deleteFilm was called with the following film ID and title ("+filmTitle+")");
		boolean deleted = false;
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM FILMS WHERE TITLE = '%s'", filmTitle); 
			int result = stmt.executeUpdate(sql);
			if(result==1) deleted=true;
			if(deleted) event.log("["+filmTitle+"] was deleted from database.");
			conn.close();
		}catch(Exception e) { e.printStackTrace();}
		return deleted;
	}

	//Returns true if the films table is not populated
	public boolean filmsTableIsEmpty() {
		event.log("");
		boolean isEmpty = false;
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) AS TOTAL FROM FILMS";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.getInt("TOTAL") == 0) isEmpty = true;
			if(isEmpty) event.log("Films table is empty.");
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }
		return isEmpty;
	}
	
	//Returns an ArrayList of all films in database.
	public ArrayList<Film> getAllFilms() {
		event.log("getAllFilms was called.");
		ArrayList<Film> films = new ArrayList<Film>();
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM FILMS";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				//Create film object with all row values
				Film tmpFilm = new Film(rs.getString("TITLE"),rs.getString("GENRE"),rs.getString("YEAR"),rs.getInt("RUNTIME"));
				tmpFilm.setFilmID(rs.getString("FILMID"));
				tmpFilm.setDirectorID(rs.getString("DIRECTORID"));
				String poster_url = external.getPosterPath(rs.getString("TITLE"), "w500");
				tmpFilm.setPoster(poster_url);
				films.add(tmpFilm);
			}
			conn.close();
		}catch(Exception e) { e.printStackTrace();}
		
		event.log("getAllFilms has returned " + films.size() + " films.");
		return films;
		
	}
	
	//Returns an object of the passed film title
	public Film getFilmByTitle(String filmTitle) {
		event.log("getFilm was called with this title [" + filmTitle + "].");
		Film film = new Film();
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM FILMS WHERE TITLE = '%s'", filmTitle);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				//Create film object with all row values
				film.setTitle(rs.getString("TITLE"));
				film.setDirectorID(rs.getString("DIRECTORID"));
				film.setGenre(rs.getString("GENRE"));
				film.setYear(rs.getString("YEAR"));
				film.setRuntime(rs.getInt("RUNTIME"));
				film.setFilmID(rs.getString("FILMID"));
				String poster_url = external.getPosterPath(rs.getString("TITLE"), "w500");
				film.setPoster(poster_url);
			}
		}catch(Exception e) { e.printStackTrace(); }
		
		if(film.isEmpty()) event.log(film.getTitle()+" was not found in the database.");
		else event.log(film.getTitle()+" was found and returned to caller");
			
		return film;
	}
	
	//Returns an object of the passed film title
	public Film getFilmByID(String filmID) {
		event.log("getFilm was called with this Film ID [" + filmID + "].");
		Film film = new Film();
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM FILMS WHERE FILMID = '%s'", filmID);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				//Create film object with all row values
				film.setTitle(rs.getString("TITLE"));
				film.setDirectorID(rs.getString("DIRECTORID"));
				film.setGenre(rs.getString("GENRE"));
				film.setYear(rs.getString("YEAR"));
				film.setRuntime(rs.getInt("RUNTIME"));
				film.setFilmID(rs.getString("FILMID"));
				String poster_url = external.getPosterPath(rs.getString("TITLE"), "w500");
				film.setPoster(poster_url);
			}
		}catch(Exception e) { e.printStackTrace(); }
		
		if(film.isEmpty()) event.log(film.getTitle()+" was not found in the database.");
		else event.log(film.getTitle()+" was found and returned to caller");
			
		return film;
	}
	
	
	public boolean editFilm(Film prv, Film nxt) {
		event.log("editFilm was called to edit the film ["+prv.getTitle()+"].");
		boolean changed = true;
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();	
			Statement stmt = conn.createStatement();

			//Check for attribute changes
			if(prv.getTitle() != nxt.getTitle()) {
				String sql = String.format("UPDATE FILMS SET TITLE = '%s' WHERE FILMID = '%s'",nxt.getTitle(),prv.getFilmID());
				int result = stmt.executeUpdate(sql);
				if(result != 1) changed = false;
			}
			
			if(prv.getGenre() != nxt.getGenre()) {
				String sql = String.format("UPDATE FILMS SET GENRE = '%s' WHERE FILMID = '%s'",nxt.getGenre(),prv.getFilmID());
				int result = stmt.executeUpdate(sql);
				if(result != 1) changed = false;
			}
			
			if(prv.getYear() != nxt.getYear()) {
				String sql = String.format("UPDATE FILMS SET YEAR = '%s' WHERE FILMID = '%s'", nxt.getYear(),prv.getFilmID());
				int result = stmt.executeUpdate(sql);
				if(result != 1) changed = false;
			}
			
			if(prv.getRuntime() != nxt.getRuntime()) {
				String sql = String.format("UPDATE FILMS SET RUNTIME = %s WHERE FILMID = '%s'", nxt.getRuntime(), prv.getFilmID());
				int result = stmt.executeUpdate(sql);
				if(result != 1) changed = false;
			}
			
			if(changed) event.log("Film details was edited successfully.");
			else event.log("Film details was not edited.");
			
			conn.close();
		}catch(Exception e) { e.printStackTrace();}
		return changed;
	}
	
	/*				CREW RELATED FUNCTIONS 				*/
	
	//Adds a crew member to the database
	public boolean addCrew(Crew crew) {
		boolean added = false;
		event.log("AddCrew was called to add [" + crew.getFirstname() + " " + crew.getLastname());
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("INSERT INTO CREW VALUES('%s','%s','%s','%s','%s')",crew.getCrewID(),crew.getFirstname(),crew.getLastname(),crew.getDOB(),crew.getRole());
			if(stmt.executeUpdate(sql) == 1) added = true;
			if(added) event.log("Crew member [" + crew.getFirstname() + " " + crew.getLastname() + "] was added to the database.");
			conn.close();
		}catch(Exception e){ e.printStackTrace();}
		
		return added;
	}
	
	//Deletes a crew member from the database
	public boolean deleteCrew(Crew crew) {
		boolean deleted = false;
		event.log("deleteCrew was called to delete the following crew member [" + crew.getFirstname() + " " + crew.getLastname() +"].");
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("DELETE FROM CREW WHERE CREWID = '%s'",crew.getCrewID());
			if(stmt.executeUpdate(sql) == 1) deleted = true;
			if(deleted) event.log("Crew member [" + crew.getFirstname() + " " + crew.getLastname() + "] has been deleted from the database.");
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }
		return deleted;
	}	
	
	//Returns a crew object based on first and last name
	public Crew getCrew(String fname, String lname) {
		event.log("getCrew was called with the following information: ["+fname+" "+lname+"].");
		Crew found = new Crew();
		try{
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM CREW WHERE FNAME = '%s' AND LNAME = '%s'",fname,lname);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				found.setFirstname(rs.getString("FNAME"));
				found.setLastname(rs.getString("LNAME"));
				found.setCrewID(rs.getString("CREWID"));
				found.setDOB(rs.getString("DOB"));
				String str_role = rs.getString("ROLE");
				found.setRole(str_role.charAt(0));
			}
		}catch(Exception e) { e.printStackTrace(); }
		if(found.isEmpty()) event.log("Requested crew member ["+fname+""+lname+"] was not found");
		else event.log("Requested crew memmber was found. ID: " + found.getCrewID()+".");
		return found;
	}
	
	//Returns a crew object based on CrewID
	public Crew getCrew(String crewID) {
		event.log("getCrew was called by Crew ID [" + crewID + "].");
		Crew crew = new Crew();
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM CREW WHERE CREWID = '%s'",crewID);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				crew.setCrewID(crewID);
				crew.setDOB(rs.getString("DOB"));
				crew.setFirstname(rs.getString("FNAME"));
				crew.setLastname(rs.getString("LNAME"));
				String role = rs.getString("ROLE");
				crew.setRole(role.charAt(0));
			}
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }
		return crew;
	}
	//Compares data of prv crew with new crew and edits where neccessary
	public boolean editCrew(Crew prv, Crew nxt) {
		boolean edited = true;
		event.log("edit crew was called to edit the details of the following crew member: " + prv.getFirstname() + " " + prv.getLastname());
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			
			if( prv.getFirstname() != nxt.getFirstname() ) {
				String sql = String.format("UPDATE CREW SET FNAME = '%s' WHERE CREWID = '%s'",nxt.getFirstname(),prv.getCrewID());
				if(stmt.executeUpdate(sql) != 1) edited = false;
			}
			
			if( prv.getLastname() != nxt.getLastname() ) {
				String sql = String.format("UPDATE CREW SET LNAME = '%s' WHERE CREWID = '%s'",nxt.getLastname(),prv.getCrewID());
				if(stmt.executeUpdate(sql) != 1) edited = false;
			}
			
			if( prv.getDOB() != nxt.getDOB() ) {
				String sql = String.format("UPDATE CREW SET DOB = '%s' WHERE CREWID = '%s'",nxt.getDOB(),prv.getCrewID());
				if(stmt.executeUpdate(sql) != 1) edited = false;
			}
			
			if( prv.getRole() != nxt.getRole()) {
				String sql = String.format("UPDATE CREW SET ROLE = '%s' WHERE CREWID = '%s'",nxt.getRole(),prv.getCrewID());
				if(stmt.executeUpdate(sql) != 1) edited = false;
			}
			
			conn.close();
			if(edited) event.log("Crew member details changed successfully.");
			else event.log("Crew member details were not changed.");
		}catch(Exception e) { e.printStackTrace();}
		return edited;
	}
	
	//Returns an arraylist of only directors
	public ArrayList<Crew> getDirectors(){
		event.log("getDirectors was called.");
		ArrayList<Crew> directors = new ArrayList<Crew>();
		try {
			
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM CREW WHERE ROLE = 'D'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
			Crew crew = new Crew();
			crew.setFirstname(rs.getString("FNAME"));
			crew.setLastname(rs.getString("LNAME"));
			crew.setCrewID(rs.getString("CREWID"));
			crew.setDOB(rs.getString("DOB"));
			crew.setRole('D');
			directors.add(crew);
			}
			
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }
		return directors;
	}
	
	//Returns an arraylist of all actors
	public ArrayList<Crew> getActors(){
		event.log("getActors was called.");
		ArrayList<Crew> directors = new ArrayList<Crew>();
		try {
			
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM CREW WHERE ROLE = 'A'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
			Crew crew = new Crew();
			crew.setFirstname(rs.getString("FNAME"));
			crew.setLastname(rs.getString("LNAME"));
			crew.setCrewID(rs.getString("CREWID"));
			crew.setDOB(rs.getString("DOB"));
			crew.setRole('A');
			directors.add(crew);
			}
			
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }
		return directors;
	}
	
	/*				PRODUCTION RELATED FUNCTIONS 				*/

	//Adds passed production element into database
	public boolean addProduction(Production prod) {
		boolean added = true;
		event.log("addProduction was called.");
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			
			ArrayList<Crew> crew = prod.getCrew_members();
			for(int i = 0; i < crew.size() ; i++) {
				String sql = String.format("INSERT INTO PRODUCTION VALUES('%s','%s')", prod.getFilm().getFilmID(), crew.get(i).getCrewID());
				if(stmt.executeUpdate(sql) != 1) added = false;
			}
			
			conn.close();
		}catch(Exception e) { e.printStackTrace(); }

		//Add film to database
		addFilm(prod.getFilm());
		
		if(added) event.log("Production details of [" + prod.getFilm().getTitle() + "] has been added sucessfully.");
		else event.log("Error encounterd when adding production details of [" + prod.getFilm().getTitle() + "].");
		return added;
	}
	
	
	//Returns production element from database 
	public Production getProduction(String FilmID) {
		Production prod = new Production();
		event.log("getProduction was called with this film ID [" + FilmID + "].");
		
		//Get film object
		Film film = getFilmByID(FilmID);
		prod.addFilm(film);
		
		//get the crew members one by one
		
		try {
			Connection conn = DatabaseConnection.initializeDatabase();
			Statement stmt = conn.createStatement();
			String sql = String.format("SELECT * FROM PRODUCTIONS WHERE FILMID = '%s'", FilmID);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String crewID = rs.getString("CREWID");
				Crew crew = getCrew(crewID);
			}
		
			
		}catch( Exception e) { e.printStackTrace(); }
		
		//add them to the prod object
		return prod;
		
	}
	
}
