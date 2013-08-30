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
				<br>
				<form
					action="./ServletGroup?to=assignVote&idGroup=<%=g.getIdGroup()%>"
					method="post">
				<input type="submit" value="Assign Vote" />
			</form>
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
				<form method="post"
					action="./ServletGroup?to=assignIntermediateVote&idDel=<%=d.getIdDeliverable() %>&idGroup=<%=g.getIdGroup()%>">
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
					
<select name="vote">
	<option value="1">1</option>
  	<option value="2">2</option>
  	<option value="3">3</option>
  	<option value="4">4</option>
  	<option value="5">5</option>
  	<option value="6">6</option>
  	<option value="7">7</option>
  	<option value="8">8</option>
  	<option value="9">9</option>
  	<option value="10">10</option>
  	<option value="11">11</option>
  	<option value="12">12</option>
  	<option value="13">13</option>
  	<option value="14">14</option>
  	<option value="15">15</option>
  	<option value="16">16</option>
  	<option value="17">17</option>
  	<option value="18">18</option>
  	<option value="19">19</option>
  	<option value="20">20</option>
  	<option value="21">21</option>
  	<option value="22">22</option>
  	<option value="23">23</option>
  	<option value="24">24</option>
  	<option value="25">25</option>
  	<option value="26">26</option>
  	<option value="27">27</option>
  	<option value="28">28</option>
  	<option value="29">29</option>
  	<option value="30">30</option>
</select>
					
					<input type="submit" value="submit" />
				</form>
				<%
					
					if(lf != null){
						for (File f : lf) {
							Deliverable deli = f.getDeliverable();
							if( deli.getIdDeliverable() == d.getIdDeliverable() ){%><br>
								<a href="./ServletUpDown?to=downloadSingle&idFile=<%=f.getIdFile()%>" class="label"><%=f.getFileName()%></a>
								
								<%if(f.isLate() != 0) {%>
								File upload with a delay of <%=f.isLate() %> day
								<%} %>
								<br>
							<%}
							
						}
						//point where put the information about things related to download zipfile
					%>
		<form method="post" id="getZip<%=d.getIdDeliverable() %>"  action="ServletUpDown?to=getZip&idGroup=<%=g.getIdGroup() %>&idDel=<%=d.getIdDeliverable()%>">
				<u><a onclick="javascript:document.getElementById('getZip<%=d.getIdDeliverable() %>').submit();">get ZipFile</a></u>
				</form>
				<%

					}
				
		
				
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
