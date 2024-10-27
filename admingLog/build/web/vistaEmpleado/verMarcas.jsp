<%@page import="java.time.Year"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consultar Marcas Anteriores</title>
        <style>
            /* Estilos generales */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            .total-horas {
                font-weight: bold;
                background-color: #f9f9f9;
            }
        </style>
    </head>
    <body>
        <h1>Consultar Marcas Anteriores</h1>

        <!-- Formulario para seleccionar el mes y año -->
        <form action="/admingLog/SvMostrarDatos" method="POST">
            <input type="hidden" name="accion" value="verMarcasAnteriores">
            <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">

             <!-- Mostrar ID del empleado -->
            <label for="id_empleado">ID del Empleado:</label>
            <input type="text" id="id_empleado" value="${param.id_empleado}" readonly>

            
            
            <label for="mes">Mes:</label>
            <select name="mes" id="mes">
                <c:forEach begin="1" end="12" var="m">
                    <option value="${m}">${m}</option>
                </c:forEach>
            </select>

            <label for="anio">Año:</label>
            <input type="number" name="anio" id="anio" value="<%= Year.now().getValue()%>" min="2000" max="${Year.now().getValue()}">

            <button type="submit">Consultar Marcas</button>
        </form>

        <h1>Marcas Registradas</h1>
        <table>
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Hora Entrada</th>
                    <th>Hora Salida</th>
                    <th>Hora Entrada Almuerzo</th>
                    <th>Hora Salida Almuerzo</th>
                    <th>Horas Trabajadas</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty listaMarcas}">
                    <tr>
                        <td colspan="6">No hay marcas disponibles.</td>
                    </tr>
                </c:if>
                <c:forEach var="marca" items="${listaMarcas}">
                    <tr>
                        <td><c:out value="${marca.fechaMarca}" /></td>
                        <td><c:out value="${marca.marcaEntrada}" /></td>
                        <td><c:out value="${marca.marcaSalida}" /></td>
                        <td><c:out value="${marca.marcaEntradaAlmuerzo}" /></td>
                        <td><c:out value="${marca.marcaSalidaAlmuerzo}" /></td>
                        <td><c:out value="${marca.horasDia}" /></td>
                    </tr>
                </c:forEach>
                <tr class="total-horas">
                    <td colspan="5" style="text-align: right;">Total de Horas:</td>
                    <td><c:out value="${totalHoras}" /> horas</td>
                </tr>
            </tbody>
        </table>

        <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>