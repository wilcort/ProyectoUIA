<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Información de Vacaciones de Empleados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> 
    </head>
    <body>
        <h1>Información de Vacaciones de Empleados</h1>
        <table border="1" cellpadding="10" cellspacing="0">           
            <thead>
                <tr>
                    <th>ID Empleado</th>
                    <th>ID Vacación</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Fecha de Contratación</th> 
                    <th>Días Totales de Vacaciones</th>
                    <th>Ver Solicitud</th>        
                   
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vacacion" items="${listarVacaciones}">
                    <tr>
                        <td><c:out value="${vacacion.idEmpleado}" /></td>
                        <td><c:out value="${vacacion.idVacacion}" /></td>
                        <td><c:out value="${vacacion.colaborador.nombre}" /></td>
                        <td><c:out value="${vacacion.colaborador.apellido_1}" /></td>
                        <td><c:out value="${vacacion.colaborador.apellido_2}" /></td>
                        <td><c:out value="${vacacion.colaborador.fecha_contratacion}" /></td>                        
                        <td><c:out value="${vacacion.diasVacacionesTotal}" /></td>
                        <td>
                          <a href="SvVacaciones?accion=Ver_Solicitudes_Admin&id_empleado=<c:out value="${vacacion.idEmpleado}" />">Ver Solicitudes</a>
                        </td> 
                    </tr>
                </c:forEach>
                <c:if test="${empty listarVacaciones}">
                    <tr>
                        <td colspan="8">No hay información de vacaciones disponible.</td>
                    </tr>
                </c:if>


            </tbody>
        </table>

        <form action="http://localhost:8080/admingLog/vistasLog/administrador.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>