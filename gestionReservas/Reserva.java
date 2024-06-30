package gestionReservas;

import java.time.LocalDate;

public class Reserva {
    private int idReserva;
    private int idUsuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public Reserva(int idReserva, int idUsuario, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método toString para representar la reserva como String
    @Override
    public String toString() {
        return "Reserva [idReserva=" + idReserva + ", idUsuario=" + idUsuario + 
               ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + 
               ", estado=" + estado + "]";
    }
}



