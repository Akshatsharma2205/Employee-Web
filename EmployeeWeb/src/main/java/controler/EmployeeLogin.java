package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employee;
import model.EmployeeDao;

import java.io.IOException;
import java.util.LinkedList;


public class EmployeeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeLogin() {
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().println("<h1>helll </h1>");
		int employeeId = Integer.parseInt(request.getParameter("employee-id"));
		String password = request.getParameter("employee-password");
		EmployeeDao db = new EmployeeDao();
		LinkedList<Employee> li = db.getAllEmployee();
		int flag = 0;
		for (Employee em : li) {
			if(employeeId == em.getEmployeeId()) {
				flag++;
				if(password.equals(em.getEmployeePassword())) {
					response.sendRedirect("employeeDasbord.html");
				}
			}
		}
		if(flag == 0) {
			response.sendRedirect("error.html");
		}
	}

}
