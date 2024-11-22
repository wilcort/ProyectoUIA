<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Generar Planilla</title>
    </head>
    <body>
        <h1>Generar Planilla</h1>
        <form action="Svplanilla" method="POST">
            <label for="mes">Mes:</label>
            <select id="mes" name="mes" required>
                <option value="1">Enero</option>
                <option value="2">Febrero</option>
                <option value="3">Marzo</option>
                <option value="4">Abril</option>
                <option value="5">Mayo</option>
                <option value="6">Junio</option>
                <option value="7">Julio</option>
                <option value="8">Agosto</option>
                <option value="9">Septiembre</option>
                <option value="10">Octubre</option>
                <option value="11">Noviembre</option>
                <option value="12">Diciembre</option>
            </select>
            <br><br>
            <label for="anio">Año:</label>
            <input type="number" id="anio" name="anio" min="1900" max="2100" required>
            <br><br>
            <button type="submit" name="accion" value="Generar_Planilla_Quincena">Generar Planilla Quincena</button>
            <button type="submit" name="accion" value="Generar_Planilla_Mensual">Generar Planilla Mensual</button>
        </form>
    </body>
</html>
