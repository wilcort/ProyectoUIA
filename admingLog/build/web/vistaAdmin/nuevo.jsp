<%-- 
    Document   : nuevo
    Created on : Apr 8, 2024, 6:02:16 PM
    Author     : Dell
--%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
    <h2>Nuevo Registro</h2>
    <br/> 
    <form action="SvColaborador" method="POST" autocomplete="off">
        <label for="cargo">Cargo:</label>
        <select id="cargo" name="cargo">
            <option value="Administrador">Administrador</option>
            <option value="Vendedor">Vendedor</option>
        </select><br><br>
    
        <label for="estado_cargo">Estado del Cargo:</label>
        <select id="estado_cargo" name="estado_cargo">
            <option value="1">Activo</option>
            <option value="0">Desactivado</option>
        </select><br><br>
        
        <label for="nombreUsuario">Nombre de Usuario:</label>
        <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>
    
        <label for="clave">Clave:</label>
        <input type="password" id="clave" name="clave" required><br><br>
    
        <label for="estado_usuario">Estado del Usuario:</label>
        <select id="estado_usuario" name="estado_usuario">
            <option value="1">Activo</option>
            <option value="0">Desactivado</option>
        </select><br><br>
        
        <label for="num_documento">N�mero de Documento:</label>
        <input id="num_documento" name="num_documento" type="text"  autocomplete="off" required /><br><br>
        
        <label for="nombre">Nombre:</label>
        <input id="nombre" name="nombre" type="text"  autocomplete="off" required /><br><br>
        
        <label for="apellido_1">Primer Apellido:</label>
        <input id="apellido_1" name="apellido_1" type="text"  autocomplete="off" required /><br><br>
        
        <label for="apellido_2">Segundo Apellido:</label>
        <input id="apellido_2" name="apellido_2" type="text"  autocomplete="off" required /><br><br>
        
        <label for="telefono">Tel�fono:</label>
        <input id="telefono" name="telefono" type="text"  autocomplete="off" required /><br><br>
        
        <label for="direccion">Direcci�n:</label>
        <input id="direccion" name="direccion" type="text"  autocomplete="off" required /><br><br>
        
        <button id="guardar" name="guardar" type="submit">Guardar</button>
        
        <!-- Agregado un campo oculto para enviar la acci�n "insertar" al servlet -->
        <input type="hidden" name="accion" value="insertar">
    </form>
</body>
</html>