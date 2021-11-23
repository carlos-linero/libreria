package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {

    @Query("SELECT p FROM Prestamo p WHERE p.cliente.id = :id and p.estado = true")
    public List<Prestamo> obtenerPrestamos(@Param("id") String id);

    @Query("SELECT p FROM Prestamo p WHERE p.estado = :estado and p.cliente.usuario.rol.nombre = :rol")
    public List<Prestamo> prestamoRol(@Param("estado") Boolean estado, @Param("rol") String rol);
    
        @Query("SELECT p FROM Prestamo p WHERE p.estado = :estado and p.cliente.usuario.id = :id")
    public List<Prestamo> prestamoPerfil(@Param("estado") Boolean estado, @Param("id") String id);

    @Query("SELECT p FROM Prestamo p WHERE p.estado = :estado and p.cliente.id = :id")
    public List<Prestamo> findAll(@Param("estado") Boolean estado, @Param("id") String id);

    @Query("SELECT count(p) FROM Prestamo p WHERE p.cliente.id = :id and p.estado = true")
    public Long cantidadPrestamo(@Param("id") String id);

    @Query("SELECT p FROM Prestamo p WHERE p.cliente.id = :id and p.estado = true")
    public Long prestamosClienteId(@Param("id") String id);
    
    List<Prestamo> findByEstado(Boolean estado);

}
