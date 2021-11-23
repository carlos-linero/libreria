
package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Autor;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
 List<Autor> findByEstado(Boolean estado);
}
