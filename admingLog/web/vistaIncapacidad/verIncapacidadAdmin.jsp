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
            .tabla-container {
                margin-bottom: 40px;
                width: 100%;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }
            th, td {
                padding: 12px;
                text-align: left;
                border: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            .section-title {
                font-size: 24px;
                margin-bottom: 20px;
            }
            .btn-container {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }
            button {
                padding: 10px 20px;
                font-size: 16px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h1>Solicitudes de Incapacidades Pendientes (Administrador)</h1>

            <!-- Tabla de Solicitudes de Incapacidades -->
            <div class="tabla-container">
                <h2 class="section-title">Solicitudes de Incapacidades</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID Incapacidad</th>
                            <th>Motivo</th>
                            <th>Fecha Inicio</th>
                            <th>Fecha Fin</th>
                            <th>Días de Incapacidad</th>
                            <th>Estado</th>                          
                            <th>Ver Solicitudes</th> <!-- Nueva columna para "Ver Solicitudes" -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="incapacidad" items="${listarIncapEmpleado}">
                            <tr>
                                <td>${incapacidad.idIncapacidad}</td>
                                <td>${incapacidad.motivo}</td>
                                <td>${incapacidad.fechaInicio}</td>
                                <td>${incapacidad.fechaFin}</td>
                                <td>${incapacidad.diasIncapacidad}</td>
                                <td>${incapacidad.estado}</td>

                                <td>
                                    <!-- Botón para ver más detalles de la solicitud -->
                                    <form action="SvIncapacidades" method="GET" style="display:inline;">
                                        <input type="hidden" name="accion" value="Ver_Solicitud">
                                        <input type="hidden" name="id_incapacidad" value="${incapacidad.idIncapacidad}">
                                        <button type="submit">Ver Solicitud</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty listarIncapEmpleado}">
                            <tr>
                                <td colspan="7" style="text-align: center; color: #777;">No hay solicitudes de incapacidades pendientes.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <!-- Botón para regresar -->
            <div class="btn-container">
                <form action="${pageContext.request.contextPath}/vistasLog/administrador.jsp" style="display:inline;">
                    <button type="submit">Regresar</button>
                </form>
            </div>
        </div>

    </body>
</html>
