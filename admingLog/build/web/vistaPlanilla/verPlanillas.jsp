<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Reportes de Planilla</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            body {
                background-color: #f8f9fa;
            }
            h1 {
                color: #212529;
                margin-bottom: 20px;
            }
            .table-container {
                max-height: calc(2.5rem * 12 + 1rem); /* Altura de 12 filas más un margen */
                overflow-y: auto;
                border: 1px solid #dee2e6;
                border-radius: 0.5rem;
                background-color: white;
            }
            .table {
                margin: 0;
            }
            .table thead th {
                position: sticky;
                top: 0;
                background-color: #343a40;
                color: white;
                z-index: 1;
            }
            .btn-back {
                margin-top: 20px;
            }

            .btn-ver-planilla {
               background-color: #007bff;
               border-color: #0056b3;
               color: white;
            }

            .btn-ver-planilla:hover {
                background-color: #0056b3;
                border-color: #003d82;
            }

            .btn-ver-planilla i {
                margin-right: 5px; /* Añadir espacio entre el icono y el texto */
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="text-center">Reportes de Planilla de Empleado</h1>

            <!-- Verificación de si hay datos -->
            <c:if test="${empty listarPlanillas}">
                <!-- Mostrar tabla vacía -->
                <div class="table-container">
                    <table class="table table-bordered text-center">
                        <thead class="table-dark">
                            <tr>
                                <th>Mes de Pago</th>
                                <th>Tipo de Periodo</th>
                                <th>Salario Neto Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="3" class="no-data">No hay reportes de planilla disponibles para este empleado.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Mostrar la tabla con datos si hay planillas -->
            <c:if test="${not empty listarPlanillas}">
                <div class="table-container">
                    <table class="table table-striped table-hover text-center">
                        <thead class="table-dark">
                            <tr>
                                <th>Id Planilla</th>
                                <th>Mes de Pago</th>
                                <th>Tipo de Periodo</th>
                                <th>Salario Neto Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Iterar sobre la lista de planillas -->
                            <c:forEach var="planilla" items="${listarPlanillas}">
                                <tr>
                                    <td>${planilla.idPlanilla}</td>
                                    <td>${planilla.mesPago}</td>
                                    <td>${planilla.periodo}</td>
                                    <td>${planilla.salarioNeto}</td>
                                    <td>
                                        <form action="/admingLog/Svplanilla" method="POST">
                                            <input type="hidden" name="accion" value="Ver_Detalle_Planilla">
                                            <input type="hidden" name="id_planilla" value="${planilla.idPlanilla}">
                                            <button type="submit" class="btn btn-primary btn-sm btn-ver-planilla">
                                                <i class="bi bi-eye"></i> Ver Planilla
                                            </button>
</form>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Botón de regresar -->
            <div class="text-center">
                <a href="http://localhost:8080/admingLog/Svplanilla?accion=Listar_Empleados" class="btn btn-secondary btn-back">Regresar al inicio</a>
            </div>
        </div>
    </body>
</html>
