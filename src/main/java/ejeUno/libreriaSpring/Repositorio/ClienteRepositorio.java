package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.estado = :estado")
    public List<Cliente> findAll(@Param("estado") Boolean estado);
    
    @Query("SELECT c FROM Cliente c WHERE c.usuario.rol.nombre = :rol")
    public List<Cliente> obtenerClientexRol(@Param("rol") String rol);
    
    @Query("SELECT c FROM Cliente c WHERE c.usuario.id = :id")
    public Optional<Cliente>  obtenerPerfil(@Param("id") String id);
}
