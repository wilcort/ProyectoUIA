<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Visualización de Documento</title>
    </head>
    <body>
        <div class="container">
            <h1>Visualizar Documento de Incapacidad</h1>

            <table border="1">
                <thead>
                    <tr>
                        <th>ID Incapacidad</th>
                        <th>Ver Documento</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="incapacidad" items="${listarIncapEmpleado}">
                    <tr>
                        <td>${incapacidad.idIncapacidad}</td>
                        <td>
                            <!-- Enlace para abrir el documento en una nueva pestaña -->
                            <a href="SvIncapacidades?accion=Ver_documentos&id_Incapacidad=${incapacidad.idIncapacidad}" target="_blank">
                                Ver Documento
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
