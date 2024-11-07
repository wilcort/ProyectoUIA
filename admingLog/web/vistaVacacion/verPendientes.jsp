<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Solicitudes de Vacaciones de Todos los Empleados</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h1>Solicitudes de Vacaciones de Todos los Empleados</h1>

        <c:choose>
            <c:when test="${empty listarVacaciones}">
                <p>No hay solicitudes de vacaciones disponibles.</p>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID Vacación</th>
                            <th>Nombre del Colaborador</th>                       
                            <th>Total de días de Vacaciones</th>
                            <th>Estado</th>
                            <th>Comentario</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vacacion" items="${listarVacaciones}">
                            <tr>
                                <td>${vacacion.idVacacion}</td>
                                <td>${vacacion.colaborador.nombre} ${vacacion.colaborador.apellido_1} ${vacacion.colaborador.apellido_2}</td>                         
                                <td>${vacacion.diasVacacionesTotal}</td>
                                <td>${vacacion.estadoSolicitud}</td>
                                <td>${vacacion.comentario}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <form action="SvVacaciones" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>