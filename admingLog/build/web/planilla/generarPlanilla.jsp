<%-- 
    Document   : generarPlanilla
    Created on : Nov 21, 2024, 10:08:51 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Planilla Quincenal</title>
    </head>
    <body>
        <h1>Generar Planilla Quincenal</h1>
        <form action="GenerarPlanillaServlet" method="post">
            <label for="mes">Mes:</label>
            <select id="mes" name="mes" required>
                <option value="1">Enero</option>
                <option value="2">Febrero</option>
                <option value="3">Marzo</option>
                <option value="4">Abril</option>
                <option value="5">Mayo</option>
                <option value="6">Junio</option>
                <option value="7">Julio</option>
                <option value="8">Agosto</option>
                <option value="9">Septiembre</option>
                <option value="10">Octubre</option>
                <option value="11">Noviembre</option>
                <option value="12">Diciembre</option>
            </select>
            <br><br>
            <label for="anio">Año:</label>
            <input type="number" id="anio" name="anio" min="1900" max="2100" required>
            <br><br>
            <button type="submit">Generar Planilla</button>
        </form>
    </body>
</html>