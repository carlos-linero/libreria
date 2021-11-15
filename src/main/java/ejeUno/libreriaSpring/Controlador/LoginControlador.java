package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.RolServicio;
import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    private RolServicio rolServicio;

    @GetMapping
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal, HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("login");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        mav.addObject("roles", rolServicio.obtenerRol());
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        if (error != null) {
            mav.addObject("errorLog", "Correo o clave invalida");
        }
        if (logout != null) {
            mav.addObject("logout", "Ha finalizado sesión exitosamente");
        }
        if (principal != null) {
            mav.setViewName("redirect:/inicio");
        }
        
        return mav;
    }

}
