package gestionReservas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exception.ReservaException;
import utils.DatabaseConnection;

public class GestionReservas {
    private Connection connection;

    public GestionReservas() {
        try {
            connection = DatabaseConnection.getConnection();
            crearTablaInstalaciones();
            crearTablaReservas();
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        }
    }

    private void crearTablaInstalaciones() {
        String sql = "CREATE TABLE IF NOT EXISTS instalaciones (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "capacidad INTEGER NOT NULL," +
                "descripcion TEXT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'instalaciones' creada correctamente.");
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        }
    }

    private void crearTablaReservas() {
        String sql = "CREATE TABLE IF NOT EXISTS reservas (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "id_instalacion INTEGER NOT NULL," +
                     "fecha_inicio DATE NOT NULL," +
                     "fecha_fin DATE NOT NULL," +
                     "usuario_nombre TEXT NOT NULL," +
                     "usuario_apellido TEXT NOT NULL," +
                     "FOREIGN KEY (id_instalacion) REFERENCES instalaciones(id)" +
                     ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'reservas' creada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla 'reservas': " + e.getMessage());
        }
    }

    public void registrarInstalacion(InstalacionDeportiva instalacion) {
        if (instalacion == null) {
            throw new IllegalArgumentException("La instalación no puede ser nula.");
        }
        
        String sql = "INSERT INTO instalaciones(nombre, tipo, capacidad, descripcion) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, instalacion.getNombre());
            pstmt.setString(2, instalacion.getTipo());
            pstmt.setInt(3, instalacion.getCapacidad());
            pstmt.setString(4, instalacion.getDescripcion());

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                instalacion.setId(generatedKeys.getInt(1));
            }
            System.out.println("Instalación registrada en la base de datos.");
        } catch (SQLException e) {
            // Manejar la excepción adecuadamente
            e.printStackTrace();
        }
    }


    public List<InstalacionDeportiva> getInstalaciones() {
        List<InstalacionDeportiva> listaInstalaciones = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, capacidad, descripcion FROM instalaciones";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                int capacidad = rs.getInt("capacidad");
                String descripcion = rs.getString("descripcion");

                InstalacionDeportiva instalacion = new InstalacionDeportiva(id, nombre, tipo, capacidad, descripcion);
                listaInstalaciones.add(instalacion);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las instalaciones: " + e.getMessage());
        }

        return listaInstalaciones;
    }

    public void realizarReserva(Usuario usuario, InstalacionDeportiva instalacion, LocalDate fechaInicio, LocalDate fechaFin) throws ReservaException {
        if (!verificarDisponibilidad(instalacion, fechaInicio, fechaFin)) {
            throw new ReservaException("La instalación no está disponible en las fechas especificadas.");
        }

        String sql = "INSERT INTO reservas(id_instalacion, fecha_inicio, fecha_fin, usuario_nombre, usuario_apellido) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, instalacion.getId());
            pstmt.setString(2, fechaInicio.toString());
            pstmt.setString(3, fechaFin.toString());
            pstmt.setString(4, usuario.getNombre());

            pstmt.executeUpdate();

            System.out.println("Reserva realizada con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al realizar la reserva: " + e.getMessage());
        }
    }

    public List<Reserva> getReservas() {
        List<Reserva> listaReservas = new ArrayList<>();
        String sql = "SELECT id, id_instalacion, fecha_inicio, fecha_fin, usuario_nombre, usuario_apellido FROM reservas";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idInstalacion = rs.getInt("id_instalacion");
                LocalDate fechaInicio = LocalDate.parse(rs.getString("fecha_inicio"));
                LocalDate fechaFin = LocalDate.parse(rs.getString("fecha_fin"));
                String nombreUsuario = rs.getString("usuario_nombre");

                Reserva reserva = new Reserva(id, idInstalacion, fechaInicio, fechaFin, nombreUsuario);
                listaReservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las reservas: " + e.getMessage());
        }

        return listaReservas;
    }

    public void cancelarReserva(int reservaId) throws ReservaException {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservaId);

            int filasEliminadas = pstmt.executeUpdate();
            if (filasEliminadas == 0) {
                throw new ReservaException("No se encontró ninguna reserva con el ID especificado.");
            }

            System.out.println("Reserva cancelada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    private boolean verificarDisponibilidad(InstalacionDeportiva instalacion, LocalDate fechaInicio, LocalDate fechaFin) {
        String sql = "SELECT COUNT(*) AS count FROM reservas " +
                     "WHERE id_instalacion = ? AND " +
                     "(fecha_inicio BETWEEN ? AND ? OR fecha_fin BETWEEN ? AND ?)";
    
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, instalacion.getId());
            pstmt.setString(2, fechaInicio.toString());
            pstmt.setString(3, fechaFin.toString());
            pstmt.setString(4, fechaInicio.toString());
            pstmt.setString(5, fechaFin.toString());
    
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count == 0;  // Retorna true si no hay reservas que se superpongan
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la disponibilidad: " + e.getMessage());
        }
    
        return false;  // En caso de error, asume que no está disponible
    }
    
}






