package ToDoList;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveToDoServlet
 */
@WebServlet("/remove")
public class RemoveToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private MySqlConnector mySqlConnector;
	
	private ToDo todo;
	
    public RemoveToDoServlet() {
        super();
        
        todo = new ToDo(0, null);
        
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
		HttpSession session = request.getSession();
		
		int id = Integer.parseInt(request.getParameter("toDoId"));
		
		try {
			ToDo tmp = mySqlConnector.getToDo(id);
			if( tmp != null) {
				todo.setId(id);
				
				mySqlConnector.removeToDo(todo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			response.sendRedirect("/WebToDoList/todolist");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
