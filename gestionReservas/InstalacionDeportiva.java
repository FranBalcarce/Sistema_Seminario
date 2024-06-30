package gestionReservas;

public class InstalacionDeportiva {
    private int id;
    private String nombre;
    private String tipo;
    private int capacidad;
    private String descripcion;

    public InstalacionDeportiva(String nombre, String tipo, int capacidad, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public InstalacionDeportiva(int id, String nombre, String tipo, int capacidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "InstalacionDeportiva [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + ", capacidad=" + capacidad
                + ", descripcion=" + descripcion + "]";
    }
}










