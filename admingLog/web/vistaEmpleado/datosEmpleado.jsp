<%-- 
    Document   : indexEmpleado
    Created on : Sep 28, 2024, 12:43:12 PM
    Author     : Dell
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Detalles del Empleado</title>
        <style>
            table {
                border-collapse: collapse;
                width: 50%;
                margin: 20px auto;
            }
            th, td {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }
            th {
                background-color: #f2f2f2;
            }
        </style>
    </head>
    <body>
        <h1>Detalles del Empleado</h1>

        <h2>Información del Colaborador</h2>
        <table>
            <tr>
                <th>Numero Empleado</th>
                <th>Número de Documento</th>
                <th>Nombre</th>
                <th>Primer Apellido</th>
                <th>Segundo Apellido</th>
                <th>Teléfono</th>
                <th>Dirección</th>
                <th>Fecha de Contratación</th>
                <th>Salario Base</th>
                
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
                <td>${colaborador.salario_base}</td>
               
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
                <td>${colaborador.usuario.estadoUsuario ? 'activo' : 'inactivo'}</td>
            </tr>
        </table>
                           
        <h2>Cargo del Empleado</h2>
        <table>
            <tr>
                <th> ID Cargo del Empleado</th>
                <th> Nombre Cargo del Empleado</th>
                <th> Estado Cargo del Empleado</th>
            </tr>
            <tr>
                <td>${colaborador.cargo.idCargo}</td>
                <td>${colaborador.cargo.nombreCargo}</td>
                <td>${colaborador.cargo.estado ? "Activo" : " Inactivo"}</td>
            </tr> 
        </table>
            
            <h2>Horario del Empleado</h2>
        <table>
             <tr>
                <th> Hora de Entrada </th>
                <th> Hora Salida</th>
                <th> Total Horas Normales </th>
                <th> Días laborales </th>
            </tr>
            <tr>
                
                <td>${colaborador.horarios != null ? colaborador.horarios.horaEntrada : 'No asignado'}</td>
                <td>${colaborador.horarios != null ? colaborador.horarios.horaSalida : 'No asignado'}</td>
                <td>${colaborador.horarios != null ? colaborador.horarios.horasLaborales : 'No asignado'}</td>
                <td>${colaborador.horarios != null ? colaborador.horarios.diasLaborales : 'No asignado'}</td>
            </tr>
                
        </table>
        
        <!-- Botones -->
        <div style="text-align: center; margin-top: 20px;">

            <form action="http://localhost:8080/admingLog/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
                <button type="submit">Regresar</button>
            </form>
        </div>
    </body>
</html>