<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Información de Empleados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #a8e6ff, #f3e7ff);
                margin: 0;
                padding: 20px;
            }
            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px auto;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #fff;
            }
            table thead {
                background-color: #007bff;
                color: #fff;
            }
            table th, table td {
                padding: 12px;
                text-align: left;
            }
            table th {
                font-weight: bold;
            }
            table tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            table tr:hover {
                background-color: #f1f1f1;
            }
            .details-link {
                color: #007bff;
                text-decoration: none;
                font-weight: bold;
            }
            .details-link:hover {
                color: #0056b3;
                text-decoration: underline;
            }
            .button-container {
                text-align: center;
                margin-top: 20px;
            }
            .button {
                background-color: #007bff;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                text-decoration: none;
            }
            .button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <h1>Información de Empleados</h1>
        <table>
            <thead>
                <tr>
                    <th>ID Empleado</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Ver Detalles</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="empleado" items="${listarIncapEmpleado}">
                    <tr>
                        <td><c:out value="${empleado.id_Empleado}" /></td>
                        <td><c:out value="${empleado.nombre}" /></td>
                        <td><c:out value="${empleado.apellido_1}" /></td>
                        <td><c:out value="${empleado.apellido_2}" /></td>
                        <td>
                            <a href="SvIncapacidades?accion=Ver_Incap_Admin&id_empleado=<c:out value="${empleado.id_Empleado}" />">Ver Solicitudes</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listarIncapEmpleado}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: #777;">No hay información de empleados disponible.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="button-container">
            <a href="http://localhost:8080/admingLog/vistasLog/administrador.jsp" class="button">Regresar</a>
        </div>
    </body>
</html>
