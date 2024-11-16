<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Marcas del Empleado</title>
        <style>
            /* Estilos generales */
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f9f9f9;
                color: #333;
            }
            h1, h2 {
                text-align: center;
                color: #4CAF50;
            }
            .container {
                width: 90%;
                max-width: 1200px;
                margin: 20px auto;
                background: #fff;
                padding: 20px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .consulta-form {
                text-align: center;
                margin-bottom: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #4CAF50;
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ddd;
            }
            button {
                background-color: #4CAF50;
                color: white;
                border: none;
                padding: 10px 20px;
                margin: 10px 5px;
                font-size: 16px;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
            .empty-message {
                text-align: center;
                font-size: 16px;
                color: #555;
            }
            @media (max-width: 768px) {
                table {
                    font-size: 14px;
                }
                th, td {
                    padding: 8px;
                }
                button {
                    font-size: 14px;
                    padding: 8px 15px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Marcas del Empleado</h1>

            <!-- Formulario para seleccionar mes y quincena -->
            <h2>Consultar Marcas</h2>
            <form action="${pageContext.request.contextPath}/SvMostrarDatos" method="POST" class="consulta-form">
                <input type="hidden" name="accion" value="ver_Marcas">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
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
                        <th>Horas Normales</th>
                        <th>Horas Extras</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Verifica si la lista de marcas está vacía -->
                    <c:if test="${empty listaMarcas}">
                        <tr>
                            <td colspan="7" class="empty-message">No hay marcas disponibles.</td>
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
                            <td><c:out value="${marca.horasDiaNormal}" /></td>
                            <td><c:out value="${marca.horasDiaExtra}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Botón para regresar a la página principal -->
            <div style="text-align: center;">
                <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline;">
                    <button type="submit">Regresar</button>
                </form>
            </div>
        </div>
    </body>
</html>
