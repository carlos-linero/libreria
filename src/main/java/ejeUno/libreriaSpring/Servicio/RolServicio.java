package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.RolRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio implements ValidacionInterface {

    @Autowired
    private RolRepositorio rolRepositorio;

    @Transactional
    public void crear(String rolNombre) throws Exception, MiExcepcion {
        try {
            validacionNombre(rolNombre, "Rol");
            
            if (rolRepositorio.existsByNombre(rolNombre)) {
                throw new MiExcepcion("Ya existe un rol con ese nombre");
            }
            
            Rol rol = new Rol();
            rol.setNombre(rolNombre);
            rolRepositorio.save(rol);
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Rol> obtenerRol() throws Exception {
        try {

            return rolRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Rol obtenerRol(String nombre) throws Exception, MiExcepcion {
        try {
            Rol rol = rolRepositorio.findByNombre(nombre).orElseThrow(() -> new MiExcepcion("Rol no registrado"));
            return rol;
            
        } catch (Exception e) {
            throw e;
        }
    }
}
