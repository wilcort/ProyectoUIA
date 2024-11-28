<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitud Enviada</title>
        <link rel="stylesheet" href="styles.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;  /* Cambié el 60vh por 100vh para que ocupe toda la pantalla */
                margin: 0;
            }
            .container {
                max-width: 500px;
                padding: 20px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                border-radius: 8px;
                text-align: center;
                width: 100%; /* Se asegura de que ocupe todo el espacio disponible dentro del contenedor */
            }
            h1 {
                color: #333;
                font-size: 24px;  /* Tamaño de fuente ajustado */
            }
            p {
                margin-top: 10px;
                color: #555;
                font-size: 16px;  /* Tamaño de fuente ajustado */
            }
            .btn {
                width: 100%;
                padding: 10px;
                margin-top: 20px; /* Aumenté el margen superior */
                border: none;
                color: white;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
                transition: background-color 0.3s, transform 0.2s; /* Añadí transición para el efecto de hover */
            }
            .btn-regresar {
                background-color: #e91e63;
            }

            .btn:hover {
                opacity: 0.9;
                transform: scale(1.05); /* Efecto de agrandar el botón al hacer hover */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Solicitud Enviada con Éxito</h1>
            <p>Tu solicitud de incapacidad ha sido enviada exitosamente.</p>

            <button class="btn btn-regresar" onclick="location.href = '/admingLog/vistasLog/empleado.jsp'">Regresar</button>
        </div>
    </body>
</html>

