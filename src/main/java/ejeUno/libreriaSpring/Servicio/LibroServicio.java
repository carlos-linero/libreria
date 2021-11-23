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
            validacionIsbn(isbn, libroRepositorio.obtenerLibroxIsbn(isbn));
            validacionNombre(nombre, "Titulo");
            validacionAnio(anio);
            validacionCantidadEjemplar(ejemplares);
            validacionPresencia(autor, "Autor");
            validacionPresencia(editorial, "Editorial");

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
            
            validacionPresencia(autor, "Autor");
            validacionPresencia(editorial, "Editorial");
            validacionEstado(estado, "Libro");
            validacionNombre(nombre, "Titulo");
            validacionAnio(anio);
            validacionCantidadEjemplar(ejemplares);
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validacionPresencia(respuesta, "Libro");

            Libro libro = libroRepositorio.findById(id).get();
            validacionPrestados(libro.getEjemplaresPrestados());
            if (!libro.getIsbn().equals(isbn)) {
                validacionIsbn(isbn, libroRepositorio.obtenerLibroxIsbn(isbn));
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
    public void modificarLibro(String id, Boolean estado, Integer cantTotal, Integer cantDevolucion, Integer cantActual) throws Exception, MiExcepcion {
        try {
            validacionEstado(estado, "Libro");
            Optional<Libro> respuesta = libroRepositorio.findById(id);
            validacionPresencia(respuesta, "Libro");
            validacionTransaccion(cantDevolucion, cantActual, cantActual);

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
            validacionPresencia(respuesta, "Libro");

            Libro libro = libroRepositorio.findById(id).get();
            if (libro.getEstado() == false) {
                validacionIsbn(libro.getIsbn(), libroRepositorio.obtenerLibroxIsbn(libro.getIsbn()));
            }else if (libro.getEstado() == true) {
                validacionPrestados(libro.getEjemplaresPrestados());
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
    public Libro obtenerLibro(String id) throws Exception {
        try {
            Libro libro = libroRepositorio.findById(id).orElseThrow(() -> new MiExcepcion("Libro no registrado"));
            return libro;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibro() throws Exception {
        try {
            return libroRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibro(Boolean estado) throws Exception {
        try {
            return libroRepositorio.findAll(estado);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> obtenerLibro(String id, String tipo) throws Exception {
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
