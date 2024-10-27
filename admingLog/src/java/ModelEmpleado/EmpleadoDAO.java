/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import Configur.Conexion;
import Model.Cargo;
import Model.Colaborador;
import Model.Usuario;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class EmpleadoDAO {

    Connection conexion;

    public EmpleadoDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

//----------------------------------------------------------------------  
// ----------------------- MOSTRAR DATOS DEL EMPLEADO ------------------------
    public Colaborador mostrarEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Colaborador colaborador = null;
        try {
            ps = conexion.prepareStatement("SELECT e.id_empleado, e.num_documento, e.nombre, "
                    + "e.apellido_1, e.apellido_2, e.telefono, e.direccion, e.fecha_contratacion, "
                    + "e.salario_base, u.id_usuario, u.nombreUsuario, u.estadoUsuario, "
                    + "c.id_cargo, c.nombre_cargo, c.estado, h.id_horario, h.hora_entrada, h.hora_salida, "
                    + "h.horas_laborales, h.dias_laborales "
                    + "FROM empleado e "
                    + "INNER JOIN usuario u ON e.usuario_id_usuario = u.id_usuario "
                    + "LEFT JOIN cargo c ON e.id_cargo = c.id_cargo "
                    + "LEFT JOIN horarios h ON e.horarios_id_horario = h.id_horario "
                    + "WHERE u.id_usuario = ?");  // Cambiar esto

            // Establecer el parámetro
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {  // Cambiar while por if
                // Obtener los datos del colaborador
                int idEmpleado = rs.getInt("id_empleado");
                int num_documento = rs.getInt("num_documento");  // Cambiar a "num_documento"
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                java.sql.Date fecha_Contratacion = rs.getDate("fecha_contratacion");  // Cambiar a "fecha_contratacion"
                BigDecimal salario_Base = rs.getBigDecimal("salario_base");  // Cambiar a "salario_base"

                int id_usuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                boolean estadoUsuario = rs.getBoolean("estadoUsuario");

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setEstadoUsuario(estadoUsuario);

                // Datos del cargo
                int idCargo = rs.getInt("id_cargo");
                String nombreCargo = rs.getString("nombre_cargo");
                boolean estadoCargo = rs.getBoolean("estado");

                Cargo cargo = new Cargo();
                cargo.setEstado(estadoCargo);
                cargo.setIdCargo(idCargo);
                cargo.setNombreCargo(nombreCargo);

                // Datos de horario
                int idhorario = rs.getInt("id_horario");
                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");
                Double horasLaborales = rs.getDouble("horas_laborales");

                // Obtener el valor de la columna 'diasLaborales' como un String
                String diasLaboralesString = rs.getString("dias_laborales");
                Set<String> diasLaborales = new HashSet<>();
                if (diasLaboralesString != null) {
                    diasLaborales = new HashSet<>(Arrays.asList(diasLaboralesString.split(",")));
                }

                // Crear el objeto Horarios
                Horarios horarios = new Horarios();
                horarios.setHoraEntrada(horaEntrada);
                horarios.setHoraSalida(horaSalida);
                horarios.setHorasLaborales(horasLaborales);
                horarios.setDiasLaborales(diasLaborales);

                // Crear el objeto Colaborador
                colaborador = new Colaborador(idEmpleado, num_documento,
                        nombre, apellido_1, apellido_2, telefono, direccion,
                        fecha_Contratacion, fecha_Contratacion, // ¿esto debería ser fechaSalida?
                        salario_Base, usuario, cargo, horarios);

            } else {
                System.out.println("No se encontró el colaborador con empleado: " + idUsuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Cerrar PreparedStatement y ResultSet
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return colaborador;
    }

//-----------------------------------------------------------------------------
// --------------------- MARCAS EMPLEADO -----------------------------------
    public boolean realizarMarca(Marcas marca) {

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("INSERT INTO marcas ( "
                    + " fecha_marca, hora_entrada, hora_salida, "
                    + "hora_entrada_almuerzo, hora_salida_almuerzo, id_empleado) "
                    + "VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            // Obtener la fecha de marca
            java.util.Date fechaUtil = marca.getFechaMarca();

            // Verificar si fechaUtil es null
            if (fechaUtil == null) {
                throw new IllegalArgumentException("La fecha de marca no puede ser null");
            }

            // Convertir java.util.Date a java.sql.Date
            java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());

            // Usar el objeto java.sql.Date en el PreparedStatement
            ps.setDate(1, fechaSQL);
            ps.setTime(2, marca.getMarcaEntrada());
            ps.setTime(3, marca.getMarcaSalida());
            ps.setTime(4, marca.getMarcaEntradaAlmuerzo());
            ps.setTime(5, marca.getMarcaSalidaAlmuerzo());
            ps.setInt(6, marca.getIdEmpleado());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 1) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) { // Asegúrate de que hay un resultado
                        int nuevoId = generatedKeys.getInt(1);
                        marca.setIdMarca(nuevoId);
                    }
                }
                return true;
            } else {
                throw new SQLException("No se pudo insertar la marca");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Cambiado para un mejor diagnóstico
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }
    }

