package mph.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mph.entity.Group;
import mph.entity.Professor;
import mph.entity.Project;
import mph.entity.Student;
import mph.remote.SessionGroupRemote;
import mph.remote.SessionInviteRemote;
import mph.remote.SessionPersonRemote;
import mph.remote.SessionProjectRemote;

/**
 * Servlet implementation class ServletLogin
 */
public class ServletLogin extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
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
		if (to.equals("LoginForm"))
			this.login(request, response);
		if (to.equals("LogoutForm"))
			this.logout(request, response);
		if (to.equals("register"))
			this.register(request, response);
		if (to.equals("nextPage"))
			this.nextPage(request, response);

	}

	protected void login(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("ServletLogin");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("I valori inseriti sono " + username + " "
				+ password);

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionPersonRemote");
			SessionPersonRemote sessionPersonRemote = (SessionPersonRemote) PortableRemoteObject
					.narrow(ref1, SessionPersonRemote.class);
			Student s;
			Professor p;
			s = sessionPersonRemote.loginStudent(username, password);
			p = sessionPersonRemote.loginProfessor(username, password);
			if (s != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("STUDENT", s);
				redirect("/ServletPerson?to=createAdminStudent", request,
						response);

			} else if ((p != null) && (p.isProfessor())) {
				HttpSession session = request.getSession(true);
				session.setAttribute("PROFESSOR", p);
				redirect("/ServletPerson?to=createAdminProfessor", request,
						response);
				/*
				 * ServletPerson person = new ServletPerson();
				 * person.createAdminProfessor(request, response);
				 */
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("ERRORLOGIN", "TRUE");
				redirect("/index.jsp", request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void logout(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.removeAttribute("STUDENT");
		session.removeAttribute("PROFESSOR");
		session.removeAttribute("IDPERSON");
		session.removeAttribute("ERRORLOGIN");
		redirect("/index.jsp", request, response);
	}

	protected void register(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String idPerson = (String) session.getAttribute("IDPERSON");
		System.out.println("Register");
		if (idPerson != null) {
			redirect("/index.jsp", request, response);
		} else {
			redirect("/register.jsp", request, response);
		}

	}

	protected void nextPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(true);

		if (session.getAttribute("PROFESSOR") != null) {
			try {
				Context jndiContext = new javax.naming.InitialContext();
				Object ref1 = jndiContext.lookup("SessionProjectRemote");
				SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
						.narrow(ref1, SessionProjectRemote.class);
				List<Project> projects = sessionProj
						.getProjectList(((Professor) session
								.getAttribute("PROFESSOR")).getIdProfessor());
				if (projects != null) {
					session.removeAttribute("PROJECTS");
					session.setAttribute("PROJECTS", projects);
					redirect("/professorAdmin.jsp", request, response);
				} else {
					redirect("/index.jsp", request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (session.getAttribute("STUDENT") != null) {
			try {
				Context jndiContext = new javax.naming.InitialContext();
				Object ref1 = jndiContext.lookup("SessionGroupRemote");
				SessionGroupRemote sessionGroup = (SessionGroupRemote) PortableRemoteObject
						.narrow(ref1, SessionGroupRemote.class);
				Object ref2 = jndiContext.lookup("SessionInviteRemote");
				SessionInviteRemote sessionInvite = (SessionInviteRemote) PortableRemoteObject
						.narrow(ref2, SessionInviteRemote.class);
				List<Group> groups = sessionGroup
						.getStudentGroup(((Student) session
								.getAttribute("STUDENT")).getIdStudent());
				List<Group> invite_groups = sessionInvite
						.getGroupInvite(((Student) session
								.getAttribute("STUDENT")).getIdStudent());
				if (groups != null) {
					session.removeAttribute("GROUPS");
					session.setAttribute("GROUPS", groups);
					session.setAttribute("REQUESTS", invite_groups);
					redirect("/userAdmin.jsp", request, response);
				} else {
					redirect("/index.jsp", request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			redirect("/index.jsp", request, response);
		}
	}
}
