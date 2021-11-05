package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.LibroRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio implements ValidacionInterface {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void guardarLibro(Long isbn, String nombre, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception, MiExcepcion {
        try {
            validaIsbn(isbn, libroRepositorio.obtenerLibroxIsbn(isbn));
            validacionNombre(nombre);
            validacionAnio(anio);
            validaCantidadEjemplar(ejemplares);
            validaPresencia(autor, "Autor");
            validaPresencia(editorial, "Editorial");

            Libro libro = new Libro();
            libro.setEstado(true);
            libro.setIsbn(isbn);
            libro.setNombre(nombre);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libroRepositorio.save(libro);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarLibro(Editorial editorial, Autor autor, Long isbn, String nombre, Integer anio, Integer ejemplares, String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            validaPresencia(autor, "Autor");
            validaPresencia(editorial, "Editorial");
            validaEstado(estado);
            validacionNombre(nombre);
            validacionAnio(anio);
            validaCantidadEjemplar(ejemplares);
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validaPresencia(respuesta, "Libro");

            Libro libro = libroRepositorio.findById(id).get();
            if (!libro.getIsbn().equals(isbn)) {
                validaIsbn(isbn, libroRepositorio.obtenerLibroxIsbn(isbn));
            }
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setIsbn(isbn);
            libro.setNombre(nombre);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarLibro(String id, Integer cantTotal, Integer cantPrestamo, Integer cantActual, Boolean estado) throws Exception, MiExcepcion {
        try {
            validaEstado(estado);
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");
            validaTransaccion(cantPrestamo, cantActual, cantActual);

            Libro libro = libroRepositorio.findById(id).get();
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + cantPrestamo);
            libro.setEjemplaresRestantes(Math.abs(cantPrestamo - cantActual));

            libroRepositorio.save(libro);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarLibro(String id, Boolean estado, Integer cantTotal, Integer cantDevolucion, Integer cantActual) throws Exception, MiExcepcion {
        try {
            validaEstado(estado);
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");
            validaTransaccion(cantDevolucion, cantActual, cantActual);

            Libro libro = libroRepositorio.findById(id).get();
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - cantDevolucion);
            libro.setEjemplaresRestantes(cantDevolucion + cantActual);

            libroRepositorio.save(libro);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarLibro(String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validaPresencia(respuesta, "Libro");

            Libro libro = libroRepositorio.findById(id).get();
            if (libro.getEstado() == false) {
                validaIsbn(libro.getIsbn(), libroRepositorio.obtenerLibroxIsbn(libro.getIsbn()));
            }

            estado = (estado) ? false : true;

            libro.setEstado(estado);

            libroRepositorio.save(libro);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros() throws Exception {
        try {
            return libroRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibros(String id, String tipo) throws Exception {
        try {
            if (tipo.equalsIgnoreCase("autor")) {
                return libroRepositorio.obtenerAutor(id);
            } else if (tipo.equalsIgnoreCase("editorial")) {
                return libroRepositorio.obtenerEditorial(id);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
