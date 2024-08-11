
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ALMACEN</title>
    </head>
    <body>
        <h1>COLABORADORES</h1>
        <a href="SvColaborador?accion=nuevo">Ingresar Nuevo Colaborador</a>

        <br/><br/>

        <table border="1" width="100%">
            <thead>
                <tr>
                    <th>ID Cargo</th>
                    <th>Nombre Cargo</th>
                    <th>Estado del Cargo</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                
            </tbody>
        </table>
        <form action="vistasLog/administrador.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </form>
    </body>
</html>
