<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Información de Empleados </title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                font-size: 14px;
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
            table td a {
                color: #007bff;
                text-decoration: none;
                font-weight: bold;
            }
            table td a:hover {
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
            @media screen and (max-width: 600px) {
                table, thead, tbody, th, td, tr {
                    display: block;
                }
                table tr {
                    margin-bottom: 15px;
                }
                table td {
                    font-size: 12px;
                }
                table td a {
                    display: inline-block;
                    margin-top: 5px;
                }
            }
        </style>
    </head>
    <body>
        <h1>Información de Empleados Marcas </h1>
        <table>
            <thead>
                <tr>
                    <th>ID Empleado</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Ver Marcas</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="empleado" items="${listarEmpleados}">
                    <tr>
                        <td><c:out value="${empleado.id_Empleado}" /></td>
                        <td><c:out value="${empleado.nombre}" /></td>
                        <td><c:out value="${empleado.apellido_1}" /></td>
                        <td><c:out value="${empleado.apellido_2}" /></td>
                        <td>
                            <form action="/admingLog/SvMostrarDatos" method="GET">
                                <input type="hidden" name="accion" value="ver_Marcas_Update">
                                <input type="hidden" name="id_empleado" value="<c:out value='${empleado.id_Empleado}' />">
                                <button type="submit" class="btn btn-blue">Ver Todas las Marcas</button>
                            </form>
                            
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listarEmpleados}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: #777;">No hay información de empleados disponible.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="button-container">
            <a href="vistasLog/administrador.jsp" class="button">Regresar</a>
        </div>
    </body>
</html>
