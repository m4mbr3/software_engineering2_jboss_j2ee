<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="./ServletProject?to=toCreateProject" method="post">
		<div>
			NumDel: <input type="text" name='varia' /><br />
		</div>
		<input type="submit" value="Entra" />
	</form>
	
	CREAZIONE GRUPPO
	<form action="./ServletGroup?to=createGroup" method="post">
		<div>
			Groupname: <input type="text" name='nameGroup' /><br />
		</div>
		<div>
			idstu1: <input type="text" name='idStud1' /><br />
		</div>
		<div>
			idstu2: <input type="text" name='idStud2' /><br />
		</div>
		<div>
			idProf: <input type="text" name='idProf' /><br />
		</div>
		<div>
			idProj: <input type="text" name='idProj' /><br />
		</div>
		<input type="submit" value="Entra" />
	</form>
</body>
</html>