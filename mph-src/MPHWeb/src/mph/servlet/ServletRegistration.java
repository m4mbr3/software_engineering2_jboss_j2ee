package mph.servlet;

import java.io.IOException;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mph.remote.SessionPersonRemote;
import mph.remote.SessionRegistrationRemote;

/**
 * Servlet implementation class ServletRegistration
 */
public class ServletRegistration extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String to = (String) request.getParameter("to");
		if (to.equals("register"))
			this.register(request, response);
		if (to.equals("nextPage"))
			this.nextPage(request, response);
	}

	protected void register(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String type = request.getParameter("type");

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionRegistrationRemote");
			SessionRegistrationRemote sessionRegistrationRemote = (SessionRegistrationRemote) PortableRemoteObject
					.narrow(ref1, SessionRegistrationRemote.class);
			if (type.equals("Student")) {
				if (sessionRegistrationRemote.studentRegistration(firstname,
						lastname, username, password) == 0) {
					HttpSession session = request.getSession(true);
					session.setAttribute("ERROR", new String("FALSE"));
				} else {
					HttpSession session = request.getSession(true);
					session.setAttribute("REGISTERED", new String("TRUE"));
				}
				redirect("/register.jsp", request, response);
			} else {
				boolean no = false;
				if (sessionRegistrationRemote.professorRegistration(firstname,
						lastname, username, password, no) == 0) {
					HttpSession session = request.getSession(true);
					session.setAttribute("ERROR", new String("FALSE"));
				} else {
					HttpSession session = request.getSession(true);
					session.setAttribute("REGISTERED", new String("TRUE"));
				}
				redirect("/register.jsp", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void nextPage(HttpServletRequest request,
			HttpServletResponse response) {
		redirect("index.jsp", request, response);
	}
}
