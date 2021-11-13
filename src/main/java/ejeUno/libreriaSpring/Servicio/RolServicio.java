package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Repositorio.RolRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServicio {
    
    @Autowired
    private RolRepositorio rolRepositorio;
    
    public void crear(String rolNombre) throws Exception{
        try {
            Rol rol = new Rol();
            rol.setNombre(rolNombre);
        } catch (Exception e) {
            throw e;
        }
    }

        @Transactional(readOnly = true)
    public List<Rol> obtenerrOL(String id) throws Exception {
        try {

            return rolRepositorio.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
