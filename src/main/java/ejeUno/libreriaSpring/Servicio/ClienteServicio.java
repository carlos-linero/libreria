package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Cliente;
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
    public void guardarCliente(String nombre, String apellido, Long documento, String telefono) throws Exception, MiExcepcion {

        try {
            validacionNombrePersona(nombre);
            validacionNombrePersona(apellido);
            validaDocumento(documento);

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
    public void modificarCliente(String id, Boolean estado) throws Exception, MiExcepcion {
        try {
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");
            validaPresencia(estado, "'Alta'");

            Cliente cliente = clienteRepositorio.findById(id).get();
            estado = (estado) ? false : true;

            cliente.setEstado(estado);

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
            validaEstado(estado);
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);

            validaPresencia(respuesta, "Autor");
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
    public Cliente obtenerCliente(String id) throws Exception {
        try {
            //return autorRepositorio.obtenerAutores(true);
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);
            validaPresencia(respuesta, "Editorial");
            return clienteRepositorio.findById(id).get();
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

}
