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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #e1f5fe;
        }

        .form-container {
            background-color: #f9f9f9;
            padding: 20px;
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

            // Verificar que las fechas sean válidas
            if (fechaInicio instanceof Date && !isNaN(fechaInicio) &&
                fechaFin instanceof Date && !isNaN(fechaFin)) {
                
                // Calcular la diferencia en milisegundos
                const diferenciaMilisegundos = fechaFin - fechaInicio;

                // Convertir la diferencia a días
                const diasVacaciones = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1; // +1 para incluir el último día

                // Actualizar el campo de días de vacaciones
                document.getElementById('diasVacaciones').value = diasVacaciones >= 0 ? diasVacaciones : 0; // No permitir números negativos
            } else {
                // Si las fechas no son válidas, limpiar el campo de días de vacaciones
                document.getElementById('diasVacaciones').value = '';
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Información de Vacaciones</h1>

        <table>
            <tr>        
                <th>Empleado ID</th>
                <th>ID Vacación</th>
                <th>Días de Vacaciones por Disfrutar</th>
                <th>Estado de Solicitud</th>
            </tr>
            <c:forEach var="vacacion" items="${listaVacaciones}">
                <tr>
                    <td>${vacacion.idEmpleado}</td> 
                    <td>${vacacion.idVacacion}</td>
                    <td>${vacacion.diasVacacionesTotal}</td>
                    <td>${vacacion.estadoSolicitud}</td>
                </tr>
            </c:forEach>
        </table>

        <!-- Formulario de solicitud de vacaciones -->
        <div class="form-container">
            <h2>Solicitar Vacaciones</h2>
            <form action="SvVacaciones" method="post">
               
                <input type="hidden" name="id_empleado" value="<%= request.getParameter("id_empleado") != null ? request.getParameter("id_empleado") : ""%>">
                
                <label for="diasVacaciones">Días de Vacaciones:</label>
                <input type="number" id="diasVacaciones" name="diasVacaciones" required readonly>

                <label for="fechaInicio">Fecha de Inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" required oninput="calcularDiasVacaciones()">

                <label for="fechaFin">Fecha de Fin:</label>
                <input type="date" id="fechaFin" name="fechaFin" required oninput="calcularDiasVacaciones()">

                <label for="comentario">Comentario:</label>
                <textarea id="comentario" name="comentario" rows="3"></textarea>
                
                <input type="hidden" name="accion" value="solicitar_Vacaciones">
                <button type="submit">Enviar Solicitud</button>
            </form>
        </div>

        <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-top: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </div>
</body>
</html>
