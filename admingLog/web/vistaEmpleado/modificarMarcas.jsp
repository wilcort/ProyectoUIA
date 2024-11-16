<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Marca</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            font-size: 24px;
            color: #007bff;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
            color: #333;
        }

        input {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 100%;
        }

        button {
            padding: 10px;
            font-size: 16px;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        .back-btn {
            margin-top: 20px;
            text-align: center;
        }

        .back-btn button {
            background-color: #6c757d;
        }

        .back-btn button:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Modificar Marca</h1>
        <form action="/admingLog/SvMostrarDatos" method="POST">
            <input type="hidden" name="accion" value="Guardar_Modificacion">
            <input type="hidden" name="id_empleado" value="${id_empleado}" />
            <input type="hidden" name="id_marca" value="${marca.idMarca}" />

            <label for="fechaMarca">Fecha:</label>
            <input type="date" name="fechaMarca" value="${marca.fechaMarca}" required>

            <label for="horaEntrada">Hora Entrada:</label>
            <input type="time" name="horaEntrada" value="${marca.marcaEntrada}" required>

            <label for="horaSalida">Hora Salida:</label>
            <input type="time" name="horaSalida" value="${marca.marcaSalida}" required>

            <label for="horaSalidaAlmuerzo">Hora Salida Almuerzo:</label>
            <input type="time" name="horaSalidaAlmuerzo" value="${marca.marcaSalidaAlmuerzo}" required>

            <label for="horaEntradaAlmuerzo">Hora Entrada Almuerzo:</label>
            <input type="time" name="horaEntradaAlmuerzo" value="${marca.marcaEntradaAlmuerzo}" required>

           <button type="submit" formaction="pages/exitoMarcasUpdate.jsp">Guardar Cambios</button>
          </form>
        <div class="back-btn">
            <form action="/admingLog/SvMostrarDatos" method="POST">
                <input type="hidden" name="accion" value="Ver_Empleados">
                <button type="submit">Regresar</button>
            </form>
        </div>
    </div>
</body>
</html>
