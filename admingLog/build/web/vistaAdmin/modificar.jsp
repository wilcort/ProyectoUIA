<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Modificar Empleado</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }

            h1 {
                text-align: center;
                color: #333;
                margin-top: 20px;
            }

            form {
                width: 60%;
                margin: 20px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            th, td {
                padding: 10px;
                text-align: left;
                border: 1px solid #ddd;
            }

            th {
                background-color: #f4f4f4;
                color: #333;
            }

            input[type="text"], input[type="date"], select {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                box-sizing: border-box;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            input[type="text"]:read-only {
                background-color: #f5f5f5;
                cursor: not-allowed;
            }

            button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
            }

            button:hover {
                background-color: #45a049;
            }

            .back-button {
                background-color: #ccc;
                color: #333;
            }

            .back-button:hover {
                background-color: #bbb;
            }
        </style>
        <script>
            function validateInput(event, pattern) {
                const regex = new RegExp(pattern);
                if (!regex.test(event.target.value)) {
                    event.target.value = event.target.value.slice(0, -1);
                }
            }

            document.addEventListener('DOMContentLoaded', () => {
                // Validación en tiempo real
                document.querySelector('[name="num_documento"]').addEventListener('input', (e) => {
                    validateInput(e, "^[0-9]*$");
                });

                document.querySelector('[name="nombre"]').addEventListener('input', (e) => {
                    validateInput(e, "^[a-zA-ZÀ-ÿ\\s]*$");
                });

                document.querySelector('[name="apellido_1"]').addEventListener('input', (e) => {
                    validateInput(e, "^[a-zA-ZÀ-ÿ\\s]*$");
                });

                document.querySelector('[name="apellido_2"]').addEventListener('input', (e) => {
                    validateInput(e, "^[a-zA-ZÀ-ÿ\\s]*$");
                });

                document.querySelector('[name="telefono"]').addEventListener('input', (e) => {
                    validateInput(e, "^[0-9]*$");
                });

                document.getElementById('modificar_estado_usuario').addEventListener('change', (e) => {
                    const estadoUsuario = document.getElementById('estado_Usuario');
                    estadoUsuario.disabled = e.target.value !== "si";
                });
            });
        </script>
    </head>
    <body>
        <h1>Modificar Empleado</h1>

        <form action="SvColaborador" method="post">
            <input type="hidden" name="idUsuario" value="${colaborador.usuario.id_usuario}">
            <input type="hidden" name="estado_actual" value="${colaborador.usuario.estadoUsuario ? '1' : '0'}">

            <table>
                <tr>
                    <th>ID Empleado</th>
                    <td><input type="text" name="id_empleado" value="${colaborador.id_Empleado}" readonly></td>
                </tr>
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
                    <th>Fecha de Contratación</th>
                    <td><input type="date" name="fecha_contratacion" value="${colaborador.fecha_contratacion}" required></td>
                </tr>
                <tr>
                    <th>Modificar Estado Usuario</th>
                    <td>
                        <select id="modificar_estado_usuario" name="modificar_estado_usuario">
                            <option value="" disabled selected>Seleccione</option>
                            <option value="si">Sí</option>
                            <option value="no">No</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Estado Usuario</th>
                    <td>
                        <select id="estado_Usuario" name="estado_Usuario" disabled>
                            <option value="" disabled selected>Seleccione</option>
                            <option value="1">Activo</option>
                            <option value="0">Desactivado</option>
                        </select>
                    </td>
                </tr>
            </table>

            <input type="hidden" name="accion" value="actualizar_Empleado">
            <button type="submit">Guardar</button>
        </form>

        <form action="http://localhost:8080/admingLog/SvColaborador" style="display: inline;">
            <button type="submit" class="back-button">Regresar</button>
        </form>
    </body>
</html>
