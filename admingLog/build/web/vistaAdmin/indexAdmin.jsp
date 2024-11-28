<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ALMACÉN - COLABORADORES</title>
        <!-- Bootstrap 4 CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- Custom Styles -->
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Arial', sans-serif;
            }
            .container {
                margin-top: 50px;
            }
            h1 {
                text-align: center;
                margin-bottom: 30px;
            }
            table th, table td {
                text-align: center;
            }
            table {
                margin-bottom: 30px;
            }
            .btn {
                border-radius: 30px;
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
            .btn-link {
                color: #007bff;
            }
            .btn-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Gestión de Colaboradores </h1>
            
            <div class="mb-3">
                <a href="SvColaborador?accion=nuevo" class="btn btn-primary">
                    <i class="fas fa-plus-circle"></i> Ingresar Nuevo Colaborador
                </a>
            </div>

            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>ID del Empleado</th>
                        <th>Num. Documento</th>
                        <th>Nombre</th>
                        <th>Primer Apellido</th>
                        <th>Segundo Apellido</th>
                        <th>Teléfono Principal</th>
                        <th>Dirección</th>
                        <th>Fecha de Contratación</th>
                        <th>ID Usuario</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="colaborador" items="${lista}">
                        <tr>
                            <td><c:out value="${colaborador.id_Empleado}" /></td>
                            <td><c:out value="${colaborador.num_documento}" /></td>
                            <td><c:out value="${colaborador.nombre}" /></td>
                            <td><c:out value="${colaborador.apellido_1}" /></td>
                            <td><c:out value="${colaborador.apellido_2}" /></td>
                            <td><c:out value="${colaborador.telefono}" /></td>
                            <td><c:out value="${colaborador.direccion}" /></td>
                            <td><c:out value="${colaborador.fecha_contratacion}" /></td>
                            <td><c:out value="${colaborador.usuario.id_usuario}" /></td>
                            <td>
                                <a href="SvColaborador?accion=Ver_Empleado&id=<c:out value="${colaborador.id_Empleado}" />" class="btn btn-link">
                                    <i class="fas fa-eye"></i> Ver Empleado
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <form action="vistasLog/administrador.jsp" style="display: inline;">
                <button type="submit" class="btn btn-secondary">Regresar</button>
            </form>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>

