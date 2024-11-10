<%-- 
    Document   : modificarCargo
    Created on : Aug 10, 2024, 8:25:39 PM
    Author     : Dell
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificar Cargo</title>
        <style>
            form {
                width: 50%;
                margin: 20px auto;
                padding: 10px;
                border: 1px solid #dddddd;
                border-radius: 5px;
                background-color: #f9f9f9;
            }
            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }
            input, select {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #dddddd;
                border-radius: 4px;
            }
            button {
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <h1>Modificar Cargo</h1>

        <form action="SvCargo?accion=actualizar_Cargo" method="post">
            <input type="hidden" name="id_cargo" value="${cargo.idCargo}">
            
            <label for="nombre_cargo">Nombre del Cargo:</label>
            <input type="text" id="nombre_cargo" name="nombre_cargo" value="${cargo.nombreCargo}" required>
            
            <label for="salario">Salario:</label>
            <input type="text" id="salario" name="salario" value="${cargo.salario}" required>
            
            
            <label for="estado">Estado:</label>
            <select id="estado" name="estado">
                <option value="" disabled selected>Seleccione</option>
                <option value="true" ${cargo.estado ? 'selected' : ''}>Activo</option>
                <option value="false" ${!cargo.estado ? 'selected' : ''}>Inactivo</option>
            </select>

            <button type="submit">Actualizar</button>
        </form>
    </body>
</html>