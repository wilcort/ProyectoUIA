<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Formulario de Marcas</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            h1 {
                color: #333;
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin: 10px 0 5px;
            }

            input[type="date"],
            input[type="time"] {
                width: 80%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button {
                padding: 10px 15px;
                background-color: #5cb85c;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button:hover {
                background-color: #4cae4c;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table, th, td {
                border: 1px solid #ccc;
            }

            th, td {
                padding: 10px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
            }

            .no-data {
                text-align: center;
                font-style: italic;
            }
        </style>
    </head>
    <body>

        <h1>Registrar Marcas de Empleado</h1>

        <!-- Formulario para realizar marcas -->
        <form action="SvMostrarDatos" method="post" onsubmit="guardarMarcas()">
            <input type="hidden" name="accion" value="realizar_Marca">


            <label for="fecha_marca">Fecha de la Marca:</label>
            <input type="date" id="fecha_marca" name="fecha_marca" required>

            <label for="hora_entrada">Hora de Entrada:</label>
            <input type="time" id="hora_entrada" name="hora_entrada" >

            <label for="hora_salida">Hora de Salida:</label>
            <input type="time" id="hora_salida" name="hora_salida" >

            <label for="hora_entrada_almuerzo">Hora de Entrada del Almuerzo:</label>
            <input type="time" id="hora_entrada_almuerzo" name="hora_entrada_almuerzo" >

            <label for="hora_salida_almuerzo">Hora de Salida del Almuerzo:</label>
            <input type="time" id="hora_salida_almuerzo" name="hora_salida_almuerzo" >

            <button type="submit">Registrar Marca</button>
        </form>
        <!-- Formulario para regresar a la página principal -->
        <form action="http://localhost:8080/admingLog/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>

        <!-- Mensaje de éxito/error -->
        <div>
            <p style="color: green;">${mensaje}</p>
        </div>

        <!-- Tabla para mostrar marcas almacenadas -->
        <h2>Marcas Registradas</h2>
        <table>
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Hora de Entrada</th>
                    <th>Hora de Salida</th>
                    <th>Hora de Entrada Almuerzo</th>
                    <th>Hora de Salida Almuerzo</th>
                </tr>
            </thead>
            <tbody>
            <c:if test="${not empty marcas}">
                <c:forEach var="marca" items="${marcas}">
                    <tr>
                        <td>${marca.fechaMarca}</td>
                        <td>${marca.marcaEntrada}</td>
                        <td>${marca.marcaSalida}</td>
                        <td>${marca.marcaEntradaAlmuerzo}</td>
                        <td>${marca.marcaSalidaAlmuerzo}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty marcas}">
                <tr>
                    <td colspan="5" class="no-data">No hay marcas registradas.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>
