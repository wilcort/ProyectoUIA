<%-- 
    Document   : nuevoHorario
    Created on : Sep 14, 2024, 9:10:03 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Ingresar Horario</title>
        <style>
            /* Estilo básico para el formulario */
            label {
                display: block;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <h1>Ingresar Nuevo Horario</h1>

        <!-- El formulario tiene el ID "formHorario" para que el script lo pueda identificar -->
        <form id="formHorario" action="SvHorarios" method="post">
            <input type="hidden" name="accion" value="insertarHorario">

            <label for="horaEntrada">Hora de Entrada:</label>
            <input type="time" id="horaEntrada" name="horaEntrada" required><br><br>

            <label for="horaSalida">Hora de Salida:</label>
            <input type="time" id="horaSalida" name="horaSalida" required><br><br>

            <label>Días Laborales:</label>
            <div>
                <label><input type="checkbox" name="diasLaborales" value="lunes"> Lunes</label>
                <label><input type="checkbox" name="diasLaborales" value="martes"> Martes</label>
                <label><input type="checkbox" name="diasLaborales" value="miercoles"> Miércoles</label>
                <label><input type="checkbox" name="diasLaborales" value="jueves"> Jueves</label>
                <label><input type="checkbox" name="diasLaborales" value="viernes"> Viernes</label>
                <label><input type="checkbox" name="diasLaborales" value="sabado"> Sábado</label>
                <label><input type="checkbox" name="diasLaborales" value="domingo"> Domingo</label>
            </div><br>

            <input type="submit" value="Guardar Horario">
        </form>

        <form action="SvHorarios" method="get" style="display: inline;">
            <button type="submit">Volver a la lista de horarios</button>
        </form>
    </body>
</html>