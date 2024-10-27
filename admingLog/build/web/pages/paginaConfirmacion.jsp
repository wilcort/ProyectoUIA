<%-- 
    Document   : paginaConfirmacion
    Created on : Oct 21, 2024, 6:06:11 PM
    Author     : Dell
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Confirmación de Envío</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            color: #343a40;
            text-align: center;
            padding: 50px;
        }
        .container {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 400px;
            margin: auto;
        }
        h1 {
            color: #28a745; /* Color verde para el título */
        }
        p {
            margin: 20px 0;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff; /* Color azul para el botón */
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        a:hover {
            background-color: #0056b3; /* Color más oscuro al pasar el ratón */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Mensaje Enviado</h1>
        <p>Tu mensaje ha sido enviado con éxito.</p>
        <p>Gracias por contactarnos.</p>
        
        
        <!-- Formulario para regresar a la página principal -->
        <form action="http://localhost:8080/admingLog/vistasLog/empleado.jsp" style="display: inline; margin-left: 10px;">
            <button type="submit">Regresar</button>
    
    
    </div>
</body>
</html>
