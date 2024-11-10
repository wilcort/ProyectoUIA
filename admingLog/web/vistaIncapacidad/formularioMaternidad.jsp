<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Solicitud de Incapacidad por Maternidad</title>
        <link rel="stylesheet" href="styles.css"> <!-- Enlaza tu archivo CSS si lo tienes -->
    </head>
    <body>
        <div class="container">
            <h1>Formulario de Incapacidad por Maternidad</h1>
            <form action="${pageContext.request.contextPath}/SvIncapacidades" method="POST" enctype="multipart/form-data">

    
                <input type="hidden" name="accion" value="Enviar_Maternidad">

                 <input type="text" name="id_empleado" value="${sessionScope.id_empleado != null ? sessionScope.id_empleado : 'ID no disponible'}" readonly>


                <div class="form-group">
                    <label for="detalle">Detalle:</label>
                    <textarea id="detalle" name="detalle" rows="4" maxlength="255"  placeholder="Describe el detalle de la incapacidad"></textarea>
                </div>

                <div class="form-group">
                    <label for="semanas_de_gestacion">Semanas de Gestación:</label>
                    <input type="number" id="semanas_de_gestacion" name="semanas_de_gestacion" min="1" max="40" value="24" placeholder="Ingresa las semanas de gestación" readonly>
                </div>


                <div class="form-group">
                    <label for="documento">Adjuntar Documento:</label>
                    <input type="file" id="documento" name="documento" accept=".pdf,.jpg,.jpeg,.png,.doc,.docx" >
                </div>

                <button type="submit" class="btn-submit">Enviar Solicitud</button>
            </form>
        </div>

        <!-- Estilos CSS en línea para ejemplo (puedes mover esto a un archivo CSS) -->
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f9;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                max-width: 500px;
                padding: 20px;
                background-color: #ffffff;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                border-radius: 8px;
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .form-group {
                margin-bottom: 15px;
            }
            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
            }
            input[type="number"],
            input[type="file"],
            textarea {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
            }
            .btn-submit {
                width: 100%;
                padding: 10px;
                background-color: #4CAF50;
                border: none;
                color: white;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
            }
            .btn-submit:hover {
                background-color: #45a049;
            }
        </style>
    </body>
</html>
