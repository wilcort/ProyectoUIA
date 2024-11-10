<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Incapacidades</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 80vh;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .container {
                width: 80%;
                max-width: 900px;
                margin: 20px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: #f1f1f1;
            }
            td {
                color: #333;
            }
            .btn-secondary {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 20px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                text-decoration: none;
                border-radius: 5px;
                display: inline-block;
                margin-top: 20px;
                transition: background-color 0.3s ease;
            }
            .btn-secondary:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Lista de Incapacidades del Empleado</h1>

            <table>
                <thead>
                    <tr>
                        <th>Motivo</th>
                        <th>Fecha Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Días de Incapacidad</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="incapacidad" items="${listarIncapEmpleado}">
                        <tr>
                            <td>${incapacidad.motivo}</td>
                            <td>${incapacidad.fechaInicio}</td>
                            <td>${incapacidad.fechaFin}</td>
                            <td>${incapacidad.diasIncapacidad}</td>
                            <td>${incapacidad.estado}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline;">
                <button type="submit" class="btn-secondary">Regresar</button>
            </form>
        </div>
    </body>
</html>

