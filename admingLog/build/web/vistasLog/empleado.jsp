<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vista Empleado</title>
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f4f4f9;
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
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
                position: relative; /* Para que el botón Salir se coloque en relación a este contenedor */
            }

            h1 {
                color: #333;
                margin-bottom: 20px;
                font-size: 24px;
            }

            p {
                color: #666;
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

            .btn-one {
                background-color: #007bff;
            }

            .btn-one:hover {
                background-color: #0056b3;
            }

            .btn-second {
                background-color: #6c757d;
            }

            .btn-second:hover {
                background-color: #5a6268;
            }

            .btn-third {
                background-color: #28a745;
            }

            .btn-third:hover {
                background-color: #218838;
            }

            .btn-fourth {
                background-color: #f39c12;
            }

            .btn-fourth:hover {
                background-color: #e67e22;
            }

            .btn-fifth {
                background-color: #e74c3c;
            }

            .btn-fifth:hover {
                background-color: #c0392b;
            }

            .btn-danger {
                background-color: red;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s;
                width: auto;
                font-size: 16px;
                margin-top: 20px; 
                position: relative;
                top: 20px;/* Espacio para separar el botón del contenido */
            }

            .btn-danger:hover {
                background-color: #c0392b;
            }

            form {
                display: inline-block;
                margin: 5px;
            }

            /* Estilo para el botón Salir en la parte inferior */
            .btn-logout {
                position: absolute;
                bottom: 450px;
                left: 50%;
                transform: translateX(-50%); /* Centra el botón */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Bienvenido Administrador</h1>
            <p>Bienvenido al sistema de RRHH</p>
            <p>ID de Usuario: <%= session.getAttribute("id_usuario")%></p> <!-- Muestra el id_usuario aquí -->

            <a href="/admingLog/SvMostrarDatos?accion=Ver_Empleado&id_usuario=<%= session.getAttribute("id_usuario")%>" class="btn btn-one">Ver Datos del Empleado</a>

            <a href="" class="btn btn-second">Ver Horas Extra</a> 
         
            
            <form action="/admingLog/SvIncapacidades" method="POST">
                <input type="hidden" name="accion" value="Ver_Formulario">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                <button type="submit" class="btn btn-fourth">Solicitud de Incapacidades</button>
            </form>

            <form action="/admingLog/SvVacaciones" method="POST">
                <input type="hidden" name="accion" value="Ver_Datos">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                <button type="submit" class="btn btn-third">Solicitar Vacaciones</button>
            </form>

            <form action="/admingLog/SvMostrarDatos" method="POST">
                <input type="hidden" name="accion" value="ver_Marcas">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                <button type="submit" class="btn btn-one">Ver Marcas Del Empleado</button>
            </form>

            <form action="/admingLog/SvMostrarDatos" method="GET">
                <input type="hidden" name="accion" value="realizar_Marca">
                <input type="hidden" name="id_usuario" value="<%= session.getAttribute("id_usuario")%>">
                <button type="submit" class="btn btn-one">Realizar Marcas</button>
            </form>
        </div>

        <!-- Botón de Salir en la parte inferior -->
        <form action="/admingLog/SvLogin?accion=verificar" method="POST" class="btn-logout">
            <button type="submit" class="btn btn-danger">Salir</button>
        </form>
    </body>
</html>
