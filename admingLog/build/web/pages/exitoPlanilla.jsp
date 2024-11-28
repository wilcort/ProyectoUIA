<%-- 
    Document   : exitoPlanilla
    Created on : Nov 22, 2024, 12:19:59 PM
    Author     : Dell
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Éxito</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .container {
                max-width: 500px;
                padding: 20px;
                background-color: #fff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                text-align: center;
                width: 100%;
            }
            h2 {
                color: #333;
                font-size: 24px;
                margin-bottom: 20px;
            }
            a {
                display: inline-block;
                padding: 10px 20px;
                color: #fff;
                background-color: #4CAF50;
                text-decoration: none;
                border-radius: 4px;
                font-size: 16px;
                margin-top: 20px;
                transition: background-color 0.3s;
            }
            a:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Planilla insertada exitosamente.</h2>
            <a href="http://localhost:8080/admingLog/Svplanilla?accion=Listar_Empleados">Volver a la Página Principal</a>
        </div>
    </body>
</html>
