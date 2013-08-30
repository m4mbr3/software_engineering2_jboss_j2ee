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
        </ul>  
    </nav>  
    <section id="intro">  
      <header>  
       <h1>Login Page</h1>  
   	 </header>  
    </section>  
   <div id="content">  
    <div id="mainContent">  
        <section>          				
        	<%if(session.getAttribute("ERRORLOGIN")==null){ %>
        	<h3>LOGIN AREA</h3>
        	<form method="post" action="ServletLogin?to=LoginForm" id="LoginForm">
				<label>Username: </label><input type="username" id="username" name="username"></input></label><br />
				<label>Password: </label><input type="password" id="password" name="password"></input></label><br />
				<input type="submit" value="Send"></input>
			</form>
			<form method="post" action="ServletLogin?to=register" id="register">
				Do you Want Register in?<a onclick="javascript:document.getElementById('register').submit();"> Click here</a>
			</form>
        	<%
        	}else{
        	%>
        	 <h3>User or password are wrong! Please Check it and Try again</h3>
        	 <h3>For Going back <form method="post" id="logoutforum" action="ServletLogin?to=LogoutForm">
			<a  onclick="javascript:document.getElementById('logoutforum').submit();">Click Here</a>
	        </form></h3>
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