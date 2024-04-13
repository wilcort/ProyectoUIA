<%-- 
    Document   : nuevo
    Created on : Apr 8, 2024, 6:02:16 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <h2>Nuevo Registro</h2>
        
        <br /> 
        
        <form action="SvColaborador?accion=insertar" method="POST" autocomplete="off">
            
            
            <label for="cargo">Cargo:</label>
            <select id="cargo" name="cargo">
            <option value="Administrador">Administrador</option>
            <option value="Vendedor">Vendedor</option>
            </select><br><br>
        
            <label for="estado">Estado:</label>
            <select id="estado" name="estado">
            <option value="1">Activo</option>
            <option value="0">Desactivado</option>
           </select><br><br>
            
            <input type="hidden" name="accion" value="insertarUsuario">
            
            <label for="nombreUsuario">Nombre de Usuario:</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>
        
            <label for="clave">Clave:</label>
            <input type="password" id="clave" name="clave" required><br><br>
        
            <label for="estado">Estado:</label>
            <select id="estado" name="estado">
            <option value="1">Activo</option>
            <option value="0">Desactivado</option>
           </select><br><br>
            
            
            <p>
                Número Documento:
                <input id="num_documento" name="codigo" type="text" />
            </p>
            
            <p>
                Nombre:
                <input id="nombre" name="nombre" type="text" />
            </p>
            
            <p>
                Primer Apellido
                <input id="apellido_1" name="precio" type="text" />
            </p>
            
            <p>
                Segundo Apellido
                <input id="apellido_2" name="precio" type="text" />
            </p>
            
            <p>
                Teléfono:
                <input id="telefono" name="existencia" type="text" />
            </p>
            
             <p>
                Dirección
                <input id="direccion" name="existencia" type="text" />
            </p>
            
            <button id="guardar" name="guardar" type="submit"> Guardar </button>
            
        </form>
    </body>
</html>