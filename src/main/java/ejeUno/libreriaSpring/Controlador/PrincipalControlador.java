package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/inicio")
public class PrincipalControlador {
    
    @GetMapping
    public ModelAndView inicio() throws MiExcepcion, Exception{
        try {
            return new ModelAndView("index");
        } catch (Exception e) {
            throw e;
        }
    }

}
