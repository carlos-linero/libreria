package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, String>{

    Optional<Rol> findByNombre(String nombre);
    Boolean existsByNombre(String rolNombre);
}
