<%-- 
    Document   : indexCargos
    Created on : Aug 10, 2024, 7:34:09 PM
    Author     : Dell
--%>

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
                    <th>Num. Documento</th>
                    <th>Nombre</th>
                    <th>Primer Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Teléfono Principal</th>
                    <th>Dirección</th>
                    <th>Id. Usuario</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="colaborador" items="${lista}">               
                    <tr>
                        <td><c:out value="${colaborador.num_documento}" /></td>
                        <td><c:out value="${colaborador.nombre}" /></td>
                        <td><c:out value="${colaborador.apellido_1}" /></td>
                        <td><c:out value="${colaborador.apellido_2}" /></td>
                        <td><c:out value="${colaborador.telefono}" /></td>
                        <td><c:out value="${colaborador.direccion}" /></td>
                        <td><c:out value="${colaborador.usuario.id_usuario}" /></td> 
                        <td><a href="SvColaborador?accion=Ver_Empleado&id=<c:out value="${colaborador.usuario.id_usuario}" />">Ver Empleado</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <form action="vistasLog/administrador.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
        </form>
    </form>
    </body>
</html>
