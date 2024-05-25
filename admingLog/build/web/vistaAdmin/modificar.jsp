<%-- 
    Document   : modificar
    Created on : May 22, 2024, 9:36:48 PM
    Author     : Dell
--%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificar Empleado</title>
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
        <h1>Modificar Empleado</h1>

        <form action="SvColaborador" method="post">
            <input type="hidden" name="accion" value="modificar_Empleado">
            <input type="hidden" name="id" value="${colaborador.usuario.id_usuario}">

            <table>
                <tr>
                    <th>Número de Documento</th>
                    <td><input type="text" name="num_documento" value="${colaborador.num_documento}"></td>
                </tr>
                <tr>
                    <th>Nombre</th>
                    <td><input type="text" name="nombre" value="${colaborador.nombre}"></td>
                </tr>
                <tr>
                    <th>Primer Apellido</th>
                    <td><input type="text" name="apellido_1" value="${colaborador.apellido_1}"></td>
                </tr>
                <tr>
                    <th>Segundo Apellido</th>
                    <td><input type="text" name="apellido_2" value="${colaborador.apellido_2}"></td>
                </tr>
                <tr>
                    <th>Teléfono</th>
                    <td><input type="text" name="telefono" value="${colaborador.telefono}"></td>
                </tr>
                <tr>
                    <th>Dirección</th>
                    <td><input type="text" name="direccion" value="${colaborador.direccion}"></td>
                </tr>
                <tr>
                    <th>Nombre de Usuario</th>
                    <td><input type="text" name="nombreUsuario" value="${colaborador.usuario.nombreUsuario}"></td>
                </tr>
                <tr>
                    <th>Clave</th>
                    <td><input type="password" name="claveUsuario" value="${colaborador.usuario.clave}"></td>
                </tr>
                <tr>
                    <th>Estado</th>
                    <td>
                        <select name="estado_cargoUsuario">
                            <option value="1" ${colaborador.usuario.estado ? 'selected' : ''}>Activo</option>
                            <option value="0" ${!colaborador.usuario.estado ? 'selected' : ''}>Inactivo</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Cargo</th>
                    <td><input type="text" name="cargo_Usuario" value="${colaborador.usuario.cargo.nombreCargo}"></td>
                </tr>
            </table>
            <div style="text-align: center; margin-top: 20px;">
                <button type="submit">Guardar Cambios</button>
            </div>
        </form>
    </body>
</html>