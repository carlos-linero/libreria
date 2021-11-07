package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.estado = :estado")
    public List<Cliente> findAll(@Param("estado") Boolean estado);
}
