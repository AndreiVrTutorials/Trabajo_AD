<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tratamiento Ficheros</title>
</head>
<body>
	<h1>Tratamiento Ficheros -- ODS 13</h1>
	<form action="ServletFich" method="post">
	<table>
		<tr>
			<td>
				<h5>Formato del fichero: </h5>
				<select name="formato">
					<option value="CSV">CSV</option>
					<option value="JSON">JSON</option>
					<option value="RDF">RDF</option>
					<option value="XLS">XLS</option>
					<option value="XML">XML</option>
				</select>
				<hr>
				<h5>¿Que quiere hacer con el fichero?</h5>
				Lectura: <input type="radio" name="accion" value="lectura"><br>
				Escritura: <input type="radio" name="accion" value="escritura"><p>
				<input type="submit" value="Enviar"> 
			</td>
			<td>
				<h5>Datos:</h5>
				Año: <input type="text" name="anio"><p>
				Sector: <input type="text" name="sector"><p>
				Contaminante: <input type="text" name="contaminante"><p>
				Cantidad: <input type="text" name="cantidad"><br>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>