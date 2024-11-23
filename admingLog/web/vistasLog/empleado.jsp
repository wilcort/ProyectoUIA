<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                width: 80%;
                max-width: 900px;
                margin: 50px auto;
                background-color: #fff;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
                text-align: center;
            }

            h1 {
                color: #006d77;
                margin-bottom: 20px;
                font-size: 28px;
            }

            p {
                color: #555;
                font-size: 16px;
                margin-bottom: 20px;
            }

            .btn {
                display: inline-block;
                padding: 12px 20px;
                margin: 12px;
                text-decoration: none;
                color: white;
                border-radius: 5px;
                font-weight: bold;
                transition: background-color 0.3s, transform 0.3s;
                cursor: pointer;
                font-size: 16px;
            }

            .btn:hover {
                transform: scale(1.05);
            }

            .btn-blue {
                background-color: #007bff;
            }

            .btn-blue:hover {
                background-color: #0056b3;
            }

            .btn-green {
                background-color: #38a169;
            }

            .btn-green:hover {
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

            .group-container {
                margin-top: 20px;
                padding: 15px;
                border-top: 1px solid #ddd;
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 10px;
            }
            .btn-teal {
                background-color: #008080; /* Teal */
                color: white;
            }

            .btn-teal:hover {
                background-color: #006666;
            }

            .modal {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.4);
                padding-top: 60px;
            }

            .modal-content {
                background-color: #fff;
                margin: 5% auto;
                padding: 20px;
                border: 1px solid #ccc;
                width: 80%;
                max-width: 400px;
                border-radius: 10px;
            }

            .close {
                color: #888;
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
        <script>
            function openModal() {
                document.getElementById("modal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("modal").style.display = "none";
            }

            function submitForm() {
                document.getElementById("marcasForm").submit();
                closeModal();
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Bienvenido Administrador</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <p>ID de Usuario: <%= session.getAttribute("id_usuario")%></p>

            <!-- Botones principales -->
            <a href="/admingLog/SvMostrarDatos?accion=Ver_Empleado&id_usuario=<%= session.getAttribute("id_usuario")%>" class="btn btn-blue">Ver Datos del Empleado</a>

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

            <!-- Grupo de botones relacionados con las marcas -->
            <div class="group-container">
                <form action="/admingLog/SvMostrarDatos" method="POST">
                    <input type="hidden" name="accion" value="ver_Marcas">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="button" class="btn btn-blue" onclick="openModal()">Ver Marcas Del Empleado</button>
                </form>

                <form action="/admingLog/SvMostrarDatos" method="GET">
                    <input type="hidden" name="accion" value="realizar_Marca">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                    <button type="submit" class="btn btn-red">Realizar Marcas</button>
                </form>

                <form action="/admingLog/SvMostrarDatos" method="GET">
                    <input type="hidden" name="accion" value="ver_Todas_Marcas">
                    <button type="submit" class="btn btn-green">Ver Todas las Marcas</button>
                </form>
            </div>

            <!-- Botón de Salir -->
            <form action="/admingLog/SvLogin?accion=verificar" method="POST" class="btn-logout">
                <button type="submit" class="btn btn-red">Salir</button>
            </form>
        </div>

        <!-- Modal -->
        <div id="modal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <form id="marcasForm" action="/admingLog/SvMostrarDatos" method="POST">
                    <input type="hidden" name="accion" value="ver_Marcas">
                    <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">

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

                    <label for="quincena">Quincena:</label>
                    <select name="quincena" id="quincena">
                        <option value="1">Primera Quincena</option>
                        <option value="2">Segunda Quincena</option>
                    </select>

                    <button type="button" onclick="submitForm()" class="btn btn-blue">Consultar Marcas</button>
                </form>
            </div>
        </div>
    </body>
</html>