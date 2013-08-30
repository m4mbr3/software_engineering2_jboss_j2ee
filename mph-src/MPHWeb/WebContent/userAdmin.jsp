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
       <h1>Student Admin Page</h1>  
   	 </header>  
    </section>  
   <div id="content">  
    <div id="mainContent">  
        <section>
        <h3>Student Information</h3>
         		<table style="padding=10px; align=left;">
         		<td>
         		<tr>FirstName </tr>
         		<tr> <%out.println(((Student)session.getAttribute("STUDENT")).getFirstName()); %> </tr>
         		<br /> 
         		</td>
         		<td>
         		<tr>LastName </tr>
         		<tr> <%out.println(((Student)session.getAttribute("STUDENT")).getLastName()); %> </tr>
         		</td>
         		
         		</table>
         		
         		<% 
        	if (session.getAttribute("GROUPS") != null)
        	{	
        		if(((List<Group>)session.getAttribute("GROUPS")).size()>0)
        		{
        			
        			int i;
        			out.println("<hr />");
        			for(Group g : (List<Group>) session.getAttribute("GROUPS"))
    	        	{%>
    	        		<h4 align="left"><form method="post" id="log<%=g.getIdGroup()%>" action="ServletGroup?to=createGroupPage&idGroup=<%=g.getIdGroup()%>">
    	        		Group: <u><a onclick="javascript:document.getElementById('log<%=g.getIdGroup()%>').submit();"><%=g.getGroupName()%></a></u><br />
    	        		</form></h4><br />
    	        		<hr />
    	        	<%}
        		}
        		else
        		{
        			out.println("YOU DON'T BELONG TO ANYGROUPS");
        		}
        	
        	}
        	else 
        	{
        		out.println("YOU DON'T BELONG TO ANYGROUPS");
        	}
        	
        	
        	
        	%>       	
           
        </section>  
    </div>  
    <aside>  
       <section>
				<header>
					<h3>My groups request</h3>
				</header>
				<ul>
				<%
					if(session.getAttribute("REQUESTS") != null)
					{
					List<Group> groups = (List<Group>)session.getAttribute("REQUESTS");
						for (Group g : groups){
					%>
						<br />
						Name of Group: <%= g.getGroupName() %>
						<form method="post" id="accept<%=g.getIdGroup()%>" action="ServletPerson?to=accept&idGroup=<%=g.getIdGroup()%>" >
						<u></u><a  onclick="javascript:document.getElementById('accept<%=g.getIdGroup()%>').submit();"><%=g.getGroupName() %></a></u>
						<hr />
						</form> 	
					<%}
						}
					else{%>
						<li>No invite for you sorry</li>
					<%}
				%>
				</ul>
			</section>
			<section>
				<header>
					<h3>What could you do?</h3>
				</header>
				<ul>
					<li>
					<form method="post" id="createGroup" action="ServletGroup?to=toCreateGroup">
					<a onclick="javascript:document.getElementById('createGroup').submit();">Create a new Group </a>
	       			</form>
					</li>
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