<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ingresar Nuevo Empleado</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f7fc;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .form-container {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 600px;
            }
            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 15px;
            }
            label {
                font-size: 13px;
                color: #333;
                margin-bottom: 5px;
                display: block;
            }
            input[type="text"], input[type="password"], input[type="tel"], input[type="date"], select {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 13px;
            }
            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 18px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                font-size: 15px;
                margin-top: 15px;
            }
            button:hover {
                background-color: #45a049;
            }
            .form-footer {
                text-align: center;
                margin-top: 15px;
                font-size: 12px;
                color: #666;
            }
            .btn-regresar {
                background-color: #f44336;
                color: white;
                padding: 10px 18px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                width: 100%;
                font-size: 15px;
                margin-top: 10px;
            }
            .btn-regresar:hover {
                background-color: #e53935;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Nuevo Registro de Colaborador</h2>
            <form action="SvColaborador" method="POST" autocomplete="off">

                <label for="estado_Usuario">Estado Usuario:</label>
                <select id="estado_Usuario" name="estado_Usuario">
                    <option value="" disabled selected>Escoja opción</option>
                    <option value="1">Activo</option>
                    <option value="0">Inactivo</option>
                </select>

                <label for="nombreUsuario">Nombre de Usuario:</label>
                <input type="text" id="nombreUsuario" name="nombreUsuario" required pattern="^[A-Za-záéíóúÁÉÍÓÚüÜÑñ ]+$" title="El nombre solo debe contener letras">

                <label for="claveUsuario">Clave:</label>
                <input type="password" id="claveUsuario" name="claveUsuario" required>

                <label for="num_documento">Número Documento:</label>
                <input id="num_documento" name="num_documento" type="text" required pattern="^\d+$" title="El número de documento debe contener solo números">

                <label for="nombre">Nombre:</label>
                <input id="nombre" name="nombre" type="text" required pattern="^[A-Za-záéíóúÁÉÍÓÚüÜÑñ ]+$" title="El nombre solo debe contener letras">

                <label for="apellido_1">Primer Apellido:</label>
                <input id="apellido_1" name="apellido_1" type="text" required pattern="^[A-Za-záéíóúÁÉÍÓÚüÜÑñ ]+$" title="El primer apellido solo debe contener letras">

                <label for="apellido_2">Segundo Apellido:</label>
                <input id="apellido_2" name="apellido_2" type="text" required pattern="^[A-Za-záéíóúÁÉÍÓÚüÜÑñ ]+$" title="El segundo apellido solo debe contener letras">

                <label for="telefono">Teléfono:</label>
                <input id="telefono" name="telefono" type="tel" required pattern="^\d+$" title="El teléfono debe contener solo números">

                <label for="direccion">Dirección:</label>
                <input id="direccion" name="direccion" type="text" required>

                <label for="fecha_contratacion">Fecha de Contratación:</label>
                <input type="date" id="fecha_contratacion" name="fecha_contratacion" required>

                <input type="hidden" name="accion" value="insertar">

                <button id="guardar" name="guardar" type="submit">Guardar</button>
                <!-- Botón Regresar -->
                <button type="button" class="btn-regresar" onclick="window.history.back()">Regresar</button>
            </form>
            <div class="form-footer">
                <p>© 2024 Todos los derechos reservados.</p>
            </div>
        </div>
    </body>
</html>

