package gestionReservas;

import java.util.List;
import java.util.Scanner;

import exception.ReservaException;

public class Administrador {
    private GestionReservas gestionReservas;
    private Scanner scanner = new Scanner(System.in);

    public Administrador() {
        try {
            gestionReservas = new GestionReservas(); // Asegúrate de que este constructor existe y no lanza excepciones no controladas.
        } catch (Exception e) {
            System.out.println("Error al inicializar GestionReservas: " + e.getMessage());
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("=== Menú de Administrador ===");
            System.out.println("1. Registrar Instalación");
            System.out.println("2. Listar Instalaciones");
            System.out.println("3. Listar Reservas");
            System.out.println("4. Cancelar Reserva");
            System.out.println("5. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1:
                    registrarInstalacion();
                    break;
                case 2:
                    listarInstalaciones();
                    break;
                case 3:
                    listarReservas();
                    break;
                case 4:
                    cancelarReserva();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
        gestionReservas.cerrarConexion(); // Cerrar la conexión al finalizar
    }

    private void registrarInstalacion() {
        System.out.println("=== Registro de Instalación ===");
        System.out.print("Nombre de la instalación: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo de instalación: ");
        String tipo = scanner.nextLine();
        System.out.print("Capacidad máxima: ");
        int capacidad = leerInt();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        InstalacionDeportiva instalacion = new InstalacionDeportiva(nombre, tipo, capacidad, descripcion);

        gestionReservas.registrarInstalacion(instalacion);
    }

    private void listarInstalaciones() {
        System.out.println("=== Listado de Instalaciones ===");
        List<InstalacionDeportiva> instalaciones = gestionReservas.getInstalaciones();
        for (InstalacionDeportiva instalacion : instalaciones) {
            System.out.println(instalacion);
        }
    }

    private void listarReservas() {
        System.out.println("=== Listado de Reservas ===");
        List<Reserva> reservas = gestionReservas.getReservas();
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    private void cancelarReserva() {
        System.out.print("Ingrese el ID de la reserva a cancelar: ");
        int reservaId = leerInt();
        try {
            gestionReservas.cancelarReserva(reservaId);
        } catch (ReservaException e) {
            System.out.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    private int leerInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        Administrador administrador = new Administrador();
        administrador.mostrarMenu();
    }
}

