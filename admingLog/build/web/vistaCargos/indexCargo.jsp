<%-- 
    Document   : indexCargo
    Created on : Aug 10, 2024, 8:25:08 PM
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
    <a href="SvCargo?accion=nuevo">Crear Nuevo Cargo</a>

    <br/><br/>

    <table border="1" width="100%">
        <thead>
            <tr>
                <th>ID. Cargo</th>
                <th>Nombre Cargo</th>
                <th>Estado del Cargo</th>
                <th></th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="cargo" items="${listaCargos}">
                <tr>
                    <td>${cargo.idCargo}</td>
                    <td>${cargo.nombreCargo}</td>
                    <td><c:choose>
                        <c:when test="${cargo.estado}">Activo</c:when>
                        <c:otherwise>Inactivo</c:otherwise>                        
                    </c:choose>
                    </td>
                    <td>
                     <a href="SvCargo?accion=Ver_Cargo&id=<c:out value="${cargo.idCargo}" />">Información del Cargo</a>  
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <form action="vistasLog/administrador.jsp" style="display: inline; margin-left: 10px;">
        <button type="submit">Regresar</button>
    </form>
    
</body>
</html>