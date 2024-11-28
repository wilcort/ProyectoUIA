<%-- 
    Document   : pagina_exito
    Created on : Aug 30, 2024, 8:01:23 PM
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
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                max-width: 500px;
                padding: 20px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                border-radius: 8px;
                text-align: center;
            }
            h2 {
                color: #333;
                margin-bottom: 20px;
            }
            .btn {
                width: 100%;
                padding: 10px;
                margin-top: 20px;
                border: none;
                color: white;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
                transition: background-color 0.3s;
            }
            .btn-regresar {
                background-color: #4CAF50;
            }
            .btn-regresar:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Colaborador Insertado Exitosamente</h2>
            <button class="btn btn-regresar" onclick="location.href='http://localhost:8080/admingLog/SvColaborador'">Volver a la Lista de Colaboradores</button>
        </div>
    </body>
</html>
