<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalle de la Planilla</title>
        <link rel="stylesheet" href="css/styles.css">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 30px;
                background-color: #f7f7f7;
                color: #333;
            }

            h2 {
                text-align: center;
                color: #009688;
                margin-bottom: 20px;
            }

            table {
                width: 80%;
                margin: 0 auto;
                border-collapse: collapse;
                background-color: #fff;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                overflow: hidden;
            }

            th, td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #009688;
                color: white;
                font-size: 16px;
            }

            td {
                font-size: 15px;
                color: #555;
            }

            .currency-cell {
                background-color: #e9f6f4;
                font-weight: bold;
                color: #00796b;
                text-align: center;
            }

            .amount {
                color: #009688;
                font-weight: bold;
                font-size: 16px;
            }

            .back-button {
                display: block;
                width: 180px;
                padding: 10px;
                margin: 30px auto;
                background-color: #009688;
                color: white;
                text-align: center;
                font-size: 16px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .back-button:hover {
                background-color: #00796b;
            }
        </style>
    </head>
    <body>

        <h2>Detalle de la Planilla</h2>

        <!-- Mostrar información de la planilla -->
        <table>
            <tr>
                <th>ID Planilla</th>
                <td>${planilla.idPlanilla}</td>
            </tr>
            <tr>
                <th>Periodo</th>
                <td>${planilla.periodo}</td>
            </tr>
            <tr>
                <th>Salario Referencia</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.salarioReferencia}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Deducciones CCSS</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.deduccionesCCSS}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Pago por Incapacidades</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.pagoIncapacidades}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Pago por Vacaciones</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.pagoVacaciones}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Deducciones Impuestos</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.deduccionesImpuestos}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Salario por Horas Extra</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.salarioHorasExtra}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Salario por Horas Regulares</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.salarioHorasRegulares}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Horas Extra</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.horasExtra}" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Horas Regulares</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.horasRegulares}" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Salario Bruto</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.salarioBruto}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Salario Neto</th>
                <td class="currency-cell">
                    <fmt:formatNumber value="${planilla.salarioNeto}" type="currency" currencySymbol="₡" groupingUsed="true" minFractionDigits="2" maxFractionDigits="2" />
                </td>
            </tr>
            <tr>
                <th>Mes de Pago</th>
                <td>${planilla.mesPago}</td>
            </tr>
            <tr>
                <th>ID Empleado</th>
                <td>${planilla.empleadoIdEmpleado}</td>
            </tr>
        </table>

        <button class="back-button" onclick="window.history.back();">Volver</button>

    </body>
</html>
