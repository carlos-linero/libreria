package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/inicio")
public class PrincipalControlador {

    @GetMapping
    public ModelAndView inicio(HttpServletRequest request) throws MiExcepcion, Exception {
        ModelAndView mav = new ModelAndView("index");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error-name"));
        }
        return mav;

    }

}
