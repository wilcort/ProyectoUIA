<%-- 
    Document   : verHorarios
    Created on : Sep 14, 2024, 2:03:02 PM
    Author     : Dell
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles del Horario</title>

        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .highlight {
                color: red;
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>Detalles del Horario</h1>

        <c:choose>
            <c:when test="${not empty horario}">
                <table>
                    <thead>
                        <tr>
                            <th>ID Horario</th>
                            <th>Hora de Entrada</th>
                            <th>Hora de Salida</th>
                            <th>Horas Laborales</th>
                            <th>Días Laborales</th>
                        </tr>
                    </thead>
                    <tbody>
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
                        </tr>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No se encontró el horario.</p>
            </c:otherwise>
        </c:choose>

        <br/>
        <form action="SvHorarios?accion=eliminar_Horario" method="post" style="display: inline;">
            <input type="hidden" name="id" value="${horario.idHorario}">
            <button type="submit" name="eliminar" value="eliminar">Eliminar</button>
        </form>

        <form action="SvHorarios" method="get" style="display: inline;">
            <button type="submit">Volver a la lista de horarios</button>
        </form>
        
        
    </body>
</html>