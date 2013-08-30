package mph.servlet;

import java.io.IOException;

import mph.entity.Group;
import mph.entity.Professor;
import mph.entity.Project;
import mph.entity.Student;
import java.util.*;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mph.remote.SessionGroupRemote;
import mph.remote.SessionInviteRemote;
import mph.remote.SessionProjectRemote;

/**
 * Servlet implementation class ServletPerson
 */
public class ServletPerson extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPerson() {
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
		if (to.equals("visible"))
			this.visible(request, response);
		if (to.equals("createAdminProfessor"))
			this.createAdminProfessor(request, response);
		if (to.equals("createAdminStudent"))
			this.createAdminStudent(request, response);
		if (to.equals("accept"))
			this.accept_group(request, response);
		if (to.equals("changeVisibility"))
			this.changeVisibility(request, response);

	}

	protected void createAdminProfessor(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
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
	}

	protected void createAdminStudent(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGroup = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			Object ref2 = jndiContext.lookup("SessionInviteRemote");
			SessionInviteRemote sessionInvite = (SessionInviteRemote) PortableRemoteObject
					.narrow(ref2, SessionInviteRemote.class);
			List<Group> groups = sessionGroup
					.getStudentGroup(((Student) session.getAttribute("STUDENT"))
							.getIdStudent());
			List<Group> invite_groups = sessionInvite
					.getGroupInvite(((Student) session.getAttribute("STUDENT"))
							.getIdStudent());
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
	}

	protected void accept_group(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGroup = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			sessionGroup.accept(
					Long.parseLong(request.getParameter("idGroup")),
					((Student) session.getAttribute("STUDENT")).getIdStudent());
			redirect("/ServletPerson?to=createAdminStudent", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void visible(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGroup = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);

			List<Group> groups = sessionGroup.getAllGroup();
			session.setAttribute("ALL_Groups", groups);
			redirect("/group_groups.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void changeVisibility(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGroup = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			if (sessionGroup.createVisibility(
					Long.parseLong(request.getParameter("idGroup1")),
					Long.parseLong(request.getParameter("idGroup2"))) == true) {
				redirect("ServletLogin?to=nextPage", request, response);
			} else {
				redirect("ServletPerson?to=visible", request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
