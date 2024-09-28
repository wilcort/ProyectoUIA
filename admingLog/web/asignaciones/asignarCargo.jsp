<%-- 
    Document   : asignarCargo
    Created on : Sep 18, 2024, 6:28:30 PM
    Author     : Dell
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Asignar Cargo</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 40px;
            }
            .container {
                max-width: 600px;
                margin: auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
            }
            label, select, button {
                display: block;
                width: 100%;
                margin-bottom: 10px;
            }
            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px;
                border: none;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Asignar Cargo al Empleado</h1>

            <!-- Mostrar ID del empleado -->
            <label for="id_empleado">ID del Empleado:</label>
            <input type="text" id="id_empleado" value="${param.id_empleado}" readonly>

            <form action="SvAsignar" method="post">
                <input type="hidden" name="accion" value="asignar_Cargo">
                <input type="hidden" name="id_empleado" value="${param.id_empleado}">

                <label for="cargo">Seleccione el Cargo:</label>

                <select name="idCargo" id="cargo" required>
                    <option value="">Seleccione un Cargo</option>
                    <!-- Iterar sobre la lista de cargos -->
                    <c:forEach var="cargo" items="${cargos}">
                        <option value="${cargo.idCargo}">${cargo.nombreCargo}</option>
                    </c:forEach>
                </select>

                <button type="submit">Asignar Cargo</button>
            </form>

                <!-- Botón para regresar a "ver_Empleado" -->
                <form action="SvColaborador" method = "get">
                    <input type="hidden" name="accion" value="Ver_Empleado">
                    <input type="hidden" name="id" value="${param.id_empleado}">
                    <button type="submit">Regresar</button>
                </form>    
                  
        </div>
    </body>
</html>