//---------------------------------------------------------------------
// --------------------- OBTENER EL ID EMPLEADO -----------------------------------
    public Integer obtenerIdEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer idEmpleado = null;

        try {
            ps = conexion.prepareStatement("SELECT e.id_empleado FROM empleado e "
                    + "INNER JOIN usuario u ON e.usuario_id_usuario = u.id_usuario "
                    + "WHERE u.id_usuario = ?");

            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                idEmpleado = rs.getInt("id_empleado");

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idEmpleado;
    }

//----------------------------------------------------------------------------
    public Marcas obtenerMarcaPorFecha(int idEmpleado, LocalDate fecha) {
        Marcas marca = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM marcas "
                    + "WHERE id_empleado = ? AND fecha_marca = ?");

            ps.setInt(1, idEmpleado);
            ps.setDate(2, java.sql.Date.valueOf(fecha));

            rs = ps.executeQuery();

            if (rs.next()) {
                marca = new Marcas();
                marca.setFechaMarca(rs.getDate("fecha_marca"));
                marca.setMarcaEntrada(rs.getTime("hora_entrada"));
                marca.setMarcaSalida(rs.getTime("hora_salida"));
                marca.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));
                marca.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marca;
    }

//--------------------------------------------
    public List<Marcas> obtenerTodasLasMarcas(int idEmpleado) {
        List<Marcas> marcas = new ArrayList<>();

        String sql = "SELECT * "
                + "FROM marcas WHERE id_empleado = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado); // Setear el ID del empleado
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Marcas marca = new Marcas();
                marca.setIdMarca(rs.getInt("id_marca"));
                marca.setFechaMarca(rs.getDate("fecha_marca"));
                marca.setMarcaEntrada(rs.getTime("hora_entrada"));
                marca.setMarcaSalida(rs.getTime("hora_salida"));
                marca.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
                marca.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));
                marca.setHorasDia(rs.getDouble("horas_trabajadas_dia"));

                marcas.add(marca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marcas;
    }
    //---------------------------------------------------------
    //-------------- CALCULO TOTAL QUINCENAS ---------------

    public double[] calculoHorasQuincenas(int idEmpleado, int inicio, int fin) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] horas = new double[1]; // Cambiamos a un arreglo de un elemento para almacenar el total

        try {
            ps = conexion.prepareStatement(" SELECT \n"
                    + "    SUM(CASE WHEN DAY(m.fecha_marca) BETWEEN ? AND ? "
                    + "THEN m.horas_trabajadas_dia ELSE 0 END) AS total_horas_quincena\n"
                    + "FROM \n"
                    + "    marcas m\n"
                    + "JOIN \n"
                    + "    empleado e ON m.id_empleado = e.id_empleado\n"
                    + "WHERE \n"
                    + "    MONTH(m.fecha_marca) = MONTH(CURRENT_DATE)\n"
                    + "AND e.id_empleado = ? "
                    + "GROUP BY \n"
                    + "    e.id_empleado;"); // No es necesario agrupar por nombre si solo queremos el total

            // Establecemos los parámetros de la consulta
            ps.setInt(1, inicio);
            ps.setInt(2, fin); // Día de inicio
            ps.setInt(3, idEmpleado); // ID del empleado

            // Ejecutamos la consulta
            rs = ps.executeQuery();

            if (rs.next()) {
                horas[0] = rs.getDouble("total_horas_quincena"); // Guardamos el total en el arreglo
            }

        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en caso de que ocurra
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return horas; // Retorna el arreglo con las horas trabajadas
    }

