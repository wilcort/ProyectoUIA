<%-- 
    Document   : asignarHorario
    Created on : Sep 28, 2024, 6:09:05 AM
    Author     : Dell
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Asignar Horario</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                background-color: #f9f9f9;
                color: #333;
            }
            h1, h2 {
                text-align: center;
                color: #007BFF;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
                background-color: #fff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            th, td {
                padding: 10px;
                text-align: left;
                border: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            .highlight {
                color: green;
                font-weight: bold;
            }
            label, select, button {
                display: block;
                margin: 10px auto;
                width: 80%;
                max-width: 500px;
                padding: 10px;
            }
            select {
                border: 1px solid #ddd;
                border-radius: 4px;
            }
            button {
                background-color: #007BFF;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 16px;
            }
            button:hover {
                background-color: #0056b3;
            }
            a {
                display: block;
                text-align: center;
                margin-top: 20px;
                text-decoration: none;
                color: #007BFF;
            }
            a:hover {
                text-decoration: underline;
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
                                    <c:forEach var="dia" items="${fn:split(diasSemanal, ',')}">
                                        <c:choose>
                                            <c:when test="${fn:contains(horario.diasLaborales, fn:trim(dia))}">
                                                <span class="highlight">${fn:trim(dia)}</span>
                                            </c:when>
                                            <c:otherwise>
                                                ${fn:trim(dia)}
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test="${dia != 'domingo'}">, </c:if>
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
            <select name="idHorario" id="idHorario" required>
                <option value="">Seleccione un Horario</option>
                <c:forEach var="horario" items="${horarios}">
                    <option value="${horario.idHorario}">
                        ID: ${horario.idHorario} - ${horario.horaEntrada} a ${horario.horaSalida}
                    </option>
                </c:forEach>
            </select>

            <button type="submit">Asignar Horario</button>
        </form>

        <a href="SvColaborador?accion=Ver_Empleado&id=${sessionScope.id_empleado}">Regresar a Datos del Empleado</a>
    </body>
</html>
