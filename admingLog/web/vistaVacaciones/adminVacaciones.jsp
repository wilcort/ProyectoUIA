<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vacaciones de Empleado</title>
    <style>
        /* CSS básico */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            padding-top: 20px;
        }

        .container {
            width: 80%;
            max-width: 800px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }

        .table-container {
            max-height:200px;
            overflow-y: auto;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        thead th {
            position: sticky;
            top: 0;
            background-color: #4CAF50;
            color: white;
            z-index: 1;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #e1f5fe;
        }

        .form-container {
            background-color: #f9f9f9;
            padding: 30px;
            margin-top: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input, textarea, button {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            border: 1px solid #ddd;
        }

        button {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
    <script>
        function calcularDiasVacaciones() {
            const fechaInicio = new Date(document.getElementById('fechaInicio').value);
            const fechaFin = new Date(document.getElementById('fechaFin').value);

            if (fechaInicio instanceof Date && !isNaN(fechaInicio) &&
                fechaFin instanceof Date && !isNaN(fechaFin)) {
                
                const diferenciaMilisegundos = fechaFin - fechaInicio;
                const diasVacaciones = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1;
                document.getElementById('diasVacaciones').value = diasVacaciones >= 0 ? diasVacaciones : 0;
            } else {
                document.getElementById('diasVacaciones').value = '';
            }
        }

        window.onload = function() {
            const today = new Date().toISOString().split("T")[0];
            document.getElementById("fechaInicio").setAttribute("min", today);

            document.getElementById("fechaInicio").addEventListener("input", function() {
                const fechaInicio = this.value;
                document.getElementById("fechaFin").setAttribute("min", fechaInicio);
                calcularDiasVacaciones();
            });
        };
    </script>
</head>
<body>
    <div class="container">
        <h1>Información de Vacaciones</h1>
        <div class="table-container">
            <table>
                <thead>
                    <tr>                              
                        <th>ID Vacación</th>
                        <th>Días de Vacaciones por Disfrutar</th>
                        <th>Días de Vacaciones Solicitados</th>
                        <th>Fecha Inicio Vacaciones</th>
                        <th>Fecha Fin Vacaciones</th>
                        <th>Estado de Solicitud</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vacacion" items="${listaVacaciones}">
                        <tr>
                            <td>${vacacion.idVacacion}</td>
                            <td>${vacacion.diasVacacionesSolicitados}</td>
                            <td>${vacacion.diasVacacionesTotal}</td>
                            <td>${vacacion.fechaInicio}</td>
                            <td>${vacacion.fechaFin}</td>
                            <td>${vacacion.estadoSolicitud}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="form-container">
            <h2>Solicitar Vacaciones</h2>
            <form action="SvVacaciones" method="post">
                <!-- Mostrar ID de Empleado y Fecha de Contratación -->
                <label>ID del Empleado:</label>
                <input type="text" name="id_empleado" value="${sessionScope.id_empleado}" readonly>
 
                <!-- Campos de Solicitud de Vacaciones -->
                <label for="diasVacaciones">Días de Vacaciones:</label>
                <input type="number" id="diasVacaciones" name="diasVacaciones" required readonly>

                <label for="fechaInicio">Fecha de Inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" required oninput="calcularDiasVacaciones()" min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">

                <label for="fechaFin">Fecha de Fin:</label>
                <input type="date" id="fechaFin" name="fechaFin" required oninput="calcularDiasVacaciones()">

                <label for="comentario">Comentario:</label>
                <textarea id="comentario" name="comentario" rows="3"></textarea>

                <input type="hidden" name="accion" value="Realizar_Solicitud">                           
                <button type="submit">Enviar Solicitud</button>
            </form>
        </div>

        <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-top: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </div>
</body>
</html>

