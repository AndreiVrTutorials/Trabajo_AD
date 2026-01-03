<%@page import="com.gf.webapp.entity.DatosODS"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Resultado Lectura</title>
</head>
<body>
	<h1>Listado de Emisiones -- ODS 13</h1>
	<%
	List<DatosODS> listaDatos = (List<DatosODS>) request.getAttribute("lista");
	
	if(listaDatos != null && !listaDatos.isEmpty()){
	%>
	<table border="1">
		<tr>
			<th>Año</th>
			<th>Sector</th>
			<th>Contaminante</th>
			<th>Cantidad</th>
		</tr>
	</table>
	<%
	}
	%>
</body>
</html>