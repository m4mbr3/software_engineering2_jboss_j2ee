<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page import="java.util.*"%>
<%@ page import="mph.entity.Student"%>
<%@ page import="mph.entity.Group"%>
<%@ page import="mph.entity.File"%>
<%@ page import="mph.entity.Mark"%>
<%@ page import="mph.entity.Professor"%>
<%@ page import="mph.entity.Deliverable"%>

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
					<a	onclick="javascript:document.getElementById('ind').submit();">Home
						</a>
				</form>
			</li>
			<%
				if (session.getAttribute("STUDENT") != null) {
			%>
			<li>
				<form method="post" id="logoutforum"
					action="ServletLogin?to=LogoutForm">
					<a
						onclick="javascript:document.getElementById('logoutforum').submit();">Logout
						<%
						out.print(((Student) session.getAttribute("STUDENT"))
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
			<h1>Group Page</h1>
		</header>
	</section>
	<div id="content">
		<div id="mainContent">
			<section>
				<%
					if (session.getAttribute("GROUP") != null) {
						Group g = (Group) session.getAttribute("GROUP");
						//Professor p = (Professor)session.getAttribute("PROF_GROUP");
						List<Student> st = (List<Student>) session
								.getAttribute("STUD_GROUP");
						List<Deliverable> ld = (List<Deliverable>) session
								.getAttribute("listdel");
						List<File> lf = (List<File>) session.getAttribute("listfil");
						List<Mark> lm = (List<Mark>) session.getAttribute("listma");
				%>
				<h4>
					Name Group :
					<%=g.getGroupName()%></h4>
					<%if(g.getGroupMark() != -1 ) {%>
					Project vote: <%=g.getGroupMark() %>
					<%} %>
				<br />
				<%
				int i=0;
				%>
				<h4>Student List</h4>
			<% 
				for(Student s : st)
					{
						i++;%>
						<h4>Student Name <%=i %>: <%=s.getLastName() %> <%=s.getFirstName() %></h4>
					<%}
				%><%
					
						for (Deliverable d : ld) {
							
				%>
				<script type="text/javascript"> 
				function fun()
				{
					document.getElementById("up").action += "&type="+document.getElementById("type_form").value;
					}
				
				</script>
				<form enctype="multipart/form-data" method="post"
					action="./ServletUpDown?to=upload&idDel=<%=d.getIdDeliverable() %>" id="up" onSubmit="fun();")>
				<h4>
					Deliverable:
					<%=d.getDeliverableName()%></h4>
					
					<%if(lm != null){
						for (Mark m : lm){
							Deliverable de = m.getDeliverable();
							if( de.getIdDeliverable() == d.getIdDeliverable() ){%>
								Deliverable vote: <%=m.getMark() %><br>
							<%}
						}
					}%>
					
			
					<select name="type_form" id="type_form">
							<option id="pdf" value="pdf" name="pdf">pdf</option>
							<option id="rar" value="rar" name="rar">rar</option>
							<option id="jpg" value="jpg" name="jpg">jpg</option>
					</select>
					<input type="file" name="mptest" />
					<input type="submit" value="submit" />
				</form>
				<%
					if(lf != null){
						for (File f : lf) {
							Deliverable deli = f.getDeliverable();
							if( deli.getIdDeliverable() == d.getIdDeliverable() ){%><br>
								<a
								href="./ServletUpDown?to=downloadSingle&idFile=<%=f.getIdFile()%>"
								class=label><%=f.getFileName()%></a>
								<%if(f.isLate() != 0) {%>
								File upload with a delay of <%=f.isLate() %> day
								<%} %>
								<br>
								<a
								href="./ServletUpDown?to=removeFile&idFile=<%=f.getIdFile()%>&idGroup=<%=g.getIdGroup() %>"
								class=label>Remove</a>
							<%}
						}%>
						<form method="post" id="getZip<%=d.getIdDeliverable() %>"  action="ServletUpDown?to=getZip&idGroup=<%=g.getIdGroup() %>&idDel=<%=d.getIdDeliverable()%>">
						<u><a onclick="javascript:document.getElementById('getZip<%=d.getIdDeliverable() %>').submit();">get ZipFile</a></u>
						</form>
					<% }
					
					
					
				
						}%>
				
				<br />
				<%
					} else {
				%>
				YOU HAVE TO GO TO THE INDEX PAGE!
				<%
					}
				%>
			</section>
		</div>
		<aside>
			<section>
				<header>
					<h3>External Groups</h3>
				</header>
				<ul>
					<% 
					if(session.getAttribute("listgroup")!= null)
					{
						for(Group g : (List<Group>) session.getAttribute("listgroup"))
						{%>
					<form method="post" id="groupext<%=g.getIdGroup() %>"
					action="ServletGroup?to=extGroup&idGroup=<%=g.getIdGroup()%>">
					<a onclick="javascript:document.getElementById('groupext<%=g.getIdGroup()%>').submit();"><%=g.getGroupName() %>	</a>
				</form>
						<% }
					}
					session.removeAttribute("listfil");
					%>
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
