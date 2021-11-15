package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Servicio.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/rol")
public class RolControlador {

    @Autowired
    private RolServicio rolServicio;

    @PostMapping("/guardar")
    public RedirectView guardar(RedirectAttributes attributes) throws Exception {
        try {
            rolServicio.crear("CLIENTE");
            rolServicio.crear("ADMIN");
            attributes.addFlashAttribute("exito-name", "Se configuraron los roles exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/login");
    }
}
