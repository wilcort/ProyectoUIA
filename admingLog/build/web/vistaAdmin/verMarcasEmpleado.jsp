<%@page import="java.util.List"%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Marcas de Empleado</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 8px;
                text-align: center;
                border: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
            }
            .error {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <h2>Marcas de Empleado</h2>

        <!-- Mensaje de error si existe -->
        <%
            String errorMessage = (String) request.getAttribute("error");
            if (errorMessage != null) {
        %>
        <div class="error"><%= errorMessage%></div>
        <% } %>

        <!-- Sección para filtrar marcas por mes y año -->
        <section>
            <form action="SvMarcas" method="GET">
                <label for="mes">Mes:</label>
                <select id="mes" name="mes" required>
                    <option value="">Seleccione un mes</option>
                    <% for (int i = 1; i <= 12; i++) {%>
                    <option value="<%= i%>"><%= new java.text.DateFormatSymbols().getMonths()[i - 1]%></option>
                    <% }%>
                </select>

                <label for="anio">Año:</label>
                <input type="number" id="anio" name="anio" min="2000" required>

                <input type="hidden" name="id_empleado" value="<%= request.getParameter("id_empleado") != null ? request.getParameter("id_empleado") : ""%>">
                <input type="hidden" name="accion" value="ver_marcas">

                <button type="submit">Filtrar</button>
            </form>

            <h4>Marcas Filtradas</h4>
            <table>
                <thead>
                    <tr>
                        <th>id Marca</th>
                        <th>Fecha Marca</th>
                        <th>Hora Entrada</th>
                        <th>Hora Salida</th>
                        <th>Hora Entrada Almuerzo</th>
                        <th>Hora Salida Almuerzo</th>
                        <th>Horas Trabajadas</th>
                        <th>Acciones</th> <!-- Nueva columna para el botón Modificar -->
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<ModelEmpleado.Marcas> listaMarcasMes = (List<ModelEmpleado.Marcas>) request.getAttribute("listaMarcas");
                        if (listaMarcasMes != null && !listaMarcasMes.isEmpty()) {
                            for (ModelEmpleado.Marcas marca : listaMarcasMes) {
                    %>
                    <tr>
                        <td><%= marca.getIdMarca()%></td>
                        <td><%= marca.getFechaMarca()%></td>
                        <td><%= marca.getMarcaEntrada()%></td>
                        <td><%= marca.getMarcaSalida()%></td>
                        <td><%= marca.getMarcaEntradaAlmuerzo()%></td>
                        <td><%= marca.getMarcaSalidaAlmuerzo()%></td>
                        <td><%= marca.getHorasDia()%> horas</td>
                        <td>
                            <!-- Formulario para el botón Modificar -->
                            <form action="SvMarcas" method="POST">
                                <input type="hidden" name="id_marca" value="<%= marca.getIdMarca()%>">
                                <input type="hidden" name="id_empleado" value="<%= request.getParameter("id_empleado") != null ? request.getParameter("id_empleado") : ""%>">
                                <input type="hidden" name="accion" value="modificar_Marca">
                                <button type="submit">Modificar</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">No hay marcas disponibles para este empleado en este mes y año.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>

            <!-- Mostrar total de horas trabajadas en el mes -->
            <h4>Total de Horas Trabajadas en el Mes: <%= request.getAttribute("totalHorasMes") != null ? request.getAttribute("totalHorasMes") : "0"%> horas</h4>
        </section>

        <!-- Sección para ver todas las marcas del empleado -->
        <section>
            <h3>Ver Todas las Marcas del Empleado</h3>
            <table>
                <thead>
                    <tr>
                        <th>Fecha Marca</th>
                        <th>Hora Entrada</th>
                        <th>Hora Salida</th>
                        <th>Hora Entrada Almuerzo</th>
                        <th>Hora Salida Almuerzo</th>
                        <th>Horas Trabajadas</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<ModelEmpleado.Marcas> todasLasMarcas = (List<ModelEmpleado.Marcas>) request.getAttribute("todasLasMarcas");
                        if (todasLasMarcas != null && !todasLasMarcas.isEmpty()) {
                            for (ModelEmpleado.Marcas marca : todasLasMarcas) {
                    %>
                    <tr>
                        <td><%= marca.getFechaMarca()%></td>
                        <td><%= marca.getMarcaEntrada()%></td>
                        <td><%= marca.getMarcaSalida()%></td>
                        <td><%= marca.getMarcaEntradaAlmuerzo()%></td>
                        <td><%= marca.getMarcaSalidaAlmuerzo()%></td>
                        <td><%= marca.getHorasDia()%> horas</td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6">No hay marcas registradas para este empleado.</td>
                    </tr>
                    <% }%>

                </tbody>
            </table>
        </section>
        <!-- Botón para regresar a "ver_Empleado" -->
        <form action="SvColaborador" method = "get">
            <input type="hidden" name="accion" value="Ver_Empleado">
            <input type="hidden" name="id" value="${param.id_empleado}">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>
