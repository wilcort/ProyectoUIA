<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <script>
            function toggleModification(selectElementId, targetElementId) {
                var selectElement = document.getElementById(selectElementId);
                var targetElement = document.getElementById(targetElementId);
                targetElement.disabled = selectElement.value !== "si";
            }

            document.addEventListener('DOMContentLoaded', (event) => {
                document.getElementById('modificar_estado_usuario').addEventListener('change', () => {
                    toggleModification('modificar_estado_usuario', 'estado_Usuario');
                });
                 document.getElementById('modificar_cargo_actual').addEventListener('change', () => {
                        toggleModification('modificar_cargo_actual', 'cargo_Usuario');
                    });
                });
        </script>
    </head>
    <body>
        <h1>Modificar Empleado</h1>

        <form action="SvColaborador" method="post">  
                
            
            <input type="hidden" name="idUsuario" value="${colaborador.usuario.id_usuario}">
            <input type="hidden" name="estado_actual" value="${colaborador.usuario.estadoUsuario ? '1' : '0'}">
            <input type="hidden" name="cargo_actual" value="${colaborador.usuario.id_cargo}">

            <table>
                <tr>
                    <th>ID Empleado</th>
                    <td><input type="text" name="id_empleado" value="${colaborador.id_Empleado}" required></td>
                </tr>
                <tr>
                    <th>N�mero de Documento</th>
                    <td><input type="text" name="num_documento" value="${colaborador.num_Documento}" required></td>
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
                    <th>Tel�fono</th>
                    <td><input type="text" name="telefono" value="${colaborador.telefono}" required></td>
                </tr>
                <tr>
                    <th>Direcci�n</th>
                    <td><input type="text" name="direccion" value="${colaborador.direccion}" required></td>
                </tr>
                <tr>
                    <th>Fecha de Contratci�n</th>
                    <td><input type="date" name="fecha_contratacion" value="${colaborador.fecha_Contratacion}" required></td>
                </tr>
                <tr>
                    <th>Salario Base</th>
                    <td><input type="number" name="salario_base" step="0.01" value="${colaborador.salario_Base}" required></td>
                </tr>
                <tr>
                    <th>Estado Usuario</th>
                    <td><input type="text" name="estado" value="${colaborador.usuario.estadoUsuario ? 'activo' : 'inactivo'}" required></td>
                </tr>

                <tr>
                    <th>Modificar Estado Usuario</th>
                    <td>
                        <select id="modificar_estado_usuario" name="modificar_estado_usuario">
                            <option value="" disabled selected>Seleccione</option>
                            <option value="si">S�</option>
                            <option value="no">No</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Estado Usuario:</th>
                    <td>
                        <select id="estado_Usuario" name="estado_Usuario" disabled>
                            <option value="" disabled selected>Escoja opci�n</option>
                            <option value="1">Activo</option>
                            <option value="0">Desactivado</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Cargo Actual</th>
                    <td>${colaborador.usuario.cargo.nombreCargo}</td>
                    <td>${colaborador.usuario.id_cargo}</td>
                </tr>  
                <tr>
                    <th>Modificar Cargo Actual</th>
                    <td>
                        <select id="modificar_cargo_actual" name="modificar_cargo_actual">
                            <option value="" disabled selected>Seleccione</option>
                            <option value="si">S�</option>
                            <option value="no">No</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Cargo:</th>
                    <td>
                        <select id="cargo_Usuario" name="cargo_Usuario" disabled>
                            <option value="" disabled selected>Escoja opci�n</option>
                            <c:forEach var="cargo" items="${cargos}">
                                <option value="${cargo.idCargo}">${cargo.nombreCargo}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
 
            </table>
            <input type="hidden" name="accion" value="actualizar_Empleado">
            <button id="guardar" type="submit">Guardar</button>
        </form>

                <form action="http://localhost:8080/admingLog/SvColaborador" style="display: inline; margin-left: 10px;">
                    <button type="submit">Regresar</button>
                </form>
    </body>
</html>