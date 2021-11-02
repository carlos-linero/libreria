package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Libro;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id and l.estado = true")
    public List<Libro> obtenerAutor(@Param("id") String id);

    @Query("SELECT l FROM Libro l WHERE l.editorial.id = :id and l.estado = true")
    public List<Libro> obtenerEditorial(@Param("id") String id);

    @Query("SELECT count(l) FROM Libro l WHERE l.isbn = :isbn and l.estado = true")
    public Long obtenerLibroxIsbn(@Param("isbn") Long isbn);
}
