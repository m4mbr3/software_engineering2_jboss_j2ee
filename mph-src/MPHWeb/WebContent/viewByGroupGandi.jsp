<%@ page import="mph.entity.Project"%>
<%@ page import="mph.entity.Deliverable"%>
<%@ page import="mph.entity.File"%>
<%@ page import="mph.entity.Group"%>
<%@ page import="java.util.List"%>
<%@ page import="mph.entity.Professor"%>
<%@ page import="mph.entity.Project"%>
<%@ page import="java.util.List"%>
<!doctype html>
<html>
<head>
<title>MPH-Manage Project Homework</title>
<link rel="stylesheet" type="text/css" href="css/prova.css" />
</head>
<body>
	<header>
		<h1>Manage Project Homework</h1>
	</header>
	<nav>
		<ul>
			<li>
				<form method="post" id="ind" action="">
					<a onclick="history.back();">Home </a>
				</form>
			</li>
			<%
				if (session.getAttribute("PROFESSOR") != null) {
			%>
			<li>
				<form method="post" id="logoutforum"
					action="ServletLogin?to=LogoutForm">
					<a
						onclick="javascript:document.getElementById('logoutforum').submit();">Logout
						<%
						out.println(((Professor) session.getAttribute("PROFESSOR"))
									.getFirstName());
					%>
					</a>
				</form>
			</li>
			<%
				}
			%>
		</ul>
	</nav>
	<section id="intro">
		<header>
			<h1>View by Group</h1>
		</header>
	</section>
	<div id="content">
		<div id="mainContent">
			<br /> <br /> <br />
			<%
				Project p = (Project) session.getAttribute("project");

				List<Deliverable> dels = (List<Deliverable>) session
						.getAttribute("listdel");
				List<Group> groups = (List<Group>) session
						.getAttribute("listgroups");
				List<File> files = (List<File>) session.getAttribute("listfil");
			%>

			
			<h4 align="left">Nome Progetto:<form method="post" id="logoutforum<%=p.getIdProject()%>" action="ServletProject?to=toProjectPage&idProj=<%=p.getIdProject()%>">
	                        <u><a onclick="javascript:document.getElementById('logoutforum<%=p.getIdProject()%>').submit();"><%=p.getProjectName()%></a></u><br />
	                      		<hr /></form>
			
			<%
				if (groups != null) {
			%>

			<%
				for (Group g : groups) {
			%>
			<br />
			<form
				action="./ServletGroup?to=groupPageProf&idGroup=<%=g.getIdGroup()%>"
				method="post">
				<input type="submit" value=<%=g.getGroupName()%> />
			</form>
			<%
				if (dels != null) {
							for (Deliverable d : dels) {
			%>
			<br>
			<%=d.getDeliverableName()%>
			<br>
			<%
				if (files != null) {
									for (File f : files) {
										Deliverable del = f.getDeliverable();
										Group gr = f.getGroup();
										if (del.getIdDeliverable() == d
												.getIdDeliverable()
												&& gr.getIdGroup() == g
														.getIdGroup()) {
			%>

			<a
				href="./ServletUpDown?to=downloadSingle&idFile=<%=f.getIdFile()%>"
				class=label><%=f.getFileName()%></a>
				<%if(f.isLate() != 0)  {%>Ritardo di giorni: <%=f.isLate() %> <%} %>
				<br>
			<%
				}
									}%>
									<form method="post" id="getZip<%=g.getIdGroup() %>"  action="ServletUpDown?to=getZip&idGroup=<%=g.getIdGroup() %>">
									<u><a onclick="javascript:document.getElementById('getZip<%=g.getIdGroup() %>').submit();">get ZipFile by Group</a></u>
									</form>
							<%	}
			%>

			<%
				}
						}
					}
			%>
			<%
				} else {
			%>
			<p>There's no group for this project</p>
			<%
				}
				session.removeAttribute("listgroups");
				session.removeAttribute("listdel");
				session.removeAttribute("listfil");
			%>
			<br /> <br /> <br />
		</div>
		<aside></aside>
	</div>
	<footer>
		<h3>Software Engineering 2 Project</h3>
		<div>
			<section>
				<h5>Andrea Gandini</h5>
			</section>
			<section>
				<h5>Andrea Mambretti</h5>
			</section>
			<section>
				<h5>Guendalina Milano</h5>
			</section>
		</div>
	</footer>

</body>
</html>
