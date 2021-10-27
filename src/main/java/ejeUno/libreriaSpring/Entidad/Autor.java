
package ejeUno.libreriaSpring.Entidad;

import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Autor {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable=false)
    private String nombre;
    @Column(nullable=false)
    private Boolean estado;

    public Autor() {
    }

    public Autor(String id, String nombre, Boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public static Comparator<Autor> compararNombre = new Comparator<Autor>(){
    @Override
    public int compare (Autor a1, Autor a2){
    return a1.getNombre().compareToIgnoreCase(a2.getNombre());
    }
    };
    
}

