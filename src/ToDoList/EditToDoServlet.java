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
 * Servlet implementation class EditToDoServlet
 */
@WebServlet("/edit")
public class EditToDoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MySqlConnector mySqlConnector;
	
	private ToDo todo;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditToDoServlet() {
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
		
		System.out.println(session.getAttribute("role").toString());
		
		int id = Integer.parseInt(request.getParameter("toDoId"));
		
		try {
			ToDo tmp = mySqlConnector.getToDo(id);
			if( tmp != null) {
				todo.setId(tmp.getId());
				todo.setDescription(tmp.getDescription());
				
				request.setAttribute("todo", todo);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/EditToDo.jsp");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String desc = request.getParameter("description");
		
		todo.setDescription(desc);
		
		try {
			mySqlConnector.updateToDo(todo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			response.sendRedirect("/WebToDoList/todolist");
		}
	}

}
