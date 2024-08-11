<%-- 
    Document   : administrador
    Created on : Mar 29, 2024, 11:17:33 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador</title>
        <style>
            .container {
                text-align: center;
                margin-top: 50px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                margin: 10px;
                text-decoration: none;
                color: white;
                border-radius: 5px;
                font-weight: bold;
            }
            .btn-primary {
                background-color: #007bff;
            }
            .btn-secondary {
                background-color: #6c757d;
            }
        </style>
    </head>
    <body>
        <h1>Hello Administrador!</h1>
        
        <div class="container">
            <h1>Bienvenido a mi Aplicación</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <a href="/admingLog/SvColaborador" class="btn btn-primary">Manejo de Empleados</a>
            <a href="/" class="btn btn-secondary" class="btn btn-secondary">Manejo Cargos</a>
        </div>
        
    </body>
</html>

