package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, String>{

}
