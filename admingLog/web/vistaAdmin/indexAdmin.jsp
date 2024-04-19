
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
        <a href="SvColaborador?accion=nuevo">Ingresar Nuevo Colaborador</a>
        
        <br /> <br /> 
        
        <table border="1" width="80%">
            <thead>
                <tr>
                    <th>Num. Documento</th>
                    <th>Nombre</th>
                    <th>Prime Apellido</th>
                    <th>Segundo Apellido</th>
                    <th>Telefono Principal</th>
                    <th>Dirrección</th>
                    <th>Id. Usuario</th>
                    <th>Cargo</th>
                    <th>Estado</th>                   
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
                    <td><c:out value="${colaborador.usuario.cargo.nombreCargo}" /></td>
                    <td><c:out value="${colaborador.usuario.cargo.estado}" /></td>
                       
                    <td><a href="ProductosController?accion=modificar&id=<c:out value="${producto.id}" />">Modificar</a></td>
                    <td><a href="ProductosController?accion=eliminarProductos&id=<c:out value="${producto.id}" />">Eliminar</a></td>
                </tr>
            </c:forEach>
        </tbody>
        
        </table>
       
        
    </body>
</html>