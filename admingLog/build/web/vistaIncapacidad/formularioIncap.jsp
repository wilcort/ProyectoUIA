<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitud de Incapacidades</title>
        <link rel="stylesheet" href="styles.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 80vh;
                margin: 0;
            }
            .container {
                max-width: 500px;
                padding: 20px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                border-radius: 8px;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
            }
            input[type="number"],
            input[type="file"],
            input[type="date"],
            select,
            textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }
            .btn-submit, .btn-secondary {
                width: 100%;
                padding: 10px;
                margin-top: 10px;
                background-color: #4CAF50;
                border: none;
                color: white;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
            }
            .btn-secondary {
                background-color: #007bff;
            }
            .btn-submit:hover {
                background-color: #45a049;
            }
            .btn-secondary:hover {
                background-color: #0056b3;
            }
        </style>

        <script>
            function calcularDias() {
                const fecha_inicio = new Date(document.getElementById('fecha_inicio').value);
                const fecha_fin = new Date(document.getElementById('fecha_fin').value);

                if (fecha_inicio instanceof Date && !isNaN(fecha_inicio) &&
                        fecha_fin instanceof Date && !isNaN(fecha_fin)) {

                    const diferenciaMilisegundos = fecha_fin - fecha_inicio;
                    const diasIncapacidad = Math.ceil(diferenciaMilisegundos / (1000 * 60 * 60 * 24)) + 1;
                    document.getElementById('dias_Incapacidad').value = diasIncapacidad >= 0 ? diasIncapacidad : 0;
                } else {
                    document.getElementById('dias_Incapacidad').value = '';
                }
            }

            function redirigirFormulario(event) {
                event.preventDefault();  // Evitar que el formulario se envíe inmediatamente
                const tipoIncapacidad = document.getElementById("tipo_incapacidad").value;
                let redireccionURL = '';

                if (tipoIncapacidad === "maternidad") {
                    redireccionURL = "/vistaIncapacidad/formularioMaternidad.jsp";
                } else if (tipoIncapacidad === "enfermedad") {
                    redireccionURL = "/vistaIncapacidad/formularioEnfComun.jsp";
                } else {
                    alert("Por favor, selecciona un tipo de incapacidad.");
                    return;
                }

                // Agrega una breve pausa antes de redirigir
                setTimeout(() => {
                    window.location.href = redireccionURL;
                    // Después de la redirección, envía el formulario
                    document.getElementById("solicitudForm").submit();
                }, 500);  // La pausa de 500ms para permitir que la redirección ocurra antes de enviar el formulario
            }

            window.onload = function () {
                const today = new Date().toISOString().split("T")[0];
                document.getElementById("fecha_inicio").setAttribute("min", today);

                document.getElementById("fecha_inicio").addEventListener("input", function () {
                    const fechaInicio = this.value;
                    document.getElementById("fecha_fin").setAttribute("min", fechaInicio);
                    calcularDias();
                });
                document.getElementById("fecha_fin").addEventListener("input", calcularDias);
            };
        </script>
    </head>

    <body>
    <div class="container">
        <h1>Solicitud de Incapacidades</h1>
        <form id="solicitudForm" action="/admingLog/SvIncapacidades" method="POST" onsubmit="redirigirFormulario(event)">
            <input type="hidden" name="accion" value="Realizar_Solicitud">

            <input type="text" name="id_empleado" value="${sessionScope.id_empleado != null ? sessionScope.id_empleado : 'ID no disponible'}" readonly>
            
            <div class="form-group">
                <label for="tipo_incapacidad">Tipo de Incapacidad:</label>
                <select id="tipo_incapacidad" name="tipo_incapacidad">
                    <option value="" disabled selected>Seleccione el tipo de incapacidad</option>
                    <option value="maternidad">Maternidad</option>
                    <option value="enfermedad">Enfermedad</option>
                </select>
            </div>

            <div class="form-group">
                <label for="dias_Incapacidad">Días de Incapacidad:</label>
                <input type="number" id="dias_Incapacidad" name="dias_Incapacidad" readonly>
            </div>

            <div class="form-group">
                <label for="fecha_inicio">Fecha de Inicio de Incapacidad:</label>
                <input type="date" id="fecha_inicio" name="fecha_inicio">
            </div>

            <div class="form-group">
                <label for="fecha_fin">Fecha de Fin de Incapacidad:</label>
                <input type="date" id="fecha_fin" name="fecha_fin">
            </div>
            
            <input type="hidden" name="id_incapacidad" value="${sessionScope.id_incapacidad != null ? sessionScope.id_incapacidad : 'ID no disponible'}" readonly>  
            <button type="submit" class="btn-submit">Enviar Solicitud de Incapacidad</button>
        </form>

        <!-- Formulario para ver las solicitudes -->
        <form action="/admingLog/SvIncapacidades" method="GET" style="display: inline;">
            <input type="hidden" name="accion" value="Ver_Solicitudes">
           

            <input type="hidden" name="id_empleado" value="${sessionScope.id_empleado != null ? sessionScope.id_incapacidad : 'ID no disponible'}" readonly>             
            <button type="submit" class="btn-submit">Ver Solicitudes de Incapacidad</button>
        </form>

        <!-- Botón de Regreso -->
        <form action="${pageContext.request.contextPath}/vistasLog/empleado.jsp" style="display: inline;">
            <button type="submit" class="btn-secondary">Regresar</button>
        </form>
    </div>
</body>
