<%@ page import="mph.entity.*" %>
<%@ page import="java.util.*"%>
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
        <ul><li>
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
       <h1>Visibility Page</h1>  
   	 </header>  
    </section>  
   <div id="content">  
    <div id="mainContent">  
        <section>  
        	<br />
        	<br />
        	Select Group to enable
        	<form method="post" action="ServletPerson?to=changeVisibility"> 
        	<select name="idGroup1">
        	<% 
        		for (Group g : ( List<Group>) session.getAttribute("ALL_Groups")){
					        			
        			
        	%>
        		<option id="<%=g.getIdGroup()%>" name="<%=g.getIdGroup()%>" value="<%=g.getIdGroup()%>"><%=g.getGroupName()%></option>
        	<%
        			
        		}

        	%>
        	</select>
        	<br />
        	that can see this group
        	<br />
        	<select name="idGroup2">
        	<% 
        		for (Group g : ( List<Group>) session.getAttribute("ALL_Groups")){
					        			
        			
        	%>
        		<option id="<%=g.getIdGroup()%>" name="<%=g.getIdGroup()%>" value="<%=g.getIdGroup()%>"><%=g.getGroupName()%></option>
        	<%
        			
        		}
			
        	%>
        
        	</select>
			<br />        	       	
        	<input type="submit" value="Send"> 
        	</form>
        	<br />
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