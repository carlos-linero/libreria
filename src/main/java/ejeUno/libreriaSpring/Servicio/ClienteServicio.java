package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.ClienteRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio implements ValidacionInterface {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void guardarCliente(Usuario usuario) throws Exception, MiExcepcion {

        try {
            validacionPresencia(usuario, "Usuario");
            Cliente cliente = new Cliente();
            cliente.setUsuario(usuario);
            cliente.setNombre("Jane");
            cliente.setApellido("Doe");
            cliente.setEstado(true);

            clienteRepositorio.save(cliente);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void guardarCliente(String nombre, String apellido, Long documento, String telefono) throws Exception, MiExcepcion {

        try {
            validacionNombrePersona(nombre);
            validacionNombrePersona(apellido);
            validacionDocumento(documento);

            Cliente cliente = new Cliente();
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            cliente.setEstado(true);

            clienteRepositorio.save(cliente);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarCliente(String id, Boolean estado, Long cantidad) throws Exception, MiExcepcion {
        try {
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);
            validacionPresencia(respuesta, "Editorial");
            validacionPresencia(estado, "'Alta'");

            Cliente cliente = clienteRepositorio.findById(id).get();
            if (cliente.getUsuario().getEstado() == true) {
                validacionPrestados(cantidad);
            }
            estado = (estado) ? false : true;

            cliente.getUsuario().setEstado(estado);

            clienteRepositorio.save(cliente);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void modificarCliente(String nombre, String apellido, Long documento, String telefono, String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            validacionEstado(estado, "Libro");
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);

            validacionPresencia(respuesta, "Autor");
            validacionNombrePersona(nombre);
            validacionNombrePersona(apellido);

            Cliente cliente = clienteRepositorio.findById(id).get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDocumento(documento);
            cliente.setTelefono(telefono);

            clienteRepositorio.save(cliente);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Cliente obtenerCliente(String id) throws Exception, MiExcepcion {
        try {
            //return autorRepositorio.obtenerAutores(true);
            Cliente cliente = clienteRepositorio.findById(id).orElseThrow(() -> new MiExcepcion("Error al obtener cliente"));
            return cliente;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
        @Transactional(readOnly = true)
    public Cliente obtenerPerfil(String id) throws Exception, MiExcepcion {
        try {
         
            Cliente cliente = clienteRepositorio.obtenerPerfil(id).orElseThrow(() -> new MiExcepcion("Error al obtener perfil"));
            return cliente;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) { 
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerCliente() throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            return clienteRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
    
        @Transactional(readOnly = true)
    public List<Cliente> obtenerClientexRol(String rol) throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            return clienteRepositorio.obtenerClientexRol(rol);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerCliente(Boolean estado) throws Exception {
        try {
            return clienteRepositorio.findAll(estado);
        } catch (Exception e) {
            throw e;
        }
    }
}
