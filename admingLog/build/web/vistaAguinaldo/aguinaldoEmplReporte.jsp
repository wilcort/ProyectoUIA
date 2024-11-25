<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Información del Empleado</title>
        <link rel="stylesheet" href="styles.css">
        <style>
            /* Reset de estilos predeterminados */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            /* Estilos generales */
            body {
                font-family: 'Arial', sans-serif;
                background-color: #a3c9f1; /* Fondo azul claro */
                color: #333;
                line-height: 1.6;
            }

            .container {
                width: 80%;
                margin: 20px auto;
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            header {
                text-align: center;
                margin-bottom: 20px;
            }

            h1 {
                font-size: 2.5em;
                color: #007bff;
                margin-bottom: 10px;
            }

            h2 {
                font-size: 1.8em;
                color: #333;
                margin-bottom: 15px;
            }

            /* Estilo de las tablas */
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 30px;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #f1f1f1;
                color: #555;
                font-weight: bold;
            }

            td {
                color: #666;
            }

            /* Estilo de las cajas de información */
            .info-box {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
                margin-bottom: 30px;
            }

            /* Estilo del mensaje */
            .alert {
                background-color: #ffdd57;
                color: #333;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
            }

            /* Contenedor para alinear botones */
            .button-container {
                display: flex;
                justify-content: space-between; /* Alineación horizontal */
                align-items: center; /* Centrado vertical */
                margin-top: 20px;
            }

            /* Botones */
            .back-btn {
                display: inline-block;
                background-color: #007bff;
                color: #fff;
                padding: 10px 20px;
                font-size: 1.1em;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
                text-align: center;
            }

            .back-btn:hover {
                background-color: #0056b3;
            }

            .view-planillas-btn {
                display: inline-block;
                background-color: #28a745;
                color: #fff;
                padding: 10px 20px;
                font-size: 1.1em;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
                text-align: center;
            }

            .view-planillas-btn:hover {
                background-color: #218838;
            }

            /* Formato de colones */
            .colones {
                color: #28a745;
                font-weight: bold;
                font-size: 1.2em;
                text-align: right;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Detalles del Aguinaldo vvv</h1>
            </header>

            <!-- Mensaje de estado -->
            <div id="mensaje" class="alert">
                <c:if test="${not empty mensaje}">
                    <p>${mensaje}</p>
                </c:if>
            </div>

            <!-- Información del empleado -->
            <div id="empleado-info" class="info-box">
                <h2>Datos del Empleado</h2>
                <table>
                    <tr>
                        <th>ID de Empleado</th>
                        <td>${aguinaldo.colaborador.id_Empleado}</td>
                    </tr>
                    <tr>
                        <th>Nombre</th>
                        <td>${aguinaldo.colaborador.nombre}</td>
                    </tr>
                    <tr>
                        <th>Primer Apellido</th>
                        <td>${aguinaldo.colaborador.apellido_1}</td>
                    </tr>
                </table>
            </div>

            <!-- Información sobre el aguinaldo -->
            <div id="aguinaldo-info" class="info-box">
                <h2>Datos del Aguinaldo</h2>
                <c:if test="${not empty aguinaldo}">
                    <table>
                        <tr>
                            <th>ID de Aguinaldo</th>
                            <td>${aguinaldo.idAguinaldo}</td>
                        </tr>
                        <tr>
                            <th>Total de Salarios</th>
                            <td class="colones">
                                <fmt:formatNumber value="${aguinaldo.totalSalarios}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                            </td>
                        </tr>
                        <tr>
                            <th>Aguinaldo</th>
                            <td class="colones">
                                <fmt:formatNumber value="${aguinaldo.aguinaldo}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                            </td>
                        </tr>
                    </table>
                </c:if>
            </div>

            <!-- Botones: Volver y Ver Planilla -->
            <div class="button-container">
                <!-- Botón para regresar -->
                <button onclick="window.history.back()" class="back-btn">Volver</button>

                
            </div>
        </div>
    </body>
</html>
