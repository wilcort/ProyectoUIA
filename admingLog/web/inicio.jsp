<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Página de Inicio - Sophie Store</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Font Awesome CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- Custom CSS -->
        <style>
            body {
                background-color: #f0f8ff;
                font-family: 'Arial', sans-serif;
            }
            .container {
                margin-top: 100px;
            }
            .logo {
                width: 150px;
                margin-bottom: 30px;
            }
            h1 {
                color: #343a40;
                font-size: 2.5rem;
                font-weight: bold;
            }
            p {
                color: #6c757d;
                font-size: 1.2rem;
                margin-bottom: 30px;
            }
            .btn-custom {
                background-color: #007bff;
                color: white;
                font-size: 1.2rem;
                padding: 12px 25px;
                border-radius: 30px;
                transition: background-color 0.3s;
            }
            .btn-custom:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container text-center">
            <!-- Logo -->

           <img src="logo/logo.jpg" alt="Sophie Store Logo" class="img-fluid">

            <!-- Título -->
            <h1>Bienvenido a Sophie Store</h1>
            
            <!-- Descripción -->
            <p>Por favor, inicia sesión para continuar.</p>
            
            <!-- Botón de inicio de sesión -->
            <a href="identificar.jsp" class="btn btn-custom">
                <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
            </a>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
