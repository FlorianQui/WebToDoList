package ToDoList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ToDoListServlet
 */
@WebServlet("/todolist")
public class ToDoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MySqlConnector mySqlConnector;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToDoListServlet() {
        super();
        
        try {
			mySqlConnector = new MySqlConnector();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String u_name = "Username";
		String u_pass = "Password";
		String u_role = "Role";

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					u_name = cookie.getValue();
					System.out.println(u_name);
				}
				else if (cookie.getName().equals("password")) {
					u_pass = cookie.getValue();
					System.out.println(u_pass);
				}
				else if (cookie.getName().equals("role")) {
					u_role = cookie.getValue();
					System.out.println(u_role);
				}
			}
		}
		
		request.setAttribute("u_name", u_name);
		request.setAttribute("u_pass", u_pass);
		request.setAttribute("u_role", u_role);
		ArrayList<ToDo> toDoArrayList = new ArrayList<ToDo>();
		
		try {
			toDoArrayList = mySqlConnector.fetchToDo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("todo_list", toDoArrayList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ToDoListPage.jsp");
		dispatcher.forward(request, response);
	}
	
	public void test() {
		System.out.println("test ok");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