//-------------------------------------------------
    //-- cambio
    public List<Marcas> obtenerMarcasQuincena(int idEmpleado, int inicio, int fin) {
        List<Marcas> marcas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consulta SQL correctamente estructurada
            ps = conexion.prepareStatement("SELECT * FROM marcas "
                    + "WHERE id_empleado = ? "
                    + "AND DAY(fecha_marca) BETWEEN ? AND ?");

            // Establecemos los parámetros de la consulta
            ps.setInt(1, idEmpleado);
            ps.setInt(2, inicio); // Día de inicio
            ps.setInt(3, fin); // Día de fin

            // Ejecutamos la consulta
            rs = ps.executeQuery();

            // Procesamos el resultado
            while (rs.next()) {
                // Crear un objeto Marcas y agregarlo a la lista
                Marcas marcaUno = new Marcas();
                marcaUno.setFechaMarca(rs.getDate("fecha_marca"));
                marcaUno.setMarcaEntrada(rs.getTime("hora_entrada"));
                marcaUno.setMarcaSalida(rs.getTime("hora_salida"));
                marcaUno.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
                marcaUno.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));
                marcaUno.setHorasDia(rs.getDouble("horas_trabajadas_dia"));

                // Agregar la marca a la lista
                marcas.add(marcaUno);

            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Maneja la excepción adecuadamente
            }
        }

        return marcas;
    }

//-----------------------------------------------------------------
    public List<Marcas> obtenerMarcasPorMes(int idEmpleado, int mes, int anio) {
        List<Marcas> marcas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consulta SQL que filtra por mes y año
            ps = conexion.prepareStatement("SELECT * FROM marcas "
                    + "WHERE id_empleado = ? "
                    + "AND MONTH(fecha_marca) = ? "
                    + "AND YEAR(fecha_marca) = ?");

            // Establecemos los parámetros de la consulta
            ps.setInt(1, idEmpleado);
            ps.setInt(2, mes);
            ps.setInt(3, anio);

            // Ejecutamos la consulta
            rs = ps.executeQuery();

            // Procesamos el resultado
            while (rs.next()) {
                // Crear un objeto Marcas y agregarlo a la lista
                Marcas marcaUno = new Marcas();
                marcaUno.setIdMarca(rs.getInt("id_Marca"));
                marcaUno.setFechaMarca(rs.getDate("fecha_marca"));
                marcaUno.setMarcaEntrada(rs.getTime("hora_entrada"));
                marcaUno.setMarcaSalida(rs.getTime("hora_salida"));
                marcaUno.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
                marcaUno.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));
                marcaUno.setHorasDia(rs.getDouble("horas_trabajadas_dia"));

                // Agregar la marca a la lista
                marcas.add(marcaUno);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Maneja la excepción adecuadamente
            }
        }

        return marcas;
    }

