package ejeUno.libreriaSpring.Controlador;

import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView errores(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("errores");
        String mensaje = "";
        Integer codigo = response.getStatus();
 
        switch (codigo) {
            case 403:
                mensaje = "Informacion confidencial solo disponible para admin";
                break;
            case 404:
                mensaje = "Como aguja en un pajar lo solicitado no fue encontrado";
                break;
            case 500:
                mensaje = "Error interno en el servidor, el servidor  al final no sirvio ba-dum-tsss";
                break;

            default:
                mensaje = "Error inesperado";
                break;
        }
        mav.addObject("mensaje", mensaje);
        mav.addObject("codigo", codigo);
        return mav;
    }
}
