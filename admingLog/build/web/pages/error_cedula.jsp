<%-- 
    Document   : errores
    Created on : Aug 30, 2024, 8:29:00 PM
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
            a, .btn-regresar {
                display: inline-block;
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                margin-top: 20px;
                text-decoration: none;
                border-radius: 4px;
                cursor: pointer;
            }
            a:hover, .btn-regresar:hover {
                background-color: #45a049;
            }
            .btn-regresar {
                background-color: #f44336;
            }
            .btn-regresar:hover {
                background-color: #e53935;
            }
        </style>
    </head>
    <body>
        <h2>Numero de Documento ya Ingresado.</h2>
        <a href="SvColaborador?accion=nuevo">Ingresar Nuevo Colaborador</a>
        <!-- Botón Regresar -->
        <button class="btn-regresar" onclick="window.history.back()">Regresar</button>
    </body>
</html>
