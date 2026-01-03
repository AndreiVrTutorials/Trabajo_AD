<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<h1 style="color:red;">HA OCURRIDO UN ERROR</h1>
	<hr>
	<h3>Tipo de error:</h3>
	<p>${error}</p>
	<br>
	<form action="TratamientoFich.jsp" method="post">
		<input type="submit" value="Volver">
	</form>
</body>
</html>