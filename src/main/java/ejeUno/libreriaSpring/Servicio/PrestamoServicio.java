package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Entidad.Prestamo;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.LibroRepositorio;
import ejeUno.libreriaSpring.Repositorio.PrestamoRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio implements ValidacionInterface {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void guardarTransaccion(LocalDateTime fechaDevolucion, Integer cantidad, Cliente cliente, Libro libro) throws Exception, MiExcepcion {
        try {
            validaFechaDevolucion(fechaDevolucion);
            validaPresencia(cantidad, "cantidad");
            validaPresencia(cliente, "cliente");
            validaPresencia(libro, "libro");
            validaEstado(cliente.getEstado(), "Cliente");
            validaEstado(libro.getEstado(), "Libro");
            validaTransaccion(cantidad, libro.getEjemplaresRestantes(), libro.getEjemplares());
            Prestamo prestamo = new Prestamo();

            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + cantidad);
            libro.setEjemplaresRestantes(Math.abs(libro.getEjemplares() - libro.getEjemplaresPrestados()));
            prestamo.setCantidad(cantidad);
            prestamo.setFechaPrestamo(LocalDateTime.now());
            prestamo.setFechaDevolucion(fechaDevolucion);
            prestamo.setEstado(true);
            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);

            libroRepositorio.save(libro);
            prestamoRepositorio.save(prestamo);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void guardarTransaccion(String prestamoId, Integer cantidad, Cliente cliente, Libro libro) throws Exception, MiExcepcion {
        try {
            Prestamo prestamo = obtenerPrestamo(prestamoId);
            validaPresencia(cantidad, "cantidad");
            validaPresencia(cliente, "cliente");
            validaPresencia(libro, "libro");
            validaEstado(cliente.getEstado(), "Cliente");
            validaEstado(libro.getEstado(), "Libro");
            validaEstado(prestamo.getEstado(), "Prestamo");
            validaTransaccion(cantidad, prestamo.getCantidad(), libro.getEjemplares());

            libro.setEjemplaresPrestados(Math.abs(libro.getEjemplaresPrestados() - cantidad));
            libro.setEjemplaresRestantes(Math.abs(libro.getEjemplares() - libro.getEjemplaresPrestados()));
            prestamo.setCantidad(Math.abs(prestamo.getCantidad() - cantidad));
            prestamo.setCantidad(cantidad);
            prestamo.setFechaPrestamo(LocalDateTime.now());
            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);
            if (prestamo.getCantidad() <= 0) {
                prestamo.setEstado(false);
            }
            libroRepositorio.save(libro);
            prestamoRepositorio.save(prestamo);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Prestamo obtenerPrestamo(String id) throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
            validaPresencia(respuesta, "Prestamo");
            return prestamoRepositorio.findById(id).get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Prestamo> obtenerPrestamo() throws Exception {
        try {
            //return editorialRepositorio.obtenerEditoriales(true);
            return prestamoRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Prestamo> obtenerPrestamo(Boolean estado) throws Exception {
        try {
            return prestamoRepositorio.findAll(estado);
        } catch (Exception e) {
            throw e;
        }
    }
}
