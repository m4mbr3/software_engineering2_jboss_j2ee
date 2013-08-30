<%@ page import="mph.entity.Professor"%>
<!doctype html>
<html>
<head>
<title>MPH-Manage Project Homework</title>
<link rel="stylesheet" type="text/css" href="css/prova.css" />
</head>
<body onload="addDel();">

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
			<h1>Create Project</h1>
		</header>
	</section>
	<div id="content">
		<div id="mainContent">
			<%String x = (String) request.getAttribute("ERROR_DATE");
			if(x != null) {
			if( x.equals("true") ) {%>
				<h4>Check data !!!</h4>
			<%}} %>
			<form action="./ServletProject?to=createProject" id="myTable" method="post">
				<div align="left">
					<table id="dataPrj">
						<tr>
							<td>NameProject:</td>
						</tr>
						<tr>
							<td><input type="text" name='nameProj'/></td>
						</tr>
						<tr>
							<td>Deadline Project (format gg/mm/yyyy):</td>
						</tr>
						<tr>
							<td><input type="text" name='deadlineProj'/></td>
						</tr>
						<tr>
							<td>Description:</td>
						</tr>
						<tr>
							<td><textarea name='description'></textarea>
							</td>
						</tr>
					</table>
				</div>
				<input type="hidden" id="numDel" name="numDel" value="0">
				<input type="submit" value="Submit !" />
			</form>
			<a href="#" onclick="addDel();">Add Deliverable</a>
			<a href="#" onclick="removeDel();">Remove Deliverable</a>

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
<script type="text/javascript">
	var i = 1;
	var idTable = "dataPrj";
	var idHiddenNumDel = "numDel";
	function addDel() {
		//var txt = 'Welcome to RoseIndia.net';
		var titleDel = document.createTextNode("Deliverable " + i + ":");
		var titleDeaDel = document.createTextNode("Deadline Deliverable " + i
				+ " (format gg/mm/yyyy):");
		var inputDel = document.createElement('input');
		inputDel.setAttribute("type", "text");
		inputDel.setAttribute("name", "nameDel" + i); //name of name deliverable input 

		var inputDeaDel = document.createElement('input');
		inputDeaDel.setAttribute("type", "text");
		inputDeaDel.setAttribute("name", "deadlineDel" + i); //name of deadline deliverable input

		var td = new Array();
		var tr = new Array();
		for ( var j = 0; j < 4; j++) {
			td[j] = document.createElement('td');
			tr[j] = document.createElement('tr');
		}

		td[0].appendChild(titleDel);
		td[1].appendChild(inputDel);
		td[2].appendChild(titleDeaDel);
		td[3].appendChild(inputDeaDel);

		for ( var j = 0; j < 4; j++) {
			tr[j].appendChild(td[j]);
			document.getElementById(idTable).appendChild(tr[j]);
		}
		document.getElementById(idHiddenNumDel).setAttribute('value', i);

		i++;
	}

	function removeDel() {
		if (i > 2) {
			var numCh = document.getElementById(idTable).childNodes.length;
			for ( var j = 0; j < 4; j++) {
				document.getElementById(idTable).removeChild(
						document.getElementById(idTable).childNodes.item(numCh
								- 1 - j));
			}
			i--;
			document.getElementById(idHiddenNumDel).setAttribute('value', i - 1);
		} else {
			alert("You can't remove other deliverable!");
		}
	}

</script>
</html>
