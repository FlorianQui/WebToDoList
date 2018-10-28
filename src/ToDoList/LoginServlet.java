package ToDoList;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ToDoList.MySqlConnector;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MySqlConnector mySqlConnector;
	
	private boolean loginfailed;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
        loginfailed = true;
        
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
		
		showLoginPage(request, response);
	}
	
	public void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginPage.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("btn_submit");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		try {
			if( !this.mySqlConnector.loginUser(new User(username, password, null))) {
				System.out.println("Log in failed");
				
				loginfailed = true;
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/LoginPage.jsp");
				dispatcher.forward(request, response);
			}
			else {
				User.Role role = mySqlConnector.checkUserRole(new User(username, password, null));
				
				session.setAttribute("username", username);
				session.setAttribute("role", role.toString());
				
				Cookie c_uname = new Cookie("username", username);
				Cookie c_pass = new Cookie("password", password);
				Cookie c_role = new Cookie("role", role.toString());
				
				c_uname.setMaxAge(3600);
				c_pass.setMaxAge(3600);
				c_role.setMaxAge(3600);
				
				response.addCookie(c_uname);
				response.addCookie(c_pass);
				response.addCookie(c_role);
				
				loginfailed = false;
				
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("role", role);
				
				response.sendRedirect("/WebToDoList/todolist");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
}
