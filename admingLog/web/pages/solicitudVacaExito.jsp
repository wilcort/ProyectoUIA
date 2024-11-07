<%-- 
    Document   : vacacionesExito
    Created on : Nov 2, 2024, 7:31:11 PM
    Author     : Dell
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitud de Vacaciones </title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
                background-color: #f4f4f4;
                margin: 0; /* Asegura que no haya margen por defecto */
            }
            h1 {
                color: #4CAF50; /* Verde para el mensaje de éxito */
            }
            .button {
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #4CAF50; /* Verde para el botón */
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                text-decoration: none; /* Elimina el subrayado */
            }
            .button:hover {
                background-color: #45a049; /* Color al pasar el mouse */
            }
        </style>
    </head>
    <body>
        <h1>Solicitud de Vacaciones Enviada con Éxito.</h1>



        <form action="/admingLog/vistasLog/empleado.jsp" method="get">
            <input type="hidden" name="accion" value="listar">
            <button type="submit" class="button">Regresar</button>
        </form>      
    </body>
</html>