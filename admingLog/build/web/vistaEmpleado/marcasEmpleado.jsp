@Override
    public String getServletInfo() {
        return "Servlet para mostrar los datos del empleado.";
    }
}
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
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin: 10px 0 5px;
            }

            input[type="date"],
            input[type="time"] {
                width: 80%;
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
            }

            button:hover {
                background-color: #4cae4c;
            }

            .manual-button {
                margin-left: 10px;
                background-color: #0275d8;
            }

            .manual-button:hover {
                background-color: #025aa5;
            }

            button[disabled] {
                background-color: #ccc;
                cursor: not-allowed;
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

            <!-- Botón para enviar el formulario -->
            <button type="submit" id="submit-btn">Registrar Marca Manual</button>
        </form>

        <!-- Formulario para regresar a la página principal -->
        <form action="http://localhost:8080/admingLog/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>

        <script>
            // Función para completar automáticamente la fecha y bloquear el botón
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
                // Desactivar el botón después de hacer la marca
                bloquearBoton(campo.id + "_btn");
                guardarMarcas();
            }

            // Guardar marcas en LocalStorage para no perder los datos al volver a la página
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

            // Función para bloquear los botones
            function bloquearBoton(botonId) {
                document.getElementById(botonId).disabled = true;
            }

            // Permitir modificar marcas si la fecha cambia
            function permitirModificarMarcas() {
                const fechaActual = document.getElementById('fecha_marca').value;
                const marcasGuardadas = JSON.parse(localStorage.getItem('marcas'));
                if (marcasGuardadas && marcasGuardadas.fecha !== fechaActual) {
                    localStorage.removeItem('marcas'); // Borrar marcas al cambiar la fecha
                    document.getElementById('hora_entrada').value = ""; // Limpiar hora entrada
                    document.getElementById('hora_salida').value = ""; // Limpiar hora salida
                    document.getElementById('hora_salida_almuerzo').value = ""; // Limpiar hora salida almuerzo
                    document.getElementById('hora_entrada_almuerzo').value = ""; // Limpiar hora entrada almuerzo
                    desbloquearBotones(); // Desbloquear botones
                } else {
                    bloquearCampos(); // Bloquear campos si la fecha es la misma
                }
            }

            // Desbloquear todos los botones
            function desbloquearBotones() {
                document.getElementById('fecha_marca_btn').disabled = false;
                document.getElementById('hora_entrada_btn').disabled = false;
                document.getElementById('hora_salida_btn').disabled = false;
                document.getElementById('hora_salida_almuerzo_btn').disabled = false;
                document.getElementById('hora_entrada_almuerzo_btn').disabled = false;
            }

            // Cargar marcas cuando se cargue la página
            window.onload = cargarMarcas;
        </script>
    </body>
</html>

