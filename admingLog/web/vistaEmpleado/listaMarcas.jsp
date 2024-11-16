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

            form {
                text-align: center;
                margin-bottom: 20px;
            }

            select {
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            button {
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
            }

            button:hover {
                background-color: #0056b3;
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

            @media screen and (max-width: 768px) {
                table {
                    font-size: 14px;
                }

                th, td {
                    padding: 8px;
                }

                .btn {
                    font-size: 14px;
                    padding: 10px 15px;
                }
            }

            @media screen and (max-width: 480px) {
                table, th, td {
                    display: block;
                    width: 100%;
                }

                th, td {
                    text-align: right;
                    padding: 10px;
                    position: relative;
                }

                td::before {
                    content: attr(data-label);
                    position: absolute;
                    left: 0;
                    width: 50%;
                    text-align: left;
                    font-weight: bold;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Marcas de Empleado</h1>

            <!-- Filtro por mes -->
            <form action="/admingLog/SvMostrarDatos" method="GET">
                <input type="hidden" name="accion" value="filtrar">
                <label for="mes">Filtrar por mes:</label>
                <select name="mes" id="mes">
                    <option value="">Seleccione Mes</option>
                    <option value="01">Enero</option>
                    <option value="02">Febrero</option>
                    <option value="03">Marzo</option>
                    <option value="04">Abril</option>
                    <option value="05">Mayo</option>
                    <option value="06">Junio</option>
                    <option value="07">Julio</option>
                    <option value="08">Agosto</option>
                    <option value="09">Septiembre</option>
                    <option value="10">Octubre</option>
                    <option value="11">Noviembre</option>
                    <option value="12">Diciembre</option>
                </select>
                <button type="submit">Filtrar</button>
            </form>

            <!-- Tabla de Marcas -->
            <c:choose>
                <c:when test="${empty listaMarcas}">
                    <div class="no-data">No hay marcas registradas para este empleado.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Entrada</th>
                                <th>Salida</th>
                                <th>Salida Almuerzo</th>
                                <th>Entrada Almuerzo</th>
                                <th>Horas Trabajadas Normales</th>
                                <th>Horas Trabajadas Extra</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="marca" items="${listaMarcas}">
                                <tr>
                                    <td data-label="Fecha"><c:out value="${marca.fechaMarca}" /></td>
                                    <td data-label="Entrada"><c:out value="${marca.marcaEntrada}" /></td>
                                    <td data-label="Salida"><c:out value="${marca.marcaSalida}" /></td>
                                    <td data-label="Salida Almuerzo"><c:out value="${marca.marcaSalidaAlmuerzo}" /></td>
                                    <td data-label="Entrada Almuerzo"><c:out value="${marca.marcaEntradaAlmuerzo}" /></td>
                                    <td data-label="Horas Trabajadas"><c:out value="${marca.horasDiaNormal}" /></td>
                                    <td data-label="Horas Trabajadas"><c:out value="${marca.horasDiaExtra}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

            <!-- Botón de regreso -->
            <a href="vistasLog/empleado.jsp" class="btn">Volver</a>
        </div>
    </body>
</html>
