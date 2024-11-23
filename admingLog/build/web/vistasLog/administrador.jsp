<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Administrador</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: linear-gradient(135deg, #74ebd5 0%, #9face6 100%);
                color: #333;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                flex-direction: column;
            }
            .container {
                text-align: center;
                padding: 40px;
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 12px;
                box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2);
                max-width: 600px;
                width: 90%;
            }
            h1 {
                font-size: 26px;
                color: #4a4a4a;
                margin-bottom: 20px;
            }
            p {
                font-size: 16px;
                color: #666;
                margin-bottom: 30px;
            }
            .btn {
                display: inline-block;
                padding: 12px 20px;
                margin: 10px;
                font-size: 16px;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                font-weight: bold;
                cursor: pointer;
                border: none;
                transition: background-color 0.3s ease, transform 0.2s ease;
                width: 200px;
            }
            .btn-one {
                background-color: #5cb4ff;
            }
            .btn-one:hover {
                background-color: #007bff;
                transform: translateY(-2px);
            }
            .btn-second {
                background-color: #a3a9b4;
            }
            .btn-second:hover {
                background-color: #6c757d;
                transform: translateY(-2px);
            }
            .btn-third {
                background-color: #5ccc8e;
            }
            .btn-third:hover {
                background-color: #28a745;
                transform: translateY(-2px);
            }
            .btn-fourth {
                background-color: #b77ff7;
            }
            .btn-fourth:hover {
                background-color: #6f42c1;
                transform: translateY(-2px);
            }
            .btn-fifth {
                background-color: #f39c12;
                width: 240px;
                margin-right: 50px;

            }
            .btn-fifth:hover {
                background-color: #e67e22;
                transform: translateY(-2px);
            }
            .btn-sixth {
                background-color: #e74c3c;
            }
            .btn-sixth:hover {
                background-color: #c0392b;
                transform: translateY(-2px);
            }
            .btn-seventh {
                background-color: #8e44ad;
            }
            .btn-seventh:hover {
                background-color: #9b59b6;
                transform: translateY(-2px);
            }
            .btn-extra {
                background-color: #f39c12;
            }
            .btn-extra:hover {
                background-color: #e67e22;
                transform: translateY(-2px);
            }
            .btn-brands {
                background-color: #3498db;
            }
            .btn-brands:hover {
                background-color: #2980b9;
                transform: translateY(-2px);
            }
            .btn-danger {
                background-color: #c0392b;
                margin-top: -30px;
            }
            .btn-danger:hover {
                background-color: #dc3545;
                transform: translateY(-2px);
            }
            form {
                display: inline;
            }
            .button-container {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                gap: 20px;
                margin-bottom: 20px;
            }
            .btn-logout-container {
                position: fixed;
                bottom: 20px;
                left: 50%;
                transform: translateX(-50%);
                width: auto;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Bienvenido, Administrador</h1>
            <p>Bienvenido al sistema de RRHH. Por favor, seleccione una opción:</p>

            <!-- Botones organizados en una cuadrícula -->
            <div class="button-container">
                <a href="/admingLog/SvColaborador" class="btn btn-one">Empleados</a>
                <a href="/admingLog/SvCargo" class="btn btn-second">Cargos</a>
                <a href="/admingLog/SvHorarios" class="btn btn-third">Horarios</a>

                <form action="/admingLog/SvIncapacidades?accion=Ver_Solicitudes_Incap" method="POST">
                    <input type="hidden" name="id_Empleado" value="${sessionScope.id_empleado}">
                    <button type="submit" class="btn btn-fifth">Incapacidades</button>
                </form>

                <form action="/admingLog/SvMostrarDatos" method="POST">
                    <input type="hidden" name="accion" value="Ver_Empleados">
                    <button type="submit" class="btn btn-brands">Marcas</button>
                </form>

                <form action="/admingLog/Svplanilla" method="GET">              
                    <input type="hidden" name="accion" value="Listar_Empleados">
                    <button type="submit" class="btn btn-seventh">Planilla</button>
                </form>

                <a href="/admingLog/SvAguinaldo" class="btn btn-sixth">Aguinaldo</a>

                <a href="/admingLog/SvLiquidaciones" class="btn btn-fourth">Liquidaciones</a>
                <a href="/admingLog/SvHorasExtra" class="btn btn-extra">Horas Extra</a>


            </div>

            <form action="/admingLog/SvVacaciones?accion=Ver_Vacaciones_Empleados" method="POST">
                <input type="hidden" name="id_Empleado" value="${sessionScope.id_empleado}">
                <button type="submit" class="btn btn-fourth">Vacaciones</button>
            </form>
        </div>

        <!-- Botón "Salir" fuera del cuadro principal -->
        <div class="btn-logout-container">
            <form action="/admingLog/SvLogin?accion=verificar" method="POST">
                <button type="submit" class="btn btn-danger">Salir</button>
            </form>
        </div>
    </body>
</html>
