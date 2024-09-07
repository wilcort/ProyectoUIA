<%-- 
    Document   : verEmpleado
    Created on : Apr 28, 2024, 8:23:58 AM
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
                <td>${colaborador.num_Documento}</td>
                <td>${colaborador.nombre}</td>
                <td>${colaborador.apellido_1}</td>
                <td>${colaborador.apellido_2}</td>
                <td>${colaborador.telefono}</td>
                <td>${colaborador.direccion}</td>
                <td>${colaborador.fecha_Contratacion}</td>
                <td>${colaborador.salario_Base}</td>
               
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

        <h2>Información del Cargo</h2>
        <table>
            <tr>         
                <th>Nombre de Cargo</th>
                <th>Estado de Cargo</th>
                <th>id de Cargo</th>
            </tr>
            <tr>
                <td>${colaborador.usuario.cargo.nombreCargo}</td>
                <td>${colaborador.usuario.cargo.estado ? 'activo' : 'inactivo'}</td>
                <td>${colaborador.usuario.id_cargo}</td>
            </tr>
        </table>
        <!-- Botones -->
        <div style="text-align: center; margin-top: 20px;">

            <form action="SvColaborador?accion=eliminar_Empleado" method="post" style="display: inline;">
                <input type="hidden" name="id" value="${colaborador.usuario.id_usuario}">
                <button type="submit" name="eliminar" value="eliminar">Eliminar</button>
            </form>

            <form action="SvColaborador?accion=modificar_Empleado" method="post"
                  style="display: inline; margin-left: 10px;">
                <input type="hidden" name="id" value="${colaborador.usuario.id_usuario}">
                <button type="submit" name="actualizar" value="actualizar">Actualizar</button>
            </form>
            <form action="http://localhost:8080/admingLog/SvColaborador" style="display: inline; margin-left: 10px;">
                <button type="submit">Regresar</button>
            </form>
        </div>
    </body>
</html>