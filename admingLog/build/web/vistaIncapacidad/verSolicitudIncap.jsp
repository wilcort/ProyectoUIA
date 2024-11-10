<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitudes de Incapacidades Pendientes</title>
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
            <h1>Solicitudes de Incapacidades (Administrador)</h1>

            <!-- Tabla de Solicitudes de Incapacidades -->
            <div class="tabla-container">
                <h2 class="section-title">Solicitudes de Incapacidades</h2>
                <table border="1">
                    <thead>
                        <tr>
                            <th>ID Solicitud</th>
                            <th>Fecha Inicio</th>
                            <th>Fecha Fin</th>
                            <th>Días Solicitados</th>
                            <th>Estado</th>
                            <th>Motivo</th>
                            <th>Ver Anexos</th>
                            <th>Acciones</th> <!-- Columna para los botones de aprobar y denegar -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="incapacidad" items="${listarIncapEmpleado}" varStatus="status">
                            <tr>
                                <td>${incapacidad.idIncapacidad}</td>
                                <td>${incapacidad.fechaInicio}</td>
                                <td>${incapacidad.fechaFin}</td>
                                <td>${incapacidad.diasIncapacidad}</td>
                                <td>${fn:replace(fn:replace(incapacidad.estado, '[', ''), ']', '')}</td>
                                <td>${incapacidad.motivo}</td>
                                <td>
                                    <form action="SvIncapacidades" method="GET" target="_blank" style="display:inline;">
                                        <input type="hidden" name="accion" value="Ver_documentos">
                                        <input type="hidden" name="id_incapacidad" value="${incapacidad.idIncapacidad}">
                                        <input type="hidden" name="id_empleado" value="${incapacidad.empleadoIdEmpleado}">
                                        <button type="submit">Ver Documentos</button>
                                    </form>
                                </td>

                                <td>
                                    <c:if test="${status.index > -1}">
                                      <c:if test="${incapacidad.estado ne '[Aprobada]' && incapacidad.estado ne '[Rechazada]'}">
                                            <!-- Botón para aprobar -->
                                            <form action="SvIncapacidades" method="POST" style="display:inline;">
                                                <input type="hidden" name="accion" value="Aprobar_Incapacidad">
                                                <input type="hidden" name="id_incapacidad" value="${incapacidad.idIncapacidad}">
                                                <input type="hidden" name="id_empleado" value="${incapacidad.empleadoIdEmpleado}">
                                                <button type="submit">Aprobar</button>
                                            </form>

                                            <!-- Botón para denegar -->
                                            <form action="SvIncapacidades" method="POST" style="display:inline;">
                                                <input type="hidden" name="accion" value="Denegar_Incapacidad">
                                                <input type="hidden" name="id_incapacidad" value="${incapacidad.idIncapacidad}">
                                                <input type="hidden" name="id_empleado" value="${incapacidad.empleadoIdEmpleado}">
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
