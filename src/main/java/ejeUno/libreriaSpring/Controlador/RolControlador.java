package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Servicio.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rol")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;
}
