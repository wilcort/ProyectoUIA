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
            .btn-one {
                background-color: #007bff;
            }
            .btn-second {
                background-color: #6c757d;
            }
            .btn-third{              
                background-color: lawngreen;          
            }
        </style>
    </head>
    <body>
        <h1>Hello Administrador!</h1>

        <div class="container">
            <h1>Bienvenido a mi Aplicación</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <a href="/admingLog/SvColaborador" class="btn btn-one">Manejo de Empleados</a>
            <a href="/admingLog/SvCargo" class="btn btn-second" >Manejo Cargos</a>
            <a href="/admingLog/SvHorarios" class="btn btn-third" >Manejo Horarios</a>
            <form action="/admingLog/SvLogin?accion=verificar" method="POST">
                <button type="submit" class="btn btn-danger">Salir</button>
            </form>
        </div>

    </body>
</html>