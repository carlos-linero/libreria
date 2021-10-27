package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    /*@GetMapping
    public ModelAndView inicio() throws MiExcepcion, Exception {
        try {
            return new ModelAndView("autor");
        } catch (Exception e) {
            throw e;
        }
    }*/
    @GetMapping
    public ModelAndView mostrarAutores() throws MiExcepcion, Exception {
        try {
            ModelAndView mav = new ModelAndView("autor");
            List<Autor> autores = autorServicio.obtenerAutores();
            autores.sort(Autor.compararNombre);
            mav.addObject("autores", autores);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    /*@GetMapping("/crear")
    public ModelAndView crearAutor() throws Exception {
        try {
            ModelAndView mav = new ModelAndView("formulario-autor-editorial");
            mav.addObject("autor", new Autor());
            mav.addObject("title", "Crear Usuario");
            mav.addObject("action", "guardar");
            return mav;
        } catch (Exception e) {
            throw e;
        }
    }*/

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam String nombre) throws Exception {
        try {
            autorServicio.crearAutor(nombre);
            return new RedirectView("/autor");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/modificar-nombre")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam Boolean estado, @RequestParam String id) throws Exception {
        try {
            autorServicio.modificarAutor(id, nombre, estado);
            return new RedirectView("/autor");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id) throws Exception {
        try {
            autorServicio.modificarAutor(id, estado);
            return new RedirectView("/autor");
        } catch (Exception e) {
            throw e;
        }
    }

}
