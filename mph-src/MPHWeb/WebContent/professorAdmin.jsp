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
			<h1>Professor Admin Page</h1>
		</header>
	</section>
	<div id="content">
		<div id="mainContent">
			<section>
				<%
					if (session.getAttribute("PROJECTS") != null) {
						out.println("<hr />");
	                    for(Project p : (List<Project>) session.getAttribute("PROJECTS"))
	                    {%>
	                        <h4 align="left"><form method="post" id="logoutforum<%=p.getIdProject()%>" action="ServletProject?to=toProjectPage&idProj=<%=p.getIdProject()%>">
	                        <u><a onclick="javascript:document.getElementById('logoutforum<%=p.getIdProject()%>').submit();"><%=p.getProjectName()%></a></u><br />
	                        
	                        
	                        
	                            Evaluated = <% if(p.isMark() ==false) out.println("NO");
	                                    else out.println("YES");%><br />
	                            DeadLine = <%if(p.getDeadLine() != null) out.println(p.getDeadLine().toString());
	                            else out.println("NO DEADLINE SETTED");%>
	                            </form></h4><br />
	                            <hr />
						<%}
					} else {
						out.println("NO PROJECTS CREATED ");
					}
				%>

			</section>

			<form action="ServletProject?to=viewallgroup" method="post">
				<input type="submit" value="to View By Group" />
			</form>

		</div>
		<aside>
			<section>
				<header>
					<h3>What could you do?</h3>
				</header>
				<ul>
					<li><a href="ServletProject?to=toCreateProject">Create a
							new project</a></li>
					<li><form method="post" id="inv"
					action="ServletPerson?to=visible">
					<a
						onclick="javascript:document.getElementById('inv').submit();">Change the group visibility
					</a></li>
				</ul>
			</section>
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
