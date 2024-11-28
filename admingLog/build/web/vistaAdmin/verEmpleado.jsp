<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalles del Empleado</title>
        <!-- Bootstrap 4 CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #e9ecef; /* Fondo general claro */
                font-family: 'Arial', sans-serif;
            }
            .container {
                margin-top: 50px;
            }
            h1, h2 {
                text-align: center;
                margin-bottom: 20px;
                color: #343a40;
            }
            table {
                width: 80%;
                margin: 20px auto;
                border-collapse: collapse;
                background-color: #ffffff;
                box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            }
            th, td {
                text-align: center;
                padding: 10px;
                border: 1px solid #dee2e6;
            }
            th {
                background-color: #007bff; /* Azul para encabezados */
                color: white;
            }
            tr:nth-child(even) {
                background-color: #f8f9fa; /* Filas alternas más claras */
            }
            tr:hover {
                background-color: #d6e0f5; /* Efecto hover */
            }
            .btn {
                margin: 5px;
                border-radius: 20px;
                font-size: 14px;
            }
            .btn-danger {
                background-color: #dc3545;
                border-color: #dc3545;
            }
            .btn-danger:hover {
                background-color: #a71d2a;
                border-color: #a71d2a;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #5a6268;
            }
            .btn-group {
                text-align: center;
                margin: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Detalles del Empleado</h1>

            <h2>Información del Colaborador</h2>
            <table>
                <tr>
                    <th>Número de Empleado</th>
                    <th>Número de Documento</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th>Fecha de Contratación</th>
                </tr>
                <tr>
                    <td>${colaborador.id_Empleado}</td>
                    <td>${colaborador.num_documento}</td>
                    <td>${colaborador.nombre}</td>
                    <td>${colaborador.apellido_1}</td>
                    <td>${colaborador.apellido_2}</td>
                    <td>${colaborador.telefono}</td>
                    <td>${colaborador.direccion}</td>
                    <td>${colaborador.fecha_contratacion}</td>
                </tr>
            </table>

            <h2>Información del Usuario</h2>
            <table>
                <tr>
                    <th>ID de Usuario</th>
                    <th>Nombre de Usuario</th>
                    <th>Estado</th>
                </tr>
                <tr>
                    <td>${colaborador.usuario.id_usuario}</td>
                    <td>${colaborador.usuario.nombreUsuario}</td>
                    <td>${colaborador.usuario.estadoUsuario ? 'Activo' : 'Inactivo'}</td>
                </tr>
            </table>

            <h2>Cargo del Empleado</h2>
            <table>
                <tr>
                    <th>ID Cargo</th>
                    <th>Nombre Cargo</th>
                    <th>Estado Cargo</th>
                </tr>
                <tr>
                    <td>${colaborador.cargo.idCargo}</td>
                    <td>${colaborador.cargo.nombreCargo}</td>
                    <td>${colaborador.cargo.estado ? "Activo" : "Inactivo"}</td>
                </tr>
            </table>

            <h2>Horario del Empleado</h2>
            <table>
                <tr>
                    <th>Hora de Entrada</th>
                    <th>Hora de Salida</th>
                    <th>Horas Laborales</th>
                    <th>Días Laborales</th>
                </tr>
                <tr>
                    <td>${colaborador.horarios != null ? colaborador.horarios.horaEntrada : 'No asignado'}</td>
                    <td>${colaborador.horarios != null ? colaborador.horarios.horaSalida : 'No asignado'}</td>
                    <td>${colaborador.horarios != null ? colaborador.horarios.horasLaborales : 'No asignado'}</td>
                    <td>
                        <c:choose>
                            <c:when test="${colaborador.horarios != null && colaborador.horarios.diasLaborales != null}">
                                <c:set var="dias" value="${colaborador.horarios.diasLaborales}" />
                                ${fn:replace(fn:replace(dias, '[', ''), ']', '')}
                            </c:when>
                            <c:otherwise>
                                No asignado
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>

            <div class="btn-group">
                <form action="SvColaborador?accion=eliminar_Empleado" method="post" style="display: inline;">
                    <input type="hidden" name="id" value="${colaborador.id_Empleado}">
                    <button type="submit" class="btn btn-danger">Eliminar</button>
                </form>

                <form action="SvAsignar" method="get" style="display: inline;">
                    <input type="hidden" name="accion" value="asignar_Cargo">
                    <input type="hidden" name="id_empleado" value="${colaborador.id_Empleado}">
                    <button type="submit" class="btn btn-primary">Asignar Cargo</button>
                </form>

                <form action="SvAsignar" method="get" style="display: inline;">
                    <input type="hidden" name="accion" value="asignar_Horario">
                    <input type="hidden" name="id_empleado" value="${colaborador.id_Empleado}">
                    <button type="submit" class="btn btn-primary">Asignar Horario</button>
                </form>

                <form action="SvColaborador?accion=modificar_Empleado" method="post" style="display: inline;">
                    <input type="hidden" name="id" value="${colaborador.id_Empleado}">
                    <button type="submit" class="btn btn-secondary">Actualizar</button>
                </form>

                <form action="http://localhost:8080/admingLog/SvColaborador" style="display: inline;">
                    <button type="submit" class="btn btn-secondary">Regresar</button>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
