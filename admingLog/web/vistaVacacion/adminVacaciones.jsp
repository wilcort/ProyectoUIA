<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Vacaciones de Empleado</title>
        <style>
            /* Estilos generales */
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                color: #333;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column; /* Cambiado a columna para alinear todo */
                align-items: center;
                height: 100vh; /* Uso del 100% de la altura de la ventana */
            }
            .container {
                width: 80%;
                max-width: 600px;
                background-color: #fff;
                padding: 20px;
                border-radius: 20px; /* Radio de borde reducido */
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px; /* Espacio entre la información y el formulario */
            }
            h1 {
                text-align: center;
                color: #4CAF50;
                font-size: 1.8em;
                margin-bottom: 20px;
            }
            .info-empleado {
                line-height: 1.6;
                padding: 10px;
                font-size: 1.1em;
            }
            .info-empleado p {
                margin: 10px 0;
            }
            .info-empleado strong {
                color: #333;
            }
            .form-container {
                width: 80%;
                max-width: 600px;
                background-color: #fff;
                padding: 20px;
                border-radius: 20px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            h2 {
                text-align: center;
                color: #4CAF50;
                margin-bottom: 15px;
            }
            label {
                display: block;
                margin-top: 10px;
            }
            input[type="text"],
            input[type="number"],
            input[type="date"],
            textarea {
                width: 100%;
                padding: 8px;
                margin-top: 5px;
                border-radius: 5px;
                border: 1px solid #ccc;
                box-sizing: border-box; /* Asegura que el padding no afecte el tamaño */
            }
            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                width: 100%;
                margin-top: 15px;
                font-size: 1em;
            }
            button:hover {
                background-color: #45a049; /* Cambia el color al pasar el mouse */
            }
            /* Estilos para dispositivos móviles */
            @media (max-width: 600px) {
                .container,
                .form-container {
                    width: 95%;
                    padding: 15px;
                }
                h1, h2 {
                    font-size: 1.5em;
                }
                .info-empleado {
                    font-size: 1em;
                }
            }
        </style>
        <script>
            function calcularDiasVacaciones() {
                const fechaInicio = new Date(document.getElementById('fechaInicio').value);
                const fechaFin = new Date(document.getElementById('fechaFin').value);

                if (fechaInicio instanceof Date && !isNaN(fechaInicio) &&
                        fechaFin instanceof Date && !isNaN(fechaFin)) {

                    const diferenciaMilisegundos = fechaFin - fechaInicio;
                    const diasVacaciones = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1;
                    document.getElementById('diasVacaciones').value = diasVacaciones >= 0 ? diasVacaciones : 0;
                } else {
                    document.getElementById('diasVacaciones').value = '';
                }
            }

            window.onload = function () {
                const today = new Date().toISOString().split("T")[0];
                document.getElementById("fechaInicio").setAttribute("min", today);

                document.getElementById("fechaInicio").addEventListener("input", function () {
                    const fechaInicio = this.value;
                    document.getElementById("fechaFin").setAttribute("min", fechaInicio);
                    calcularDiasVacaciones();
                });
            };
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Información de Vacaciones</h1>

            <!-- Mostrar datos del empleado -->
            <div class="info-empleado">
                <p><strong>Nombre del Empleado:</strong> ${nombreEmpleado} ${apellidoEmpleado}</p>
                <p><strong>Fecha de Contratación:</strong> ${fechaContratacion}</p>
              <!--  <p><strong>Días de Vacaciones Disponibles:</strong> ${diasVacacionesTotal}</p> -->
                <p>Días de Vacaciones Disponibles: ${diasVacacionesTotal}</p>

            </div>
        </div>

        <div class="form-container">
            <h2>Solicitar Vacaciones</h2>
            <form action="SvVacaciones" method="post">
                <!-- Mostrar ID de Empleado -->
                <label>ID del Empleado:</label>
                <input type="text" name="id_empleado" value="${sessionScope.id_empleado != null ? sessionScope.id_empleado : 'ID no disponible'}" readonly>

                <!-- Campos de Solicitud de Vacaciones -->
                <label for="diasVacaciones">Días de Vacaciones:</label>
                <input type="number" id="diasVacaciones" name="diasVacaciones" required readonly>

                <label for="fechaInicio">Fecha de Inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" required oninput="calcularDiasVacaciones()">

                <label for="fechaFin">Fecha de Fin:</label>
                <input type="date" id="fechaFin" name="fechaFin" required oninput="calcularDiasVacaciones()">

                <label for="comentario">Comentario:</label>
                <textarea id="comentario" name="comentario" rows="3"></textarea>

                <input type="hidden" name="accion" value="Realizar_Solicitud">                           
                <button type="submit">Enviar Solicitud</button>
            </form>
            
                <form action="SvVacaciones" method="POST">
                    <input type="hidden" name="accion" value="Ver_Solicitudes">                   
                    <input type="hidden" name="id_empleado" value="${sessionScope.id_empleado != null ? sessionScope.id_empleado : 'ID no disponible'}" readonly>
                    <button type="submit">Ver Solicitudes</button>
                </form>
                  
            <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline; margin-top: 10px;">
                <button type="submit">Regresar</button>
            </form>

        </div>    
    </body>

</html>
