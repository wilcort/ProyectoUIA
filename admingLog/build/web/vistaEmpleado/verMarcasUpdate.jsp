<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marcas de Empleado</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f8f9fa;
                color: #212529;
            }

            .container {
                width: 90%;
                max-width: 1200px;
                margin: 30px auto;
                background: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            h1 {
                font-size: 24px;
                color: #007bff;
                text-align: center;
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 16px;
                text-align: left;
            }

            th, td {
                padding: 12px;
                border: 1px solid #ddd;
            }

            th {
                background-color: #007bff;
                color: white;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .btn {
                display: inline-block;
                padding: 12px 20px;
                margin: 10px 0;
                text-decoration: none;
                background-color: #007bff;
                color: white;
                border-radius: 5px;
                font-weight: bold;
                text-align: center;
                transition: background-color 0.3s ease;
            }

            .btn:hover {
                background-color: #0056b3;
            }

            .no-data {
                text-align: center;
                color: #666;
                font-size: 18px;
                margin: 20px 0;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Marcas de Empleado</h1>

            <!-- Tabla de Marcas -->
            <c:choose>
                <c:when test="${empty listaMarcas}">
                    <div class="no-data">No hay marcas registradas para este empleado.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID Marca</th>
                                <th>Fecha</th>
                                <th>Entrada</th>
                                <th>Salida</th>
                                <th>Salida Almuerzo</th>
                                <th>Entrada Almuerzo</th>
                                <th>Horas Trabajadas Normales</th>
                                <th>Horas Trabajadas Extra</th>
                                <th>Modificar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="marca" items="${listaMarcas}">
                                <tr>
                                    <td data-label="ID Marca"><c:out value="${marca.idMarca}" /></td>
                                    <td data-label="Fecha"><c:out value="${marca.fechaMarca}" /></td>
                                    <td data-label="Entrada"><c:out value="${marca.marcaEntrada}" /></td>
                                    <td data-label="Salida"><c:out value="${marca.marcaSalida}" /></td>
                                    <td data-label="Salida Almuerzo"><c:out value="${marca.marcaSalidaAlmuerzo}" /></td>
                                    <td data-label="Entrada Almuerzo"><c:out value="${marca.marcaEntradaAlmuerzo}" /></td>
                                    <td data-label="Horas Trabajadas"><c:out value="${marca.horasDiaNormal}" /></td>
                                    <td data-label="Horas Trabajadas"><c:out value="${marca.horasDiaExtra}" /></td>
                                    
                                    <td data-label="Modificar">
                                        <form action="/admingLog/SvMostrarDatos" method="POST">
                                            <input type="hidden" name="accion" value="Modificar_Marca">
                                            <input type="hidden" name="id_empleado" value="${param.id_empleado}" />
                                            <input type="hidden" name="id_marca" value="${marca.idMarca}" />
                                            <button type="submit">Modificar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

            <!-- Botón de regreso -->
            <form action="/admingLog/SvMostrarDatos" method="POST">
                <input type="hidden" name="accion" value="Ver_Empleados">
                <button type="submit">Regresar</button>
            </form>
        </div>
    </body>
</html>
