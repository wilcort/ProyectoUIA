<<%-- 
    Document   : nuevo
    Created on : Apr 8, 2024, 6:02:16 PM
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
        <form action="SvColaborador" method="POST" autocomplete="off">
          
            <label for="cargo_Usuario">Cargo:</label>
            <select id="cargo_Usuario" name="cargo_Usuario">
                <c:forEach var="cargo" items="${cargos}">
                    <option value="${cargo.idCargo}">${cargo.nombreCargo}</option>
                </c:forEach>
            </select><br><br>
   <%--          
            
            <label for="cargo_Usuarioo">Cargo:</label>
            <select id="cargo_Usuario" name="cargo_Usuario">
                <option value="Administrador">Administrador</option>
                <option value="Vendedor">Vendedor</option>
                <option value="IT">IT</option>
            </select><br><br>
 --%>
            <label for="estado_cargoUsuario">Estado Cargo:</label>
            <select id="estado_cargoUsuario" name="estado_cargoUsuario">
                <option value="" disabled selected>Escoja opción</option>
                <option value="1">Activo</option>
                <option value="0">Inactivo</option>
            </select><br><br>

            <label for="nombreUsuario">Nombre de Usuario:</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

            <label for="claveUsuario">Clave:</label>
            <input type="password" id="claveUsuario" name="claveUsuario" required><br><br>

            <label for="num_documento">Número Documento:</label>
            <input id="num_documento" name="num_documento" type="text" required/><br><br>

            <label for="nombre">Nombre:</label>
            <input id="nombre" name="nombre" type="text" required/><br><br>

            <label for="apellido_1">Primer Apellido:</label>
            <input id="apellido_1" name="apellido_1" type="text" required/><br><br>

            <label for="apellido_2">Segundo Apellido:</label>
            <input id="apellido_2" name="apellido_2" type="text" required/><br><br>

            <label for="telefono">Teléfono:</label>
            <input id="telefono" name="telefono" type="text" required/><br><br>

            <label for="direccion">Dirección:</label>
            <input id="direccion" name="direccion" type="text" required/><br><br>
            
            <label for="fecha_contratacion">Fecha de Contratación:</label>
            <input type="date" id="fecha_contratacion" name="fecha_contratacion" required>
            <br/><br/>

            <!-- Campo para el salario base -->
            <label for="salario_base">Salario Base:</label>
            <input type="number" id="salario_base" name="salario_base" step="0.01" required>
            <br/><br/>

            <input type="hidden" name="accion" value="insertar">

            <button id="guardar" name="guardar" type="submit">Guardar</button>
        </form>
    </body>
</html>