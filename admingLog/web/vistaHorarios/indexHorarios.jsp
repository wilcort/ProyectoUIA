<%-- 
    Document   : indexHorarios
    Created on : Sep 8, 2024, 7:33:00 PM
    Author     : Dell
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ALMACEN</title>
        <style>
            .highlight {
                color: red; /* Cambia el color según lo que desees */
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>HORARIOS</h1>
        <a href="SvHorarios?accion=nuevo">Crear Nuevo Horario</a>

        <br/><br/>

        <table border="1" width="100%">
            <thead>
                <tr>
                    <th>ID. Horario</th>
                    <th>Hora de Entrada</th>
                    <th>Hora de Salida</th>
                    <th>Horas Laborales</th>
                    <th>Días Laborales</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="horario" items="${listaHorarios}">
                    <tr>
                        <td>${horario.idHorario}</td>
                        <td>${horario.horaEntrada}</td>
                        <td>${horario.horaSalida}</td>
                        <td>${horario.horasLaborales}</td>
                        <td>
                            <c:set var="diasSemanal" value="lunes, martes, miércoles, jueves, viernes, sábado, domingo" />
                            <c:forEach var="dia" items="${fn:split(diasSemanal, ', ')}">
                                <c:choose>
                                    <c:when test="${fn:contains(horario.diasLaborales, dia)}">
                                        <span class="highlight"><c:out value="${dia}" /></span>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${dia}" />
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${!fn:contains(diasSemanal, dia)}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <a href="SvHorarios?accion=ver_Horarios&id=<c:out value="${horario.idHorario}" />">Ver Empleado</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form action="vistasLog/administrador.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>