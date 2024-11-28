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
                background-color: #f9f9f9;
                margin: 0;
                padding: 20px;
            }
            .container {
                background-color: #fff;
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            h1 {
                text-align: center;
                color: #333;
            }
            label {
                font-weight: bold;
                margin-bottom: 5px;
                display: block;
            }
            input[type="text"], select, button {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            button {
                background-color: #007BFF;
                color: #fff;
                border: none;
                cursor: pointer;
                font-size: 16px;
            }
            button:hover {
                background-color: #0056b3;
            }
            .button-secondary {
                background-color: #6c757d;
            }
            .button-secondary:hover {
                background-color: #5a6268;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Asignar Cargo al Empleado</h1>

            <!-- Mostrar ID del empleado -->
            <label for="id_empleado">ID del Empleado:</label>
            <input type="text" id="id_empleado" value="${param.id_empleado}" readonly>

            <!-- Formulario para asignar cargo -->
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

            <!-- Botón para regresar -->
            <form action="SvColaborador" method="get">
                <input type="hidden" name="accion" value="Ver_Empleado">
                <input type="hidden" name="id" value="${param.id_empleado}">
                <button type="submit" class="button-secondary">Regresar</button>
            </form>
        </div>
    </body>
</html>
