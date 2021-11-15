package ejeUno.libreriaSpring.Entidad;

import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private Long documento;

    private String nombre;

    private String apellido;

    private String telefono;
    @Column(nullable = false)
    private Boolean estado;
    @OneToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    public Cliente() {
    }

    public Cliente(String id, Long documento, String nombre, String apellido, String telefono, Boolean estado, Usuario usuario) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.estado = estado;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDocumento() {
        return documento;
    }

    public void setDocumento(Long documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static Comparator<Cliente> getCompararNombre() {
        return compararNombre;
    }

    public static void setCompararNombre(Comparator<Cliente> compararNombre) {
        Cliente.compararNombre = compararNombre;
    }

    public static Comparator<Cliente> compararNombre = new Comparator<Cliente>() {
        @Override
        public int compare(Cliente c1, Cliente c2) {
            return c1.getNombre().compareToIgnoreCase(c2.getNombre());
        }
    };
}
