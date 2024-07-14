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
            <input type="hidden" name="id" value="${colaborador.usuario.id_usuario}">
            <table>
                <tr>
                    <th>Número de Documento</th>
                    <td><input type="text" name="num_documento" value="${colaborador.num_documento}" required></td>
                </tr>
                <tr>
                    <th>Nombre</th>
                    <td><input type="text" name="nombre" value="${colaborador.nombre}" required></td>
                </tr>
                <tr>
                    <th>Primer Apellido</th>
                    <td><input type="text" name="apellido_1" value="${colaborador.apellido_1}" required></td>
                </tr>
                <tr>
                    <th>Segundo Apellido</th>
                    <td><input type="text" name="apellido_2" value="${colaborador.apellido_2}" required></td>
                </tr>
                <tr>
                    <th>Teléfono</th>
                    <td><input type="text" name="telefono" value="${colaborador.telefono}" required></td>
                </tr>
                <tr>
                    <th>Dirección</th>
                    <td><input type="text" name="direccion" value="${colaborador.direccion}" required></td>
                </tr>
                <tr>
                    <th>Estado</th>
                    <td><input type="text" name="estado" value="${colaborador.usuario.estado? 'activo' : 'inactivo'}" required></td>
               
                </tr>
            </table>
                
                <table>
                    <label for="estado_cargoUsuario">Estado Cargo:</label>
                    <select id="estado_cargoUsuario" name="estado_cargoUsuario">
                        <option value="" disabled selected>Escoja opción</option>
                        <option value="1">Activo</option>
                        <option value="0">Desactivado</option>
                    </select><br><br> 
                </table>

            <input type="hidden" name="accion" value="actualizar_Empleado">
            <button id="guardar" type="submit">Guardar</button>
        </form>

        <form action="http://localhost:8080/admingLog/SvColaborador" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </body>
</html>