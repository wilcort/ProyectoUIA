<%-- 
    Document   : identificar
    Created on : Mar 29, 2024, 10:56:44 AM
    Author     : Dell
--%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Iniciar Sesión - Sophie Store</title>
        <!-- Bootstrap CSS (Versión 4) -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Font Awesome CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <!-- Custom CSS -->
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Arial', sans-serif;
            }
            .login-box {
                width: 100%;
                max-width: 400px;
                margin: 100px auto;
                padding: 30px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .login-logo a {
                font-size: 2rem;
                color: #007bff;
                font-weight: bold;
            }
            .login-box-body {
                padding: 20px;
            }
            .form-control {
                margin-bottom: 15px;
                border-radius: 50px;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                border-radius: 30px;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            #errorMessage {
                color: red;
                text-align: center;
                margin-top: 10px;
            }
        </style>
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo text-center">
                <a href="#"><b>Sophie Store</b></a>
            </div>
            <div class="login-box-body">
                <p class="login-box-msg text-center">Iniciar sesión para acceder al sistema</p>

                <form action="SvLogin?accion=verificar" method="POST">
                    <div class="form-group has-feedback">
                        <input type="text" name="txtUsu" id="txtUsu" class="form-control" placeholder="Usuario" required>
                        <span class="fas fa-user form-control-feedback"></span>
                    </div>
                    <div class="form-group has-feedback">
                        <input type="password" name="txtPass" id="txtPass" class="form-control" placeholder="Contraseña" required>
                        <span class="fas fa-lock form-control-feedback"></span>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <input type="submit" name="verificar" value="Iniciar Sesión" class="btn btn-primary btn-block">
                        </div>
                    </div>
                </form>

                <!-- Mensaje de error -->
                <% String mensajeError = (String) request.getAttribute("msj");
                    if (mensajeError != null && !mensajeError.isEmpty()) { %>
                <p id="errorMessage"><%= mensajeError %></p>
                <% } %>
            </div>
            <!-- /.login-box-body -->
        </div>
        <!-- /.login-box -->

        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>

        <script>
            // Mostrar el mensaje de error si no está vacío
            $(document).ready(function () {
                var errorMessage = $("#errorMessage").text().trim();
                if (errorMessage !== "") {
                    $("#errorMessage").fadeIn().delay(2000).fadeOut();
                }
            });
        </script>
    </body>
</html>
