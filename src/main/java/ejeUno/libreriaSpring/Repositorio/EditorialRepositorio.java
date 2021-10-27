package ejeUno.libreriaSpring.Repositorio;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Entidad.Editorial;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT e FROM Editorial e WHERE e.estado = :estado")
    public List<Editorial> obtenerEditoriales(@Param("estado") Boolean estado);
}
