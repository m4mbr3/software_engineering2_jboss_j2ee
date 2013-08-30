<%@ page import="mph.entity.Professor"%>
<%@ page import="mph.entity.Deliverable"%>
<%@ page import="mph.entity.Group"%>
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
				<form method="post" id="ind"
					action="ServletLogin?to=nextPage">
					<a
						onclick="javascript:document.getElementById('ind').submit();">Home
					</a>
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
			<h1>Project Page</h1>
		</header>
	</section>
	<div id="content">
		<div id="mainContent">
			<br />
			<br />
			<form>
					
					<%
					Project p = (Project) session.getAttribute("project");

					List<Deliverable> dels = (List<Deliverable>) session
							.getAttribute("listdel");
					List<Group> groups = (List<Group>) session
							.getAttribute("listgroups");
					%>
					Nome Progetto:
					<%=p.getProjectName()%>
					<br />
					<%
					StringBuffer text = new StringBuffer((String)session.getAttribute("description"));
					
			        int loc = (new String(text)).indexOf('\n');
			        while(loc > 0){
			            text.replace(loc, loc+1, "<br>");
			            loc = (new String(text)).indexOf('\n');
			       }
			        
			       out.println("Description: " + text); 
				 	out.println("<br />");
					
						if (dels != null) {
					%>
				
				
					Deliverable : <select name="scelta">

						<%
							for (Deliverable d : dels) {
						%>

						<option value=<%=d.getIdDeliverable()%>><%=d.getDeliverableName()%></option>

						<%
							}
						%>

					</select>
					<br />
					<%
						} else {
					%>
					There aren't deliverable for this project

					<%
						}
						session.removeAttribute("listdel");
					%>

					<%
						if (groups != null) {
							for (Group g : groups) {
					%>

					<option value=<%=g.getIdGroup()%>><%=g.getGroupName()%></option>

					<%
						}
					%>

					</select>
					<br />
					<br />
					<%
						} else {
					%>
					There aren't groups for this project

					<%
						}
						session.removeAttribute("listgroups");
					%>
				
				
			</form>
			<form action="ServletProject?to=viewbygroup" method="post">
				<input type="submit" value="to View By Group" />
			</form>
			<br />
			<form action="ServletProject?to=viewbydeliverable" method="post">
				<input type="submit" value="to View By Deliverable" />
			</form>
			<br />

		</div>
		<aside>
			
		</aside>
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
