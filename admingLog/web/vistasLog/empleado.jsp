<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Vista Empleado</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #eaf6f6;
                margin: 0;
                padding: 0;
                color: #333;
            }

            .container {
                width: 90%;
                max-width: 900px;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            }

            h1 {
                color: #006d77;
                font-size: 28px;
                text-align: center;
                margin-bottom: 20px;
            }

            p {
                color: #555;
                font-size: 16px;
                text-align: center;
            }

            .button-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 15px;
                margin-top: 20px;
            }

            .btn {
                padding: 12px 25px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                font-weight: bold;
                text-transform: capitalize;
                color: #fff;
                background-color: #007bff;
                cursor: pointer;
                transition: transform 0.2s ease, background-color 0.3s ease;
                text-decoration: none;
            }

            .btn:hover {
                transform: translateY(-3px);
                background-color: #0056b3;
            }

            .btn-teal {
                background-color: #38a169;
            }

            .btn-teal:hover {
                background-color: #2c7a7b;
            }

            .btn-orange {
                background-color: #ff9800;
            }

            .btn-orange:hover {
                background-color: #e65100;
            }

            .btn-red {
                background-color: #ff5722;
            }

            .btn-red:hover {
                background-color: #d32f2f;
            }

            .btn-purple {
                background-color: #9c27b0;
            }

            .btn-purple:hover {
                background-color: #7b1fa2;
            }

            .group-title {
                text-align: center;
                color: #444;
                font-weight: bold;
                margin-top: 30px;
            }

            .logout-container {
                text-align: center;
                margin-top: 30px;
            }

            /* Modal Styles */
            .modal {
                display: none; /* Hidden by default */
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgb(0,0,0);
                background-color: rgba(0,0,0,0.4);
            }

            .modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 400px;
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Bienvenido Administrador</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <p>ID de Usuario: <%= session.getAttribute("id_usuario")%></p>

            <!-- Botones principales -->
            <div class="button-container">
                <a href="/admingLog/SvMostrarDatos?accion=Ver_Empleado&id_usuario=<%= session.getAttribute("id_usuario")%>" class="btn">Ver Datos del Empleado</a>
                <form action="/admingLog/Svplanilla" method="POST">
                    <input type="hidden" name="accion" value="Ver_Planilla_Empleado">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="submit" class="btn btn-teal">Ver Planilla</button>
                </form>
                <form action="/admingLog/SvIncapacidades" method="POST">
                    <input type="hidden" name="accion" value="Ver_Formulario">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="submit" class="btn btn-orange">Solicitud de Incapacidades</button>
                </form>
                <form action="/admingLog/SvVacaciones" method="POST">
                    <input type="hidden" name="accion" value="Ver_Datos">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="submit" class="btn btn-purple">Solicitar Vacaciones</button>
                </form>
                <form action="/admingLog/SvAguinaldo" method="POST">
                    <input type="hidden" name="accion" value="Mostrar_Aguinaldo">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="submit" class="btn btn-green">Ver Aguinaldo</button>
                </form>
            </div>

            <!-- Grupo de botones relacionados con marcas -->
            <div class="group-title">Gestión de Marcas</div>
            <div class="button-container">
                <form action="/admingLog/SvMostrarDatos" method="POST">
                    <input type="hidden" name="accion" value="ver_Marcas">
                    <button type="button" class="btn" onclick="openModal()">Ver Marcas del Empleado</button>
                </form>
                <form action="/admingLog/SvMostrarDatos" method="GET">
                    <input type="hidden" name="accion" value="realizar_Marca">
                    <button type="submit" class="btn btn-red">Realizar Marcas</button>
                </form>
                <form action="/admingLog/SvMostrarDatos" method="GET">
                    <input type="hidden" name="accion" value="ver_Todas_Marcas">
                    <button type="submit" class="btn btn-green">Ver Todas las Marcas</button>
                </form>
            </div>

            <!-- Botón de Salir -->
            <div class="logout-container">
                <form action="/admingLog/SvLogin?accion=verificar" method="POST">
                    <button type="submit" class="btn btn-red">Salir</button>
                </form>
            </div>
        </div>

        <!-- Modal -->
        <div id="modal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <form id="marcasForm" action="/admingLog/SvMostrarDatos" method="POST">
                    <input type="hidden" name="accion" value="ver_Marcas">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <input type="hidden" name="fecha_actual" id="fecha_actual">

                    <!-- Selección de mes -->
                    <label for="mes">Mes:</label>
                    <select name="mes" id="mes">
                        <option value="01">Enero</option>
                        <option value="02">Febrero</option>
                        <option value="03">Marzo</option>
                        <option value="04">Abril</option>
                        <option value="05">Mayo</option>
                        <option value="06">Junio</option>
                        <option value="07">Julio</option>
                        <option value="08">Agosto</option>
                        <option value="09">Septiembre</option>
                        <option value="10">Octubre</option>
                        <option value="11">Noviembre</option>
                        <option value="12">Diciembre</option>
                    </select>

                    <!-- Selección de quincena -->
                    <label for="quincena">Quincena:</label>
                    <select name="quincena" id="quincena">
                        <option value="1">Primera Quincena</option>
                        <option value="2">Segunda Quincena</option>
                    </select>

                    <button type="submit" class="btn btn-blue">Consultar Marcas</button>
                </form>
            </div>
        </div>         


        <script>
            // Funciones para abrir y cerrar el modal
            function openModal() {
                document.getElementById("modal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("modal").style.display = "none";
            }

            // Establecer fecha actual en el formulario
            document.getElementById("fecha_actual").value = new Date().toISOString().slice(0, 10); // Fecha en formato YYYY-MM-DD
        </script>
    </body>
</html>
