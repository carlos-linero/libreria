
package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {
    
    @Transactional
    public void guardarPrestamo() throws Exception, MiExcepcion {
    
    }
}
