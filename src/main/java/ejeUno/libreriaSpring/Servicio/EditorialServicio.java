package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.EditorialRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio implements ValidacionInterface {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws Exception, MiExcepcion {
        try {
            Editorial editorial = new Editorial();
            validacionNombrePersona(nombre);

            editorial.setNombre(nombre);
            editorial.setEstado(true);

            editorialRepositorio.save(editorial);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarEditorial(String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            Optional<Editorial> respuesta = editorialRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");

            Editorial editorial = editorialRepositorio.findById(id).get();
            estado = (estado) ? false : true;

            editorial.setEstado(estado);

            editorialRepositorio.save(editorial);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarEditorial(String id, String nombre, Boolean estado) throws Exception, MiExcepcion {
        try {
            validaEstado(estado);
            Optional<Editorial> respuesta = editorialRepositorio.findById(id);

            validacionNombrePersona(nombre);
            validaPresencia(respuesta, "Editorial");

            Editorial editorial = editorialRepositorio.findById(id).get();
            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> obtenerEditoriales() throws Exception {
        try {
            //return editorialRepositorio.obtenerEditoriales(true);
            return editorialRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Editorial obteneEditorial(String id) throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
             Optional<Editorial> respuesta = editorialRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");
            return editorialRepositorio.findById(id).get();
        } catch (Exception e) {
            throw e;
        }
    }
}
