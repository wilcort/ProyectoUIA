<%-- 
    Document   : verCargo
    Created on : Aug 17, 2024, 6:12:23 AM
    Author     : Dell
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cargos</title>
        <style>
            table {
                border-collapse: collapse;
                width: 50%;
                margin: 20px auto;
            }
            th, td {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h1>Lista de Cargos</h1>

        <h2>Información de los Cargos</h2>
        <table>
            <tr>
                <th>Id del Cargo:</th>
                <th>Nombre del Cargo:</th>
                <th>Estado</th>
            </tr>
            <tr>
                <td>${cargo.idCargo}</td>
                <td>${cargo.nombreCargo}</td>
                <td>
                    <c:choose>
                        <c:when test="${cargo.estado}">
                            Activo
                        </c:when>
                        <c:otherwise>
                            Inactivo
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>

        <!-- Botones -->
        <div style="text-align: center; margin-top: 20px;">

            <form action="SvCargo?accion=eliminar_Cargo" method="post" style="display: inline;">
                <input type="hidden" name="id" value="${cargo.idCargo}">
                <button type="submit" name="eliminar" value="eliminar">Eliminar</button>
            </form>

            <form action="SvCargo?accion=modificar_Cargo" method="post" style="display: inline; margin-left: 10px;">
                <input type="hidden" name="id" value="${cargo.idCargo}">
                <button type="submit" name="actualizar" value="actualizar">Actualizar</button>
            </form>

            <form action="http://localhost:8080/admingLog/SvCargo" style="display: inline; margin-left: 10px;">
                <button type="submit">Regresar</button>
            </form>
        </div>
    </body>
</html>