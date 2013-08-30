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
		<br />
		<br />
		<br />
			<%
			List<Group> groups = (List<Group>) session
			.getAttribute("listgroups");
	List<Project> projs = (List<Project>) session.getAttribute("listproj");
	if(projs != null){
	for(Project p : projs){%>
		<%=p.getProjectName() %>
		<%
		if(groups != null){
		for(Group g : groups){
			Project pr = g.getProject();
			if(pr.getIdProject() == p.getIdProject()){%> 
			<h4 align="left"><form method="post" id="log<%=g.getIdGroup()%>" action="ServletGroup?to=groupPageProf&idGroup=<%=g.getIdGroup()%>">
        		Group: <u><a onclick="javascript:document.getElementById('log<%=g.getIdGroup()%>').submit();"><%=g.getGroupName()%></a></u><br />
        		</form></h4><br />
        		<hr />
	<%}}}}}
				session.removeAttribute("listgroups");
				session.removeAttribute("listproj");
			%>
<br />
		<br />
		<br />
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