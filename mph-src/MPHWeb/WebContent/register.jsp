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
        	<li><a href="index.jsp">Home</a></li>  
        	<%if (session.getAttribute("IDPERSON") != null){%>
        	<li> 
        	<form method="post" id="logoutforum" action="ServletLogin?to=LogoutForm">
			<a onclick="javascript:document.getElementById('logoutforum').submit();">Logout </a>
	        </form></li> 
	        <%}%>
        </ul> 
    </nav>  
    <section id="intro">  
      <header>  
       <h1>Registration Page</h1>  
   	 </header>  
    </section>  
   <div id="content">  
    <div id="mainContent">  
        <section>          	
        	<%
        	if(session.getAttribute("IDPERSON")==null){
				
				if(session.getAttribute("REGISTERED")==null && session.getAttribute("ERROR") == null)
								{%> 
							
				        	<h3>REGISTRATION USER</h3>
				        	<form method="post" action="ServletRegistration?to=register">
				        		<label>Firstname: </label><input type="firstname" id="firstname" name="firstname"></input></label><br />
				        		<label>Lastname: </label><input type="lastname" id="lastname" name="lastname"></input></label><br />
				        		<label>Username: </label><input type="username" id="username" name="username"></input></label><br />
								<label>Password: </label><input type="password" id="password" name="password"></input></label><br />
								<select id="type" name="type">
									<option id="Professor" value="Professor" name="Professor">Professor</option>
									<option id="Student" value="Student" name="Student">Student</option>
								</select>
								<input type="submit" value="Send"></input>
				        	</form>
				        	<%
        		}
				
				if(session.getAttribute("REGISTERED")==null && session.getAttribute("ERROR") != null)
        		{
					session.removeAttribute("REGISTERED"); 
					session.removeAttribute("ERROR");
			        	%>
			        		<label><h3>ERROR DURING THE REGISTRATION...USERNAME ALREADY IN USE 
			<a href="register.jsp"><u>TRY AGAIN!</u></a>
			</h3></label>
			        		
			        	<%
			    }
				if ((String)session.getAttribute("REGISTERED")!= null)
				{
		        		%>
		        		
		        		<label><h3>REGISTRATION IS DONE THANK YOU!!!</h3></label>
		        		<form method="post" action="ServletRegistration?to=nextPage" id="okform">
			<h3><a  onclick="javascript:document.getElementById('okform').submit();"><u>Go to your pesonal page!</u></a></h3>
			</form>
		        		<%session.removeValue("REGISTERED"); %>
		        		<%
		        } 
			}
        	else{%>
        		<h3>You cannot be here you are already registered </h3>
        		<form method="post" action="ServletRegistration?to=nextPage" id="forward">
			<h3><a  onclick="javascript:document.getElementById('forward').submit();"><u>Go to your pesonal page!</u></a></h3>
			</form>
        	<%}
        		%>
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