package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import org.springframework.stereotype.Service;
import ejeUno.libreriaSpring.Repositorio.AutorRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio implements ValidacionInterface {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws Exception, MiExcepcion {
        try {
            Autor autor = new Autor();
            validacionNombrePersona(nombre);

            autor.setNombre(nombre);
            autor.setEstado(true);

            autorRepositorio.save(autor);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarAutor(String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            Optional<Autor> respuesta = autorRepositorio.findById(id);

            validaPresencia(respuesta, "Autor");
            validaPresencia(estado, "'Alta'");

            Autor autor = autorRepositorio.findById(id).get();
            estado = (estado) ? false : true;

            autor.setEstado(estado);

            autorRepositorio.save(autor);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarAutor(String id, String nombre, Boolean estado) throws Exception, MiExcepcion {
        try {
            validaEstado(estado, "Autor");
            Optional<Autor> respuesta = autorRepositorio.findById(id);

            validacionNombrePersona(nombre);
            validaPresencia(respuesta, "Autor");

            Autor autor = autorRepositorio.findById(id).get();
            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Autor obtenerAutor(String id) throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            Optional<Autor> respuesta = autorRepositorio.findById(id);
            validaPresencia(respuesta, "Autor");
            Autor autor = autorRepositorio.findById(id).get();
            validaEstado(autor.getEstado(), "Autor");
            return autor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> obtenerAutor() throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            return autorRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
