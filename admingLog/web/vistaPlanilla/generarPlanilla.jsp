<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <!-- Importar JSTL -->

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Planilla</title>

        <!-- Vinculamos el CSS de Bootstrap para mejorar el diseño -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            // Función para cerrar el modal y redirigir a la página principal de empleados
            function cerrarModal() {
                window.location.href = "listadoEmpleados.jsp"; // Cambia a la URL de la lista de empleados
            }
        </script>

        <style>
            /* Estilos personalizados para hacer los cuadros de selección más angostos */
            .select-estrecho {
                width: 120px; /* Ajusta el valor para hacerlo más angosto */
            }
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
        </style>
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
            <button class="btn btn-primary" onclick="cerrarModal()">Cerrar</button>
        </div>

        <!-- Contenedor principal -->
        <div class="container mt-4">
            <h1 class="text-center mb-4">Generar Planilla</h1>

            <!-- Formulario para generar planilla -->
            <form action="Svplanilla" method="POST" class="mb-5">
                <div class="mb-3">
                    <label for="mes" class="form-label">Mes:</label>
                    <select id="mes" name="mes" class="form-select form-select-sm select-estrecho" required>
                        <option value="1">Enero</option>
                        <option value="2">Febrero</option>
                        <option value="3">Marzo</option>
                        <option value="4">Abril</option>
                        <option value="5">Mayo</option>
                        <option value="6">Junio</option>
                        <option value="7">Julio</option>
                        <option value="8">Agosto</option>
                        <option value="9">Septiembre</option>
                        <option value="10">Octubre</option>
                        <option value="11">Noviembre</option>
                        <option value="12">Diciembre</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="anio" class="form-label">Año:</label>
                    <input type="number" id="anio" name="anio" class="form-control form-control-sm input-estrecho" min="1900" max="2100" required>
                </div>

                <!-- Botones más unidos usando btn-group -->
                <div class="btn-group d-flex justify-content-between" role="group" aria-label="Botones Planilla">
                    <button type="submit" name="accion" value="Generar_Planilla_Quincena" class="btn btn-success btn-sm">Generar Planilla Quincenal</button>
                    <button type="submit" name="accion" value="Generar_Planilla_Mensual" class="btn btn-primary btn-sm">Generar Planilla Mensual</button>
                </div>
            </form>

            <!-- Botón de regresar -->
            <a href="vistasLog/administrador.jsp" class="btn btn-secondary btn-sm mt-3">Regresar</a>

            <h2 class="text-center">Listado de Empleados</h2>

            <!-- Tabla para mostrar los empleados -->
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID Empleado</th>
                        <th>Nombre</th>
                        <th>Apellido 1</th>
                        <th>Apellido 2</th>
                        <th>Ver Planilla</th>
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
                                <!-- Formulario para el botón "Ver Planilla" -->
                                <form action="Svplanilla" method="POST">
                                    <input type="hidden" name="accion" value="Planilla_Empleado">
                                    <input type="hidden" name="id_empleado" value="${empleado.id_Empleado}">
                                    <button type="submit" class="btn btn-info btn-sm">Ver Planilla</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
