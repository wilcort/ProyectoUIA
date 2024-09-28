<%-- 
    Document   : nuevoCagos
    Created on : Aug 10, 2024, 8:25:27 PM
    Author     : Dell
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Colaborador</title>
    </head>
    <body>
        <h2>Nuevo Registro</h2>
        <form action="SvCargo" method="POST" autocomplete="off">
          
            
            <label for="nombre_cargo">Nuevo Cargo</label>
            <input type="text" id="nombre_cargo" name="nombre_cargo" required><br><br>

            <label for="estado">Estado Cargo:</label>
            <select id="estado" name="estado">
                <option value="" disabled selected>Escoja opción</option>
                <option value="1">Activo</option>
                <option value="0">Desactivado</option>
            </select><br><br>
            <input type="hidden" name="accion" value="insertarCargo">
            <button id="guardar" name="guardar" type="submit">Guardar</button>
        </form>
        <form action="http://localhost:8080/admingLog/SvCargo" style="display: inline; margin-left: 10px;">
                <button type="submit">Regresar</button>
        </form>        
    </body>
</html>