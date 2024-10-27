<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Modificar Marca</title>
    </head>
    <body>
        <h2>Modificar Marca</h2>

        <!-- Formulario para modificar los datos de la marca -->
        <form action="SvMarcas" method="post">
            <!-- Campo oculto para enviar el id de la marca -->
            <input type="hidden" name="id_empleado" value="<%= request.getParameter("id_empleado") != null ? request.getParameter("id_empleado") : ""%>">
          
            <input type="hidden" name="id_marca" value="${varMarca.idMarca}">
            
 
            <input type="hidden" name="accion" value="actualizar_Marca">

            <label for="fechaMarca">Fecha de Marca:</label>
            <input type="date" id="fechaMarca" name="fecha_marca" value="${varMarca.fechaMarca}" required>
            <br><br>

            <label for="horaEntrada">Hora de Entrada:</label>
            <input type="time" id="horaEntrada" name="hora_entrada" value="${varMarca.marcaEntrada}" required>
            <br><br>

            <label for="horaSalida">Hora de Salida:</label>
            <input type="time" id="horaSalida" name="hora_salida" value="${varMarca.marcaSalida}" required>
            <br><br>

            <label for="horaSalidaAlmuerzo">Hora de Salida Almuerzo:</label>
            <input type="time" id="horaSalidaAlmuerzo" name="hora_salida_almuerzo" value="${varMarca.marcaSalidaAlmuerzo}" required>
            <br><br>

            <label for="horaEntradaAlmuerzo">Hora de Entrada Almuerzo:</label>
            <input type="time" id="horaEntradaAlmuerzo" name="hora_entrada_almuerzo" value="${varMarca.marcaEntradaAlmuerzo}" required>
            <br><br>

            <!-- Botón para guardar los cambios -->
            <button type="submit">Guardar Cambios</button>
        </form>
    </body>
</html>
