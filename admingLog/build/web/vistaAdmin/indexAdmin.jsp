
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> ALMACEN </title>
    </head>
    <body>
        <h1> COLABORADORES </h1>
        <a href="url insertar nuevo">Ingresar Nuevo Colaborador </a>
        
        <br /> <br /> 
        
        <table border="1" width="80%">
            <thead>
                <tr>
                    <th>ID Empleado</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Telefono Principal</th>
                    <th>Dirrección</th>
                    <th>Identificación</th>
                    <th>Cargo</th>
                    <th>Estado</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
               
        <tbody>
            <c:forEach var="Usuario" items="${lista}">               
                
                <tr>
                    <td><c:out value="${Usuario}" /></td>
                    <td><c:out value="${producto.nombre}" /></td>
                    <td><c:out value="${producto.precio}" /></td>
                    <td><c:out value="${producto.existencia}" /></td>
                    <td><a href="ProductosController?accion=modificar&id=<c:out value="${producto.id}" />">Modificar</a></td>
                    <td><a href="ProductosController?accion=eliminarProductos&id=<c:out value="${producto.id}" />">Eliminar</a></td>
                </tr>
            </c:forEach>
        </tbody>
        
        </table>
       
        
    </body>
</html>
