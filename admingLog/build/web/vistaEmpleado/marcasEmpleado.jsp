<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Formulario de Marcas</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            h1 {
                color: #333;
                text-align: center; /* Centrado del t�tulo */
                margin: 0 0 20px 0;
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 500px;
                margin: 0 auto; /* Centrar el formulario */
            }

            label {
                display: block;
                margin: 10px 0 5px;
            }

            input[type="date"],
            input[type="time"] {
                width: 100%; /* Ajuste al 100% para dispositivos peque�os */
                padding: 8px;
                margin-bottom: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button {
                padding: 10px 15px;
                background-color: #5cb85c;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 5px;
                width: 100%; /* Bot�n de ancho completo */
            }

            button:hover {
                background-color: #4cae4c;
            }

            .manual-button {
                margin-left: 10px;
                background-color: #0275d8;
                width: auto; /* No hace falta que sea de ancho completo */
            }

            .manual-button:hover {
                background-color: #025aa5;
            }

            button[disabled] {
                background-color: #ccc;
                cursor: not-allowed;
            }

            form[action="http://localhost:8080/admingLog/vistasLog/empleado.jsp"] {
                display: inline;
                margin-top: 10px;
                text-align: center; /* Centrado del bot�n de regresar */
                 margin-left: 640px;
            }

            /* Media Queries para hacer el dise�o responsive */
            @media (max-width: 768px) {
                form {
                    width: 90%; /* Ajuste del formulario a pantallas m�s peque�as */
                    padding: 15px;   
                }

                label,
                input[type="date"],
                input[type="time"],
                button {
                    width: 100%; /* Ancho completo en pantallas peque�as */
                }

                h1 {
                    font-size: 24px; /* Ajuste del tama�o del t�tulo */
                }
            }

            /* Estilo para el bot�n de regresar */
            .back-button {
                padding: 10px 50px;
                background-color: #0275d8;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 10px;              
                width: auto; /* Ancho ajustado */
                display: inline-block;
            }

            .back-button:hover {
                background-color: #025aa5;
            }

        </style>
    </head>
    <body>
        <h1>Registro de Marcas</h1>

        <!-- Formulario principal -->
        <form action="SvMostrarDatos" method="post" onsubmit="guardarMarcas()">
            <input type="hidden" name="accion" value="realizar_Marca">

            <!-- Fecha de Marca -->
            <label for="fecha_marca">Fecha de Marca:</label>
            <input type="date" id="fecha_marca" name="fecha_marca" onchange="permitirModificarMarcas()">
            <button type="button" class="manual-button" id="fecha_marca_btn" onclick="realizarMarcaManual(document.getElementById('fecha_marca'))">Marcar Fecha Manualmente</button>

            <!-- Hora de Entrada -->
            <label for="hora_entrada">Hora de Entrada:</label>
            <input type="time" id="hora_entrada" name="hora_entrada">
            <button type="button" class="manual-button" id="hora_entrada_btn" onclick="realizarMarcaManual(document.getElementById('hora_entrada'))">Marcar Manualmente</button>

            <!-- Hora de Salida -->
            <label for="hora_salida">Hora de Salida:</label>
            <input type="time" id="hora_salida" name="hora_salida">
            <button type="button" class="manual-button" id="hora_salida_btn" onclick="realizarMarcaManual(document.getElementById('hora_salida'))">Marcar Manualmente</button>

            <!-- Hora de Salida Almuerzo -->
            <label for="hora_salida_almuerzo">Hora de Salida Almuerzo:</label>
            <input type="time" id="hora_salida_almuerzo" name="hora_salida_almuerzo">
            <button type="button" class="manual-button" id="hora_salida_almuerzo_btn" onclick="realizarMarcaManual(document.getElementById('hora_salida_almuerzo'))">Marcar Manualmente</button>

            <!-- Hora de Entrada Almuerzo -->
            <label for="hora_entrada_almuerzo">Hora de Entrada Almuerzo:</label>
            <input type="time" id="hora_entrada_almuerzo" name="hora_entrada_almuerzo">
            <button type="button" class="manual-button" id="hora_entrada_almuerzo_btn" onclick="realizarMarcaManual(document.getElementById('hora_entrada_almuerzo'))">Marcar Manualmente</button>

            <!-- Bot�n para enviar el formulario -->
            <button type="submit" id="submit-btn">Registrar Marca Manual</button>
        </form>

        <!-- Formulario para regresar a la p�gina principal -->
        <form action="http://localhost:8080/admingLog/vistasLog/empleado.jsp">
            <button type="submit" class="back-button">Regresar</button>
        </form>

        <script>
            // Funci�n para completar autom�ticamente la fecha y bloquear el bot�n
            function realizarMarcaManual(campo) {
                const now = new Date();
                if (campo.type === "date") {
                    const fechaActual = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    const fechaFormateada = fechaActual.toISOString().slice(0, 10); // Formato YYYY-MM-DD
                    campo.value = fechaFormateada;
                } else {
                    const horaActual = now.toTimeString().slice(0, 5); // Hora en formato HH:MM
                    campo.value = horaActual;
                }
                // Desactivar el bot�n despu�s de hacer la marca
                bloquearBoton(campo.id + "_btn");
                guardarMarcas();
            }

            // Guardar marcas en LocalStorage para no perder los datos al volver a la p�gina
            function guardarMarcas() {
                const marcas = {
                    fecha: document.getElementById('fecha_marca').value,
                    horaEntrada: document.getElementById('hora_entrada').value,
                    horaSalida: document.getElementById('hora_salida').value,
                    horaSalidaAlmuerzo: document.getElementById('hora_salida_almuerzo').value,
                    horaEntradaAlmuerzo: document.getElementById('hora_entrada_almuerzo').value
                };
                localStorage.setItem('marcas', JSON.stringify(marcas));
            }

            // Cargar marcas guardadas si existen en LocalStorage y bloquear botones si ya fueron marcadas
            function cargarMarcas() {
                const marcasGuardadas = JSON.parse(localStorage.getItem('marcas'));
                if (marcasGuardadas) {
                    document.getElementById('fecha_marca').value = marcasGuardadas.fecha;
                    document.getElementById('hora_entrada').value = marcasGuardadas.horaEntrada;
                    document.getElementById('hora_salida').value = marcasGuardadas.horaSalida;
                    document.getElementById('hora_salida_almuerzo').value = marcasGuardadas.horaSalidaAlmuerzo;
                    document.getElementById('hora_entrada_almuerzo').value = marcasGuardadas.horaEntradaAlmuerzo;

                    // Bloquear los botones si ya existen marcas
                    if (marcasGuardadas.fecha)
                        bloquearBoton("fecha_marca_btn");
                    if (marcasGuardadas.horaEntrada)
                        bloquearBoton("hora_entrada_btn");
                    if (marcasGuardadas.horaSalida)
                        bloquearBoton("hora_salida_btn");
                    if (marcasGuardadas.horaSalidaAlmuerzo)
                        bloquearBoton("hora_salida_almuerzo_btn");
                    if (marcasGuardadas.horaEntradaAlmuerzo)
                        bloquearBoton("hora_entrada_almuerzo_btn");
                }
            }

            // Funci�n para bloquear los botones
            function bloquearBoton(botonId) {
                document.getElementById(botonId).disabled = true;
            }

            // Permitir modificaci�n de marcas si la fecha cambia
            function permitirModificarMarcas() {
                document.getElementById('fecha_marca_btn').disabled = false;
            }

            // Cargar las marcas cuando se carga la p�gina
            window.onload = cargarMarcas;
        </script>
    </body>
</html>
