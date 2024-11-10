<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Denegación de Incapacidad</title>
        <link rel="stylesheet" href="css/styles.css"> <!-- Aquí puedes agregar tu CSS si tienes uno -->
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                color: #333;
                margin: 0;
                padding: 0;
            }
            .container {
                max-width: 600px;
                margin: 50px auto;
                padding: 30px;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            .error-message {
                font-size: 1.5rem;
                color: red;
                margin-bottom: 20px;
            }
            .button {
                padding: 10px 20px;
                background-color: #007bff;
                color: #fff;
                text-decoration: none;
                border-radius: 5px;
                font-size: 1rem;
            }
            .button:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <h2>¡Incapacidad Denegada!</h2>
            <p class="error-message">La solicitud de incapacidad del empleado <strong>${empleado.nombre} ${empleado.apellido1}</strong> ha sido denegada.</p>

            <p>La solicitud no cumple con los requisitos necesarios para su aprobación.</p>

            <a href="/admingLog/vistasLog/administrador.jsp" class="button">Volver al listado de incapacidades</a>
        </div>

    </body>
</html>
