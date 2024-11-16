<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Marcas del Mes</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            margin: 20px auto;
            max-width: 1200px;
        }
        h1 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Marcas del Mes</h1>
        <table>
            <thead>
                <tr>
                    <th>ID Marca</th>
                    <th>Fecha Marca</th>
                    <th>Hora Entrada</th>
                    <th>Hora Salida</th>
                    <th>Hora Salida Almuerzo</th>
                    <th>Hora Entrada Almuerzo</th>
                    <th>Horas Día Normales</th>
                    <th>Horas Día Extra</th>
                    <th>ID Empleado</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="marca" items="${listaMarcas}">
                <tr>
                    <td>${marca.idMarca}</td>
                    <td>${marca.fechaMarca}</td>
                    <td>${marca.marcaEntrada}</td>
                    <td>${marca.marcaSalida}</td>
                    <td>${marca.marcaSalidaAlmuerzo}</td>
                    <td>${marca.marcaEntradaAlmuerzo}</td>
                    <td>${marca.horasDiaNormal}</td>
                    <td>${marca.horasDiaExtra}</td>
                    <td>${marca.idEmpleado}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
