
package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    Optional<Usuario> findByCorreo(String correo);
}
