package mph.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mph.entity.Deliverable;
import mph.entity.File;
import mph.entity.Group;
import mph.entity.Professor;
import mph.entity.Project;
import mph.remote.SessionProjectRemote;

/**
 * Servlet implementation class ServletProject
 */
public class ServletProject extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletProject() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String to = (String) request.getParameter("to");
		if (to.equals("createProject"))
			this.createProject(request, response);
		if (to.equals("toProjectPage"))
			this.toProjectPage(request, response);
		if (to.equals("viewallgroup"))
			this.viewAllGroup(request, response);
		if (to.equals("viewbygroup"))
			this.viewByGroup(request, response);
		if (to.equals("viewbydeliverable"))
			this.viewByDeliverable(request, response);
		if (to.equals("removeProject"))
			this.removeProject(request, response);
		if (to.equals("toCreateProject"))
			this.toCreateProject(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void createProject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		long idProf = ((Professor) session.getAttribute("PROFESSOR"))
				.getIdProfessor();
		String nameProj = request.getParameter("nameProj");
		String deadProj = request.getParameter("deadlineProj");
		deadProj = deadProj + " 00:00:00";
		String text = request.getParameter("description");
		Integer x = Integer.parseInt(request.getParameter("numDel"));
		boolean y = false;

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);

			
			Date deadlineProject = null;
			try{
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				deadlineProject = format.parse(deadProj);
			} catch (Exception e) {throw e;}
			ArrayList<String> l = new ArrayList<String>();
			// /
			for (int j = 1; j <= x; j++) {
				String dd = request.getParameter("deadlineDel" + j);
				dd = dd + " 00:00:00";
				l.add(dd);
			}
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			fmt.setLenient(false);
			if (l.size() != 1) {
				for (int k = 0; k < l.size(); k++) {
					Date d1;
					Date d2;
					try{
						d1 = fmt.parse(l.get(k));
						d2 = fmt.parse(l.get(k + 1));
					} catch (Exception e){throw e;}
						long millisDiff = d2.getTime() - d1.getTime();
						if (millisDiff > 0) {
							y = true;
						}
				}
			}
			Date d3;
			Date d4;
			try{
			d3 = fmt.parse(l.get(l.size() - 1));
			d4 = fmt.parse(deadProj);
			long millisDiff2 = d3.getTime() - d4.getTime();
			if (millisDiff2 > 0) {
				y = true;
			}
			} catch(Exception e){throw e;}
			} catch(Exception e){
				y = false;
			}
			// /
			if (y == true) {
				redirect("/toCreateProject.jsp", request, response);
			} else {
				Project p = sessionProj.createProject(idProf, nameProj,
						deadlineProject, text);

				for (int i = 1; i <= x; i++) {
					String nameDel = request.getParameter("nameDel" + i);
					String deadDel = request.getParameter("deadlineDel" + i);

					Date deadlineDel = null;
					SimpleDateFormat format2 = new SimpleDateFormat(
							"dd/MM/yyyy");
					try {
						deadlineDel = format2.parse(deadDel);
					} catch (Exception e) {
					}

					sessionProj.createDeliverable(nameDel, deadlineDel,
							p.getIdProject());
				}
				List<Deliverable> listDel = sessionProj.getDeliverable(p
						.getIdProject());
				session.setAttribute("project", p);
				session.setAttribute("idProj", p.getIdProject());
				session.setAttribute("listdel", listDel);
				session.setAttribute("description", text);

				redirect("/projectPage.jsp", request, response);
			}
		} catch (IllegalArgumentException il) {
			request.setAttribute("ERROR_DATE", "true");
			redirect("/toCreateProject.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void toProjectPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		long idProj = Long.parseLong(request.getParameter("idProj"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);
			List<Deliverable> listDel = sessionProj.getDeliverable(idProj);
			Project p = sessionProj.getProject(idProj);
			List<Group> listGroup = sessionProj.getProjectGroups(idProj);
			session.setAttribute("idProj", idProj);
			session.setAttribute("listgroups", listGroup);
			session.setAttribute("project", p);
			session.setAttribute("listdel", listDel);
			session.setAttribute("description", p.getDescription());
			redirect("/projectPage.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void viewByGroup(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		long idProj = (Long) (session.getAttribute("idProj"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);
			Project p = sessionProj.getProject(idProj);
			List<Group> listGroup = sessionProj.getProjectGroups(idProj);
			List<Deliverable> listDel = sessionProj.getDeliverable(idProj);
			List<File> listFil = sessionProj.getProjectFiles(idProj);
			session.setAttribute("project", p);
			session.setAttribute("listgroups", listGroup);
			session.setAttribute("listdel", listDel);
			session.setAttribute("listfil", listFil);
			redirect("/viewByGroupGandi.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void viewByDeliverable(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		long idProj = (Long) (session.getAttribute("idProj"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);
			Project p = sessionProj.getProject(idProj);
			List<Group> listGroup = sessionProj.getProjectGroups(idProj);
			List<Deliverable> listDel = sessionProj.getDeliverable(idProj);
			List<File> listFil = sessionProj.getProjectFiles(idProj);
			session.setAttribute("project", p);
			session.setAttribute("listgroups", listGroup);
			session.setAttribute("listdel", listDel);
			session.setAttribute("listfil", listFil);
			redirect("/viewByDeliverable.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void viewAllGroup(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		long idProf = ((Professor) session.getAttribute("PROFESSOR"))
				.getIdProfessor();

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);

			List<Project> listProj = sessionProj.getProjectList(idProf);
			List<Group> listGroup = sessionProj.getProfessorGroups(idProf);

			session.setAttribute("listgroups", listGroup);
			session.setAttribute("listproj", listProj);
			redirect("viewAllGroup.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void removeProject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		long idProj = Long.parseLong(request.getParameter("idProj"));
		long idProf = ((Professor) session.getAttribute("PROFESSOR"))
				.getIdProfessor();

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionProj = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);
			sessionProj.removeProject(idProj);
			session.setAttribute("YEAH", "cippo");
			redirect("./ServletLogin?to=nextPage", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void toCreateProject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		redirect("/toCreateProject.jsp", request, response);
	}
}
