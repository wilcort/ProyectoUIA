<%-- 
    Document   : exitoAguinaldo
    Created on : Nov 24, 2024, 9:38:01 AM
    Author     : Dell
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Éxito</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f7fc;
                margin: 0;
                padding: 0;
                text-align: center;
                padding-top: 20px;
            }
            h2 {
                color: #333;
            }
            a, .btn-volver {
                display: inline-block;
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                margin-top: 20px;
                text-decoration: none;
                border-radius: 4px;
                cursor: pointer;
            }
            a:hover, .btn-volver:hover {
                background-color: #45a049;
            }
            .btn-volver {
                background-color: #f44336;
            }
            .btn-volver:hover {
                background-color: #e53935;
            }
        </style>
    </head>
    <body>
        <h2>Aguinaldo generado exitosamente.</h2>
        <!-- Enlace original -->
        <a href="http://localhost:8080/admingLog/SvAguinaldo?accion=Listar_Empleados">Volver a la Página Principal</a>
        <!-- Botón Volver -->
        <button class="btn-volver" onclick="window.location.href='http://localhost:8080/admingLog/SvAguinaldo?accion=Listar_Empleados'">Volver a la Página Principal</button>
    </body>
</html>