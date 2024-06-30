package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import gestionReservas.*;
import exception.ReservaException;
import utils.FechaUtils;

public class MenuPrincipal {
    private GestionReservas gestionReservas = new GestionReservas(); // Se asume que GestionReservas tiene un constructor sin argumentos
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("=== Menú Principal ===");
            System.out.println("1. Registrar Instalación");
            System.out.println("2. Realizar Reserva");
            System.out.println("3. Mostrar Instalaciones");
            System.out.println("4. Mostrar Reservas");
            System.out.println("5. Cancelar Reserva");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = leerInt();
            switch (opcion) {
                case 1:
                    registrarInstalacion();
                    break;
                case 2:
                    realizarReserva();
                    break;
                case 3:
                    mostrarInstalaciones();
                    break;
                case 4:
                    mostrarReservas();
                    break;
                case 5:
                    cancelarReserva();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
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
    
        InstalacionDeportiva instalacion = new InstalacionDeportiva(0, nombre, tipo, capacidad, descripcion); // 0 porque el ID es generado por la base de datos
        
        gestionReservas.registrarInstalacion(instalacion);
        System.out.println("Instalación registrada con éxito.");
    }

    private void realizarReserva() {
        try {
            System.out.println("=== Realizar Reserva ===");
            Usuario usuario = obtenerUsuario();
            InstalacionDeportiva instalacion = obtenerInstalacion();
            LocalDate fechaInicio = FechaUtils.leerFecha(scanner, "Fecha de inicio (yyyy-MM-dd): ");
            LocalDate fechaFin = FechaUtils.leerFecha(scanner, "Fecha de fin (yyyy-MM-dd): ");

            gestionReservas.realizarReserva(usuario, instalacion, fechaInicio, fechaFin);
            System.out.println("Reserva realizada con éxito.");
        } catch (ReservaException e) {
            System.out.println("Error al realizar la reserva: " + e.getMessage());
        }
    }

    private void mostrarInstalaciones() {
        System.out.println("=== Listado de Instalaciones ===");
        List<InstalacionDeportiva> instalaciones = gestionReservas.getInstalaciones();
        for (InstalacionDeportiva instalacion : instalaciones) {
            System.out.println(instalacion.getId() + ". " + instalacion.getNombre() + " - " + instalacion.getTipo() + " - Capacidad: " + instalacion.getCapacidad());
            System.out.println("   Descripción: " + instalacion.getDescripcion());
        }
    }

    private void mostrarReservas() {
        System.out.println("=== Listado de Reservas ===");
        List<Reserva> reservas = gestionReservas.getReservas();
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    private void cancelarReserva() {
        System.out.print("Ingrese el ID de la reserva a cancelar: ");
        int idReserva = leerInt();
        try {
            gestionReservas.cancelarReserva(idReserva);
            System.out.println("Reserva cancelada correctamente.");
        } catch (ReservaException e) {
            System.out.println("Error al cancelar la reserva: " + e.getMessage());
        }
    }

    private Usuario obtenerUsuario() {
        System.out.print("Nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido del usuario: ");
        String apellido = scanner.nextLine();
        return new Usuario(nombre, apellido);
    }

    private InstalacionDeportiva obtenerInstalacion() {
        System.out.print("Nombre de la instalación: ");
        String nombre = scanner.nextLine();
        // Implementación para obtener una instalación específica
        // Puedes llamar métodos de GestionReservas o leer datos por consola
        return new InstalacionDeportiva(0, nombre, "Tipo", 10, "Descripción");
    }

    private int leerInt() {
        // Método auxiliar para leer un entero desde la consola
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrarMenu();
    }
}











