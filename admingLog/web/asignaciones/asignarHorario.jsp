<%-- 
    Document   : asignarHorario
    Created on : Sep 28, 2024, 6:09:05 AM
    Author     : Dell
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Horarios" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Asignar Horario</title>
        <link rel="stylesheet" type="text/css" href="styles.css">
        <style>
            /* Estilos básicos para la tabla */
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .highlight {
                color: green;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h1>Detalles del Horario</h1>

        <c:choose>
            <c:when test="${not empty horarios}">
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
                        <c:forEach var="horario" items="${horarios}">
                            <tr>
                                <td>${horario.idHorario}</td>
                                <td>${horario.horaEntrada}</td>
                                <td>${horario.horaSalida}</td>
                                <td>${horario.horasLaborales}</td>
                                <td>
                                    <c:set var="diasSemanal" value="lunes, martes, miércoles, jueves, viernes, sábado, domingo" />
                                    <c:set var="dias" value="${fn:split(horario.diasLaborales, ',')}" />
                                    <c:forEach var="dia" items="${fn:split(diasSemanal, ', ')}">
                                        <c:choose>
                                            <c:when test="${fn:contains(horario.diasLaborales, fn:trim(dia))}">
                                                <span class="highlight">${fn:trim(dia)}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${fn:trim(dia)}
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${!fn:contains(dias, fn:trim(dia))}">, </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No se encontraron horarios.</p>
            </c:otherwise>
        </c:choose>

        <h2>Asignar Horario a Empleado</h2>
        <form action="SvAsignar" method="post">
            <input type="hidden" name="accion" value="asignar_Horario">
            <input type="hidden" name="id_empleado" value="${sessionScope.id_empleado}">

            <label for="idHorario">Seleccione un Horario:</label>
            <select name="idHorario" id="idHorario">
                <c:forEach var="horario" items="${horarios}">
                    <option value="${horario.idHorario}">ID: ${horario.idHorario}</option>
                </c:forEach>
            </select>

            <button type="submit">Asignar Horario</button>
        </form>

        <br>
        <a href="SvColaborador?accion=Ver_Empleado&id=${sessionScope.id_empleado}">Regresar a Datos del Empleado</a>
    </body>
</html>