//----------------------------- MENSAJE EMPLEADO -----------------
    public boolean enviarMensaje(int idEmpleado, String mensaje) {
        PreparedStatement ps = null;

        try {
            // Prepara la consulta de inserción
            ps = conexion.prepareStatement("INSERT INTO mensajes_empleado "
                    + "(id_empleado, mensaje) VALUES (?, ?)");

            // Asigna los valores a los placeholders de la consulta
            ps.setInt(1, idEmpleado);
            ps.setString(2, mensaje);

            // Ejecuta la inserción en la base de datos
            int rowsAffected = ps.executeUpdate();

            // Verifica si la inserción fue exitosa
            return rowsAffected > 0; // Si al menos una fila fue afectada, devuelve true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // En caso de error, devuelve false
        } finally {
            // Cerrar el PreparedStatement y la conexión
            try {
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//---------------------------------------------------------
//------- CORRECCION DE MARCAS EMPLEADO --------------------------------------

    public boolean correcionMarcas(Marcas marcas) {
        PreparedStatement ps = null;
        boolean isUpdated = false; // Para indicar si la actualización fue exitosa
        try {
            // Preparar la sentencia SQL para actualizar los registros
            ps = conexion.prepareStatement("UPDATE marcas SET fecha_marca = ?, "
                    + "hora_entrada = ?, "
                    + "hora_salida = ?, "
                    + "hora_entrada_almuerzo = ?, "
                    + "hora_salida_almuerzo = ?, " // Coma añadida aquí
                    + "horas_trabajadas_dia = ? "
                    + "WHERE id_marca = ?");

            // Usar el objeto java.sql.Date en el PreparedStatement
            ps.setDate(1, new java.sql.Date(marcas.getFechaMarca().getTime()));
            ps.setTime(2, marcas.getMarcaEntrada());
            ps.setTime(3, marcas.getMarcaSalida());
            ps.setTime(4, marcas.getMarcaEntradaAlmuerzo());
            ps.setTime(5, marcas.getMarcaSalidaAlmuerzo());
            ps.setDouble(6, marcas.getHorasTabajadasDia());
            ps.setInt(7, marcas.getIdMarca());

            // Ejecutar la actualización
            int rowsAffected = ps.executeUpdate();
            isUpdated = (rowsAffected > 0); // Verifica si la actualización fue exitosa

            if (isUpdated) {
                System.out.println("CAMBIO REALIZADO");
            } else {
                System.out.println("NO SE REALIZÓ NINGÚN CAMBIO"); // Mensaje opcional si no se actualizó ninguna fila
            }

        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza de la excepción para depuración
        } finally {
            if (ps != null) {
                try {
                    ps.close(); // Cerrar el PreparedStatement
                } catch (SQLException e) {
                    e.printStackTrace(); // Imprimir la traza de la excepción
                }
            }
        }
        return isUpdated;
    }

//--------------------------------------------------------
//---- MOSTRAR SOLO UNA MARCA -------------------
    
    public Marcas verSoloMarca(int idMarca) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Marcas marca = null; // Cambiamos a una variable de tipo Marcas

        try {
            String sql = "SELECT * FROM marcas WHERE id_marca = ?";
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMarca);
            rs = ps.executeQuery();

            // Revisar si hay resultados en el ResultSet
            if (rs.next()) {
                // Cambiar el tipo de los resultados de Date a Time para las horas
                java.util.Date fechaMarca = rs.getDate("fecha_marca");
                java.sql.Time horaEntrada = rs.getTime("hora_entrada"); // Se usa getTime
                java.sql.Time horaSalida = rs.getTime("hora_salida"); // Se usa getTime
                java.sql.Time horaEntradaAlmuerzo = rs.getTime("hora_entrada_almuerzo"); // Se usa getTime
                java.sql.Time horaSalidaAlmuerzo = rs.getTime("hora_salida_almuerzo"); // Se usa getTime

                // Crear la instancia de Marcas con los datos recuperados
                marca = new Marcas(idMarca, fechaMarca, horaEntrada, horaSalida,
                        horaEntradaAlmuerzo, horaSalidaAlmuerzo, 0,
                        null, null);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mejorar la gestión de excepciones
        } finally {
            // Cerrar recursos en el bloque finally para evitar fugas de recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return marca; // Retornar la marca o null si no se encontró
    }

//-------------------------------------------------------   
}

 


