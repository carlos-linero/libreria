package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.ClienteRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio implements ValidacionInterface {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public void guardarCliente(Long documento, String nombre, String apellido, Integer telefono) throws Exception, MiExcepcion {

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
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Transactional
    public void modificarCliente(String id, String nombre, String apellido, Integer telefono, Boolean estado) throws Exception, MiExcepcion {
        try {
            validaEstado(estado);
            Optional<Cliente> respuesta = clienteRepositorio.findById(id);
            
            validaPresencia(respuesta, "Autor");
            validacionNombrePersona(nombre);
            validacionNombrePersona(apellido);
            

            Cliente cliente = clienteRepositorio.findById(id).get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            clienteRepositorio.save(cliente);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
}
