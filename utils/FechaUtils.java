package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FechaUtils {

    public static LocalDate leerFecha(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String fechaStr = scanner.nextLine();
        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return fecha;
    }
}





