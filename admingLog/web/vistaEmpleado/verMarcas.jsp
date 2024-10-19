<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Marcas del Empleado</title>
    <style>
        /* Agregar margen inferior al formulario */
        .consulta-form {
            margin-bottom: 20px; /* Cambia este valor según sea necesario */
        }
        table {
            width: 100%; /* Establece el ancho de las tablas */
            border-collapse: collapse; /* Mejora el diseño de la tabla */
            margin-bottom: 30px; /* Espaciado entre las tablas */
        }
        th, td {
            border: 1px solid black; /* Borde de las celdas */
            padding: 8px; /* Espaciado interno de las celdas */
            text-align: left; /* Alineación del texto */
        }
        th {
            background-color: #f2f2f2; /* Color de fondo de las cabeceras */
        }
    </style>
</head>
<body>
    <h1>Marcas del Empleado</h1>

    <!-- Tabla para realizar la consulta de marcas -->
    <h2>Consultar Marcas</h2>
    <form action="${pageContext.request.contextPath}/admingLog/SvMostrarDatos" method="post" class="consulta-form">
        <label for="mes">Seleccione el Mes:</label>
        <select name="mes" id="mes" required>
            <option value="1">Enero</option>
            <option value="2">Febrero</option>
            <option value="3">Marzo</option>
            <option value="4">Abril</option>
            <option value="5">Mayo</option>
            <option value="6">Junio</option>
            <option value="7">Julio</option>
            <option value="8">Agosto</option>
            <option value="9">Septiembre</option>
            <option value="10">Octubre</option>
            <option value="11">Noviembre</option>
            <option value="12">Diciembre</option>
        </select>
        
        <label for="quincena">Seleccione la Quincena:</label>
        <select name="quincena" id="quincena" required>
            <option value="1">Primera Quincena</option>
            <option value="2">Segunda Quincena</option>
        </select>
        
        <button type="submit">Consultar</button>
    </form>

    <!-- Tabla para mostrar las marcas registradas -->
    <h2>Marcas Registradas</h2>
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
            <!-- Verifica si la lista de marcas está vacía -->
            <c:if test="${empty listaMarcas}">
                <tr>
                    <td colspan="6">No hay marcas disponibles.</td>
                </tr>
            </c:if>

            <!-- Recorre la lista de marcas si existe -->
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
        </tbody>
    </table>

    <!-- Formulario para regresar a la página principal -->
    <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
        <button type="submit">Regresar</button>
    </form>
</body>
</html>


