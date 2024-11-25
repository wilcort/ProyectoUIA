<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- Importar JSTL -->

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Aguinaldo</title>

        <!-- Vinculamos el CSS de Bootstrap para mejorar el diseño -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            .input-estrecho {
                width: 120px; /* Ajusta el valor para hacerlo más angosto */
            }

            /* Estilos personalizados para el modal */
            #modalExito {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: white;
                border: 1px solid #ccc;
                padding: 20px;
                z-index: 1000;
                box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
                text-align: center;
                border-radius: 8px;
            }
            #modalExitoOverlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }

            .error-message {
                color: red;
                font-size: 0.9em;
            }
        </style>

        <script>
                function validarAno() {
                    var anioIngresado = parseInt(document.getElementById('anio').value); // Convertir a número
                    var anioActual = new Date().getFullYear(); // Obtener el año actual

                    console.log("Año ingresado:", anioIngresado);
                    console.log("Año actual:", anioActual);

                    // Verificar si el año ingresado es mayor o igual al actual
                    if (anioIngresado >= anioActual) {
                        document.getElementById('error-message').innerText = 'El año ingresado debe ser menor al año actual.';
                        return false; // Evitar que se envíe el formulario
                    }

                    // Verificar si el año ingresado es más de dos años menor al actual
                    if (anioActual - anioIngresado > 2) {
                        document.getElementById('error-message').innerText = 'El año ingresado no puede ser más de dos años menor al año actual.';
                        return false; // Evitar que se envíe el formulario
    }

    // Si todo está bien, limpiar el mensaje de error
    document.getElementById('error-message').innerText = '';
    return true; // Permitir que se envíe el formulario
}
        </script>
    </head>
    <body>
        <!-- Mostrar modal si se guarda correctamente -->
        <c:if test="${param.success == 'true'}">
            <script>
                document.getElementById('modalExito').style.display = 'block';
                document.getElementById('modalExitoOverlay').style.display = 'block';
            </script>
        </c:if>

        <!-- Modal -->
        <div id="modalExitoOverlay"></div>
        <div id="modalExito" class="p-4">
            <p>¡Los datos se guardaron con éxito!</p>
            <button class="btn btn-primary" onclick="window.location.href='listadoEmpleados.jsp'">Cerrar</button>
        </div>

        <!-- Contenedor principal -->
        <div class="container mt-4">
            <h1 class="text-center mb-4">Generar Aguinaldo</h1>

            <!-- Formulario para generar aguinaldo -->
            <form action="SvAguinaldo" method="POST" class="mb-5" onsubmit="return validarAno()">
                <div class="mb-3">
                    <label for="anio" class="form-label">Año:</label>
                    <input type="number" id="anio" name="anio" class="form-control form-control-sm input-estrecho" min="1900" max="2100" required>
                    <div id="error-message" class="error-message"></div> <!-- Mostrar mensaje de error aquí -->
                </div>

                <!-- Botón para generar aguinaldo -->
                <button type="submit" name="accion" value="Calcular_Aguinaldo" class="btn btn-success btn-sm">Generar Aguinaldo</button>
            </form>

            <!-- Tabla para mostrar los empleados -->
            <h2 class="text-center">Listado de Empleados</h2>
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID Empleado</th>
                        <th>Nombre</th>
                        <th>Apellido 1</th>
                        <th>Apellido 2</th>
                        <th>Ver Aguinaldo</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de empleados y mostrar sus datos -->
                    <c:forEach var="empleado" items="${listarTodosEmpleados}">
                        <tr>
                            <td>${empleado.id_Empleado}</td>
                            <td>${empleado.nombre}</td>
                            <td>${empleado.apellido_1}</td>
                            <td>${empleado.apellido_2}</td>
                            <td>
                                <!-- Formulario para el botón "Ver Aguinaldo" -->
                                <form action="SvAguinaldo" method="POST">
                                    <input type="hidden" name="accion" value="Ver_Aguinaldo_Empleado">
                                    <input type="hidden" name="id_empleado" value="${empleado.id_Empleado}">
                                    <button type="submit" class="btn btn-info btn-sm">Ver Aguinaldo</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Botón de regresar -->
            <a href="vistasLog/administrador.jsp" class="btn btn-secondary btn-sm mt-3">Regresar</a>
        </div>
    </body>
</html>