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

        #fecha-seleccionada {
            margin-top: 20px;
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>
    <h1>Registro de Marcas</h1>

    <form action="SvMostrarDatos" method="post">
        <input type="hidden" name="accion" value="realizar_Marca">
        <p>ID de Usuario: <%= session.getAttribute("id_usuario") %></p>
        <p>ID del Empleado: <%= request.getAttribute("idEmpleado") != null ? request.getAttribute("idEmpleado") : "No encontrado" %></p>

        <!-- Fecha de Marca -->
        <label for="fecha_marca">Fecha de Marca:</label>
        <input type="date" id="fecha_marca" name="fecha_marca" required>

        <!-- Hora de Entrada -->
        <label for="hora_entrada">Hora de Entrada:</label>
        <input type="time" id="hora_entrada" name="hora_entrada" required>

        <!-- Hora de Salida (opcional) -->
        <label for="hora_salida">Hora de Salida:</label>
        <input type="time" id="hora_salida" name="hora_salida">

        <!-- Hora de Entrada Almuerzo (opcional) -->
        <label for="hora_entrada_almuerzo">Hora de Entrada Almuerzo:</label>
        <input type="time" id="hora_entrada_almuerzo" name="hora_entrada_almuerzo">

        <!-- Hora de Salida Almuerzo (opcional) -->
        <label for="hora_salida_almuerzo">Hora de Salida Almuerzo:</label>
        <input type="time" id="hora_salida_almuerzo" name="hora_salida_almuerzo">

        <!-- Botón para enviar el formulario -->
        <button type="submit" id="submit-btn">Registrar Marca Manual</button>
    </form>

    <!-- Mostrar mensaje si ya existe una marca -->
    <c:if test="${not empty mensaje}">
        <div style="color: red;">${mensaje}</div>
    </c:if>
</body>
</html>
