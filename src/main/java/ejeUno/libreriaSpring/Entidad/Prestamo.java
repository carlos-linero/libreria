package ejeUno.libreriaSpring.Entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private LocalDate fechaPrestamo;
    @Column(nullable = false)
    private LocalDate fechaDevolucion;
    @Column(nullable = false)
    private Boolean estado;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private Integer cantidadRegistro;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Libro libro;

    public Prestamo() {
    }

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucion, Boolean estado, Integer cantidad, Integer cantidadRegistro, Cliente cliente, Libro libro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.cantidad = cantidad;
        this.cantidadRegistro = cantidadRegistro;
        this.cliente = cliente;
        this.libro = libro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadRegistro() {
        return cantidadRegistro;
    }

    public void setCantidadRegistro(Integer cantidadRegistro) {
        this.cantidadRegistro = cantidadRegistro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public static Comparator<Prestamo> getCompararNombre() {
        return compararNombre;
    }

    public static void setCompararNombre(Comparator<Prestamo> compararNombre) {
        Prestamo.compararNombre = compararNombre;
    }


    public static Comparator<Prestamo> compararNombre = new Comparator<Prestamo>() {
        @Override
        public int compare(Prestamo p1, Prestamo p2) {
            return p1.getCliente().getNombre().compareToIgnoreCase(p2.getCliente().getNombre());
        }
    };
}
