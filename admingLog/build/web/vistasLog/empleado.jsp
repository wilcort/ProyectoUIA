<%-- 
    Document   : vendedor
    Created on : Mar 29, 2024, 11:17:14 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista Empleado</title>
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
            .btn-third {
                background-color: lawngreen;
            }
            .btn-fourth {
                background-color: #f39c12;
            }
            .btn-fifth {
                background-color: peru;   
            }
            .btn-danger {
                background-color: red;
                color: white;
                border: none;
                cursor: pointer;
                padding: 10px 20px;
                border-radius: 5px;
            }
            
           
        </style>
    </head>
    <body>
        <h1>Hello Administrador!</h1>
        

        <div class="container">
            <h1>Bienvenido a mi Aplicación</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <p>ID de Usuario : <%= session.getAttribute("id_usuario") %></p> <!-- Muestra el id_usuario aquí -->
            <a href="/admingLog/SvMostrarDatos?accion=Ver_Empleado&id_usuario=<%= session.getAttribute("id_usuario") %>" class="btn btn-one">Ver Datos del Empleado</a>
   
            <a href="" class="btn btn-second">Ver Horas Extra</a>
            <a href="" class="btn btn-third">Solicitar Vacaciones</a>
            <a href="" class="btn btn-fourth">Solicitud de Incapacidades</a>
            
             <form action="/admingLog/SvMostrarDatos" method="POST" style="display: inline;">
                <input type="hidden" name="accion" value="ver_Marcas">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario") %>"> <!-- Campo oculto para el ID de usuario -->
                <button type="submit" class="btn btn-one">Ver Marcas Del Empleado</button>
            </form>
                
             <form action="/admingLog/SvMostrarDatos" method="GET" style="display: inline;">
                <input type="hidden" name="accion" value="realizar_Marca">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario") %>"> <!-- Campo oculto para el ID de usuario -->
                <button type="submit" class="btn btn-one">Realizar Marcas</button>
            </form>
            
            <form action="/admingLog/SvLogin?accion=verificar" method="POST">
                <button type="submit" class="btn btn-danger">Salir</button>
            </form>
        </div>
    </body>
</html>
