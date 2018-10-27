package ToDoList;

import java.io.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ToDoList.ToDo;
import ToDoList.User;

public class MySqlConnector {
	
	private Connection connection;
	
	private static final String url = "jdbc:mysql://localhost:3306/jee_project_db";
	private static final String user = "root";
	private static final String password = "admin";
	
	
	public MySqlConnector() throws ClassNotFoundException, SQLException{
		super();
		
		//load jdbc driver
		Class.forName("com.mysql.jdbc.Driver");
	}
	
	public void addUser(String username, String password, User.Role role) throws SQLException {
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		int isProf = role == User.Role.Instructor ? 1 : 0;
		
		String myQuery = "INSERT INTO user (username, password, isProf) "
				+ "VALUES ('" + 
				username +
				"','" +
				password +
				"','" +
				isProf +
				 "');";
		
		
		if( query.executeUpdate(myQuery) > 0) System.out.println("Successfully added todo");
		query.close();
		conn.close();
	}
	public void addToDo(String description) throws SQLException{
		
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "INSERT INTO todo (description) VALUES ('" + description + "');";
		if( query.executeUpdate(myQuery) > 1) System.out.println("Successfully added todo");
		
		query.close();
		conn.close();
	}

	public void updateToDo(ToDo todo) throws SQLException{
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "UPDATE todo SET description = '" + todo.getDescription() + "' WHERE idtodo = " + todo.getId() +";";
		
		if(query.executeUpdate(myQuery) > 0) System.out.println("Successfully updated todo");

		query.close();
		conn.close();
	}
	
	public void removeToDo(ToDo todo) throws SQLException{
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "DELETE FROM todo WHERE idtodo = " + todo.getId() +";";
		
		if(query.executeUpdate(myQuery) > 0) System.out.println("Successfully removed todo");

		query.close();
		conn.close();
	}
	
	public List<ToDo> fetchToDo() throws SQLException {
		ArrayList<ToDo> listResult = new ArrayList<ToDo>();
		
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "SELECT idtodo, description FROM todo;";
		ResultSet response = query.executeQuery(myQuery);
		
		while(response.next()) {
			ToDo newToDo = new ToDo(response.getInt("idtodo"),
									response.getString("description"));
			
			listResult.add(newToDo);
		}
		
		response.close();
		query.close();
		conn.close();
		
		return listResult;
	}
	
	public boolean checkUser(User userToCheck) throws SQLException{
		boolean result = false;
		
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "SELECT * FROM user WHERE username = '" + userToCheck.getUsername() + "';";
		
		if(query.executeQuery(myQuery).next()) System.out.println("Username registered"); result = true;

		query.close();
		conn.close();
		
		return result;
	}
	
	public boolean loginUser(User userToLogin) throws SQLException{
		boolean result = false;
		
		if(!checkUser(userToLogin)) return false;
		
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "SELECT * FROM user WHERE username = '" + userToLogin.getUsername() + "';";
		
		ResultSet response = query.executeQuery(myQuery);
		
		if(response.next()) {
			if(response.getString("password").equals(userToLogin.getPassword())) {
				 result = true;
			}
			else System.out.println("Wrong password");
		}
		
		
		
		response.close();
		query.close();
		conn.close();
		
		return result;
	}
	
	public User.Role checkUserRole(User userToCheck) throws SQLException{
		User.Role role = null;
		
		if(!checkUser(userToCheck)) return null;
		
		Connection conn = DriverManager.getConnection( url, user, password );
		
		Statement query = conn.createStatement();
		
		String myQuery = "SELECT isProf FROM user WHERE username = '" + userToCheck.getUsername() + "';";
		
		ResultSet response = query.executeQuery(myQuery);
		
		if(response.next()) {
			role = response.getInt("isProf") == 0 ? User.Role.Student : User.Role.Instructor;
		}
		
		response.close();
		query.close();
		conn.close();
		
		return role;
	}
	
}