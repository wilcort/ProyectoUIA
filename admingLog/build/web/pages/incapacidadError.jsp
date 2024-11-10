<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="es">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 70vh;
        }

        .error-container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 30px;
            text-align: center;
            width: 80%;
            max-width: 500px;
        }

        .error-container h1 {
            font-size: 48px;
            color: #ff4d4d;
        }

        .error-container p {
            font-size: 18px;
            color: #333;
            margin: 15px 0;
        }

        .error-container .btn {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }

        .error-container .btn:hover {
            background-color: #0056b3;
        }

    </Style>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Error - Documento no encontrado</title>
        <link rel="stylesheet" href="styles.css"> <!-- Aquí puedes agregar tu archivo de estilos -->
    </head>
    <body>
        <div class="error-container">
            <h1>¡Vaya!</h1>
            <p>El documento que buscas no está disponible en este momento.</p>
            <p>Puede ser que el archivo no esté adjuntado.</p>
            <!-- Generar dinámicamente el enlace con el ID del empleado -->
        </div>
    </body>
</html>
