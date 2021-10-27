
package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Libro;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
}
