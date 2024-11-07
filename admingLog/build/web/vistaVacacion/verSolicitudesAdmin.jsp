<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitudes de Vacaciones Pendientes</title>
        <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Enlace a tu archivo CSS -->
        <style>
            .tabla-container-uno {
                margin-bottom: 30px;
                width: 40%;
            }
            .tabla-container {
                margin-bottom: 30px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                padding: 10px;
                text-align: left;
                border: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            .section-title {
                font-size: 24px;
                margin-bottom: 15px;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h1>Solicitudes de Vacaciones Pendientes (Administrador)</h1>

            <!-- Tabla de Vacaciones Disponibles -->
            <div class="tabla-container-uno">
                <h2 class="section-title">Cantidad de Vacaciones Disponibles</h2>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Cantidad de Vacaciones Disponibles</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listarVacaciones}">
                            <tr>
                                <td>${listarVacaciones[0].diasVacacionesTotal}</td> <!-- Mostrar solo el primer valor -->
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <!-- Tabla de Solicitudes de Vacaciones -->
            <div class="tabla-container">
                <h2 class="section-title">Solicitudes de Vacaciones</h2>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID Solicitud</th>
                            <th>Fecha Solicitud</th>
                            <th>Fecha Inicio</th>
                            <th>Fecha Fin</th>
                            <th>Días Solicitados</th>
                            <th>Estado</th>
                            <th>Comentario</th>
                            <th>Acciones</th> <!-- Nueva columna para los botones de acción -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vacacion" items="${listarVacaciones}" varStatus="status">
                            <tr>
                                <td>${vacacion.idVacacion}</td>
                                <td>${vacacion.fechaSolicitud}</td>
                                <td>${vacacion.fechaInicio}</td>
                                <td>${vacacion.fechaFin}</td>
                                <td>${vacacion.diasVacacionesSolicitados}</td>
                                <td>${fn:replace(fn:replace(vacacion.estadoSolicitud, '[', ''), ']', '')}</td>
                                <td>${vacacion.comentario}</td>
                                <td>
                                    <c:if test="${status.index > 0}">
                                        <c:if test="${vacacion.estadoSolicitud ne '[Aprobada]' && vacacion.estadoSolicitud ne '[Rechazada]'}">
                                            <!-- Botón para aprobar -->
                                            <form action="SvVacaciones" method="POST" style="display:inline;">
                                                <input type="hidden" name="accion" value="Aprobar_Vacaciones">
                                                <input type="hidden" name="id_Vacacion" value="${vacacion.idVacacion}">
                                                <input type="hidden" name="id_empleado" value="${vacacion.idEmpleado}">
                                                <input type="hidden" name="diasSolicitados" value="${vacacion.diasVacacionesSolicitados}">
                                                <input type="hidden" name="diasTotales" value="${vacacion.diasVacacionesTotal}">
                                                <button type="submit">Aprobar</button>
                                            </form>

                                            <!-- Botón para denegar -->
                                            <form action="SvVacaciones" method="POST" style="display:inline;">
                                                <input type="hidden" name="accion" value="Denegar_Vacaciones">
                                                <input type="hidden" name="id_Vacacion" value="${vacacion.idVacacion}">
                                                <input type="hidden" name="id_empleado" value="${vacacion.idEmpleado}">
                                                <input type="hidden" name="diasSolicitados" value="${vacacion.diasVacacionesSolicitados}">
                                                <input type="hidden" name="diasTotales" value="${vacacion.diasVacacionesTotal}">
                                                <button type="submit">Denegar</button>
                                            </form>
                                        </c:if>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Botón para regresar -->
            <form action="${pageContext.request.contextPath}/vistasLog/administrador.jsp" style="display:inline; margin-top: 10px;">
                <button type="submit">Regresar</button>
            </form>
        </div>

    </body>
</html>
