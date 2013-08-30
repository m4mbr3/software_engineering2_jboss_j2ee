<%@ page import="mph.entity.Student"%>
<%@ page import="mph.entity.Project"%>
<%@ page import="mph.entity.Group" %>
<%@ page import="java.util.*" %>
<!doctype html>  
<html>  
<head>  
    <title>MPH-Manage Project Homework</title>  
    <link rel="stylesheet" type="text/css" href="css/prova.css"/>
</head>  
<body> 
	 <header>  
       <h1>Manage Project Homework</h1>  
   	 </header>   
    <nav>  
        <ul>   
        <li> 
        	<form method="post" id="ind" action="ServletLogin?to=nextPage">
			<a onclick="javascript:document.getElementById('ind').submit();">Home </a>
	        </form>
	   </li>  
        	<%if (session.getAttribute("STUDENT") != null){%>
        	<li> 
        	<form method="post" id="logoutforum" action="ServletLogin?to=LogoutForm">
			<a onclick="javascript:document.getElementById('logoutforum').submit();">Logout <% out.print(((Student)session.getAttribute("STUDENT")).getFirstName()); %> </a>
	        </form></li> 
	        <%}%> 
        </ul> 
    </nav>  
    <section id="intro">  
      <header>  
       <h1>Create Group Page</h1>  
   	 </header>  
    </section>  
   <div id="content">  
    <div id="mainContent">  
        <section>
        <br />
        <br />
       <% if(session.getAttribute("PROJECTS_LIST") != null){%>
       
        	<form id="FormGroup" method="post" action="ServletGroup?to=createGroup">
        	<label>Name of the Group</label> <input id="nameGroup" name="nameGroup" type="text">
        	<br />
        	<label>Choose the Project for your group</label>
        	<select name="idProj">
        	<%
        		for(Project p : (List<Project>)session.getAttribute("PROJECTS_LIST"))
        		{%>
        		<option id="<%=p.getIdProject()%>" name="<%=p.getIdProject()%>" value="<%=p.getIdProject() %>"><%=p.getProjectName() %></option>
        		<%}%>
        	</select>
        	<br />
        	<label>Choose the first Student to invite in: </label>
        	<select name="idStud1">	
        	<option id="-1" name="-1" value="-1">No Selected</option>
        	<%if(session.getAttribute("STUDENTS_LIST") != null){
        		for(Student s : (List<Student>)session.getAttribute("STUDENTS_LIST"))
        		{%>
    			    <option id="<%=s.getIdStudent() %>"	name="<%=s.getIdStudent() %>" value="<%=s.getIdStudent() %>"><%=s.getFirstName() %> <%= s.getLastName() %></option>	
        	<%}}%>
        	</select>
        	<br />
        	<label>Choose the second Student to invite in: </label>
        	<select name="idStud2">	
        	<option id="-1" name="-1" value="-1">No Selected</option>
        	<%if(session.getAttribute("STUDENTS_LIST") != null){
        	for(Student s : (List<Student>)session.getAttribute("STUDENTS_LIST"))
        		{%>
    			    <option id="<%=s.getIdStudent() %>"	name="<%=s.getIdStudent() %>" value="<%=s.getIdStudent() %>"><%=s.getFirstName() %> <%= s.getLastName() %></option>	
        	<%}}%>
        	</select>
        	<br />
        	<input type="Submit" value="Send">
        	</form>
        	<%}
        		else
        		{
        			out.println("<h3>Noone Project avaiable </h3>");%>
        			<form method="post" id="goback" action="ServletLogin?to=nextPage">
			<a onclick="javascript:document.getElementById('goback').submit();">Go Back!!! </a>
	        </form>
        			
        	<%	}%>
        	<br />
        	<br />
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