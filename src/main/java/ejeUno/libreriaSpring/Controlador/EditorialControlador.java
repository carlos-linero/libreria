package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.EditorialServicio;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public ModelAndView mostrarEditoriales(HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("editorial");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        List<Editorial> editoriales = editorialServicio.obtenerEditorial();
        editoriales.sort(Editorial.compararNombre);
        mav.addObject("editoriales", editoriales);
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes attributes) throws Exception {
        try {
            editorialServicio.crearEditorial(nombre);
            attributes.addFlashAttribute("exito-name", "La editorial ha sido registrada exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @PostMapping("/modificar-nombre")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam Boolean estado, @RequestParam String id, RedirectAttributes attributes) throws Exception {
        try {
            editorialServicio.modificarEditorial(id, nombre, estado);
            attributes.addFlashAttribute("exito-name", "La editorial ha sido editada exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorial");
    }

    @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id, RedirectAttributes attributes) throws Exception {
        try {
            editorialServicio.modificarEditorial(id, estado);
            attributes.addFlashAttribute("exito-name", "La editorial ha sido " + ((estado) ? "deshabilitada" : "habilitada") + " exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorial");
    }
}
