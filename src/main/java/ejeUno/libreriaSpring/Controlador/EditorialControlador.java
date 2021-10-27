package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping
    public ModelAndView mostrarEditoriales() throws MiExcepcion, Exception {
        try {
            ModelAndView mav = new ModelAndView("editorial");
            List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
            editoriales.sort(Editorial.compararNombre);
            mav.addObject("editoriales", editoriales);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) throws Exception {
        try {
            editorialServicio.crearEditorial(nombre);
            return new RedirectView("/editorial");
        } catch (Exception e) {
            throw e;
        }
    }
    
       @PostMapping("/modificar-nombre")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam Boolean estado, @RequestParam String id) throws Exception {
        try {
            editorialServicio.modificarEditorial(id, nombre, estado);
            return new RedirectView("/editorial");
        } catch (Exception e) {
            throw e;
        }
    }
    
        @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id) throws Exception {
        try {
            editorialServicio.modificarEditorial(id, estado);
            return new RedirectView("/editorial");
        } catch (Exception e) {
            throw e;
        }
    }
}
