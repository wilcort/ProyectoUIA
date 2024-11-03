<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Solicitudes de Vacaciones Pendientes</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h1>Solicitudes de Vacaciones Pendientes</h1>
        <input type="hidden" name="id_empleado" value="<%= session.getAttribute("id_empleado")%>">
        <c:choose>
            <c:when test="${empty listarVacaciones}">
                <p>No hay solicitudes de vacaciones pendientes.</p>
            </c:when>
            <c:otherwise>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID Vacación</th>
                            <th>Nombre del Colaborador</th>
                            <th>Fecha de Solicitud</th>
                            <th>Fecha de Inicio</th>
                            <th>Fecha de Fin</th>
                            <th>Días Solicitados</th>
                            <th>Total de días de Vacaciones</th>
                            <th>Estado</th>
                            <th>Comentario</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vacacion" items="${listarVacaciones}">
                            <tr>
                                <td>${vacacion.idVacacion}</td>
                                <td>${vacacion.colaborador.nombre} ${vacacion.colaborador.apellido_1} ${vacacion.colaborador.apellido_2}</td>
                                <td>${vacacion.fechaSolicitud}</td>
                                <td>${vacacion.fechaInicio}</td>
                                <td>${vacacion.fechaFin}</td>
                                <td>${vacacion.diasVacacionesSolicitados}</td>
                                <td>${vacacion.diasVacacionesTotal}</td>
                                <td>${vacacion.estadoSolicitud}</td>
                                <td>${vacacion.comentario}</td>
                                <td>
                                    <!-- Botón para aprobar -->
                                    <form action="SvVacaciones" method="post" style="display:inline;">
                                        <input type="hidden" name="accion" value="Aprobar">
                                        <input type="hidden" name="id_Vacacion" value="${vacacion.idVacacion}">
                                        <input type="hidden" name="id_Empleado" value="<%= session.getAttribute("id_empleado")%>"> <!-- Asegúrate de enviar el ID del empleado -->
                                        <input type="hidden" name="diasSolicitados" value="${vacacion.diasVacacionesSolicitados}">
                                        <input type="hidden" name="diasTotales" value="${vacacion.diasVacacionesTotal}">
                                        <button type="submit">Aprobar</button>
                                    </form>

                                    
                                    <!-- Botón para denegar -->
                                    <form action="vacacionesController" method="post" style="display:inline;">
                                        <input type="hidden" name="accion" value="Denegar">
                                        <input type="hidden" name="id_Vacacion" value="${vacacion.idVacacion}">
                                        <button type="submit">Denegar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <form action="http://localhost:8080/admingLog/SvVacaciones" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>
