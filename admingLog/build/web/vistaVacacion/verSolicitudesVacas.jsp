<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitudes de Vacaciones Pendientes</title>
        <link rel="stylesheet" href="path/to/your/styles.css"> <!-- Enlace a tu archivo CSS -->
    </head>
    <body>

        <div class="container">       
        <h1>Solicitudes de Vacaciones Pendientes</h1>

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
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vacacion" items="${listarVacaciones}">
                    <tr>
                        <td>${vacacion.idVacacion}</td>
                        <td>${vacacion.fechaSolicitud}</td>
                        <td>${vacacion.fechaInicio}</td>
                        <td>${vacacion.fechaFin}</td>
                        <td>${vacacion.diasVacacionesSolicitados}</td>
                        <td>${vacacion.estadoSolicitud}</td>
                        <td>${vacacion.comentario}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-top: 10px;">
                <button type="submit">Regresar</button>
        </form>
     </body>
</html>
