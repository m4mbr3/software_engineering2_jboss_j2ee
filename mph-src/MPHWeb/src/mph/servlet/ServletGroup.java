package mph.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import mph.entity.Mark;
import mph.entity.Project;
import mph.entity.Student;
import mph.remote.SessionGroupRemote;
import mph.remote.SessionInviteRemote;
import mph.remote.SessionPersonRemote;
import mph.remote.SessionProjectRemote;

/**
 * Servlet implementation class ServletGroup
 */
public class ServletGroup extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletGroup() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String to = request.getParameter("to");

		if (to.equals("createGroup"))
			this.createGroup(request, response);
		if (to.equals("accept"))
			this.accept(request, response);
		if (to.equals("remove"))
			this.remove(request, response);
		if (to.equals("toCreateGroup"))
			this.toCreateGroup(request, response);
		if (to.equals("createGroupPage"))
			this.createGroupPage(request, response);
		if (to.equals("groupPageProf"))
			this.groupPageProf(request, response);
		if (to.equals("assignVote"))
			this.assignVote(request, response);
		if (to.equals("assignIntermediateVote"))
			this.assignIntermediateVote(request, response);
		if (to.equals("extGroup"))
			this.extGroup(request, response);
	}

	private void extGroup(HttpServletRequest request,
			HttpServletResponse response) {
		long idGroup = Long.parseLong(request.getParameter("idGroup"));
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();

			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);

			Object ref2 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref2, SessionProjectRemote.class);

			Group g = sessionGro.getGroup(idGroup);
			List<Deliverable> listDel = sessionPro.getDeliverable(sessionPro
					.getGroupProject(idGroup));
			List<Student> listStud = sessionGro.getStudentOfGroup(g
					.getIdGroup());
			List<File> listFil = sessionPro.getGroupFile(idGroup);
			session.setAttribute("listfil", listFil);
			List<Mark> listMa = sessionGro.getGroupMark(g.getIdGroup());
			session.setAttribute("listma", listMa);
			session.setAttribute("GROUP", g);
			session.setAttribute("listdel", listDel);
			// session.setAttribute("listStudIn",sessionPer.getAllStudentsAvaiable(idGroup));
			session.setAttribute("STUD_GROUP", listStud);
			session.setAttribute("GROUP", g);
			redirect("/groupPageExt.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void createGroupPage(HttpServletRequest request,
			HttpServletResponse response) {

		long idGroup = Long.parseLong(request.getParameter("idGroup"));
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();

			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);

			Object ref2 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref2, SessionProjectRemote.class);

			Group g = sessionGro.getGroup(idGroup);
			List<Deliverable> listDel = sessionPro.getDeliverable(sessionPro
					.getGroupProject(idGroup));
			List<Student> listStud = sessionGro.getStudentOfGroup(g
					.getIdGroup());
			List<File> listFil = sessionPro.getGroupFile(idGroup);
			session.setAttribute("listfil", listFil);
			List<Group> listGroup = sessionGro
					.getGroupVisibleBy(g.getIdGroup());
			List<Mark> listMa = sessionGro.getGroupMark(g.getIdGroup());
			session.setAttribute("listma", listMa);
			session.setAttribute("listgroup", listGroup);
			session.setAttribute("GROUP", g);
			session.setAttribute("listdel", listDel);
			session.setAttribute("STUD_GROUP", listStud);
			redirect("/groupPage.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void groupPageProf(HttpServletRequest request,
			HttpServletResponse response) {

		long idGroup = Long.parseLong(request.getParameter("idGroup"));
		try {
			HttpSession session = request.getSession(true);
			Context jndiContext = new javax.naming.InitialContext();

			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);

			Object ref2 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref2, SessionProjectRemote.class);

			Group g = sessionGro.getGroup(idGroup);
			List<Deliverable> listDel = sessionPro.getDeliverable(sessionPro
					.getGroupProject(idGroup));
			List<Student> listStud = sessionGro.getStudentOfGroup(g
					.getIdGroup());
			List<File> listFil = sessionPro.getGroupFile(idGroup);
			List<Mark> listMa = sessionGro.getGroupMark(g.getIdGroup());
			session.setAttribute("listma", listMa);
			session.setAttribute("listfil", listFil);
			session.setAttribute("GROUP", g);
			session.setAttribute("listdel", listDel);
			session.setAttribute("STUD_GROUP", listStud);
			redirect("/groupPageProf.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createGroup(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		ArrayList<String> studSub = new ArrayList<String>();
		String nameGroup = request.getParameter("nameGroup");
		long idProj = Long.parseLong(request.getParameter("idProj"));
		Student idStud = ((Student) session.getAttribute("STUDENT"));
		studSub.add(new Long(idStud.getIdStudent()).toString());
		String idStud1 = request.getParameter("idStud1");
		String idStud2 = request.getParameter("idStud2");
		if (Integer.parseInt(idStud2) != -1) {
			studSub.add(idStud2);
		}
		if (Integer.parseInt(idStud1) != -1) {
			studSub.add(idStud1);
		}
		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			Group gr = sessionGro.getGroupByName(nameGroup);
			if (gr != null) {
				session.setAttribute("ERROR_GROUPNAME", true);
				redirect("/error.jsp", request, response);
			} else {
				boolean x = sessionGro.control(studSub, idProj);
				//if (x == false) {
					//this.toCreateGroup(request, response);
				//} else {
					Group g = sessionGro
							.createGroup(nameGroup, studSub, idProj);
					Object ref2 = jndiContext.lookup("SessionProjectRemote");
					SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
							.narrow(ref2, SessionProjectRemote.class);
					List<Deliverable> listDel = sessionPro
							.getDeliverable(sessionPro.getGroupProject(g
									.getIdGroup()));
					List<Student> listStud = sessionGro.getStudentOfGroup(g
							.getIdGroup());
					List<Group> listGroup = sessionGro.getGroupVisibleBy(g
							.getIdGroup());
					session.setAttribute("listgroup", listGroup);
					session.setAttribute("listdel", listDel);
					session.setAttribute("STUD_GROUP", listStud);
					session.setAttribute("GROUP", g);
					redirect("/groupPage.jsp", request, response);
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void toInviteGroup(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			long idGroup = ((Group) session.getAttribute("GROUP")).getIdGroup();
			long idStud = Long.parseLong(request.getParameter("idInvite"));
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionInviteRemote");
			SessionInviteRemote sessionInv = (SessionInviteRemote) PortableRemoteObject
					.narrow(ref1, SessionInviteRemote.class);
			sessionInv.sendInvite(idStud, idGroup);
			redirect("ServletGroup?to=createGroupPage", request, response);

		} catch (Exception e) {

		}
	}

	protected void toCreateGroup(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			long idStud = ((Student) session.getAttribute("STUDENT"))
					.getIdStudent();
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionProjectRemote");
			SessionProjectRemote sessionPro = (SessionProjectRemote) PortableRemoteObject
					.narrow(ref1, SessionProjectRemote.class);
			List<Project> listProj = sessionPro.getProjectList();
			session.setAttribute("PROJECTS_LIST", listProj);
			Object ref2 = jndiContext.lookup("SessionPersonRemote");
			SessionPersonRemote sessionPer = (SessionPersonRemote) PortableRemoteObject
					.narrow(ref2, SessionPersonRemote.class);
			List<Student> listStud = sessionPer.getAllStudents(idStud);
			session.setAttribute("STUDENTS_LIST", listStud);
			redirect("/toCreateGroup.jsp", request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void accept(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession(true);

		long idGroup = Long.parseLong(request.getParameter("idGroup"));
		long idStud = Long.parseLong(request.getParameter("idStud"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			sessionGro.accept(idGroup, idStud);
			redirect("/toGroupPageGandi.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void remove(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		long idGroup = Long.parseLong(request.getParameter("idGroup"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			sessionGro.removeGroup(idGroup);
			redirect("./ServletPerson?to=createAdminStudent", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void assignVote(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		long idGroup = Long.parseLong(request.getParameter("idGroup"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			sessionGro.assignVote(idGroup);
			this.groupPageProf(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void assignIntermediateVote(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();

		long idGroup = Long.parseLong(request.getParameter("idGroup"));
		Integer vote = Integer.parseInt(request.getParameter("vote"));
		long idDel = Long.parseLong(request.getParameter("idDel"));

		try {
			Context jndiContext = new javax.naming.InitialContext();
			Object ref1 = jndiContext.lookup("SessionGroupRemote");
			SessionGroupRemote sessionGro = (SessionGroupRemote) PortableRemoteObject
					.narrow(ref1, SessionGroupRemote.class);
			sessionGro.assignIntermediateVote(idGroup, idDel, vote);
			this.groupPageProf(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
