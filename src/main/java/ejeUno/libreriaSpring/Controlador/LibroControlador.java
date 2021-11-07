package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.AutorServicio;
import ejeUno.libreriaSpring.Servicio.EditorialServicio;
import ejeUno.libreriaSpring.Servicio.LibroServicio;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public ModelAndView mostrarLibros(HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("libro");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        List<Libro> libros = libroServicio.obtenerLibro();
        libros.sort(Libro.compararNombre);
        mav.addObject("libros", libros);
        mav.addObject("autores", autorServicio.obtenerAutor());
        mav.addObject("editoriales", editorialServicio.obtenerEditorial());
        return mav;

    }

    @GetMapping("/bibliografia/{tipo}/{id}/{name}")
    public ModelAndView bibliografia(@PathVariable String tipo, @PathVariable String id, @PathVariable String name) throws MiExcepcion, Exception {

        try {

            ModelAndView mav = new ModelAndView("bibliografia");
            List<Libro> libros = libroServicio.obtenerLibro(id, tipo);
            libros.sort(Libro.compararNombre);
            mav.addObject("name", name);
            mav.addObject("tipo", tipo);
            mav.addObject("libros", libros);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer Ejemplares, @RequestParam String autor, @RequestParam String editorial, RedirectAttributes attributes) throws Exception {
        try {
            Autor autorAux = autorServicio.obtenerAutor(autor);
            Editorial editorialAux = editorialServicio.obtenerEditorial(editorial);
            libroServicio.guardarLibro(isbn, nombre, anio, Ejemplares, autorAux, editorialAux);
            attributes.addFlashAttribute("exito-name", "El Libro ha sido registrado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/libro");
    }

    @PostMapping("/editar")
    public RedirectView guardar(@RequestParam String editorial, @RequestParam String autor, @RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer Ejemplares, @RequestParam String id, @RequestParam Boolean estado, RedirectAttributes attributes) throws Exception {
        try {
            Editorial editorialAux = editorialServicio.obtenerEditorial(editorial);
            Autor autorAux = autorServicio.obtenerAutor(autor);
            libroServicio.modificarLibro(editorialAux, autorAux, isbn, nombre, anio, Ejemplares, id, estado);
            attributes.addFlashAttribute("exito-name", "El Libro ha sido editado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/libro");
    }

    @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id, RedirectAttributes attributes) throws Exception {
        try {
            libroServicio.modificarLibro(id, estado);
            attributes.addFlashAttribute("exito-name", "El Libro ha sido " + ((estado) ? "deshabilitado" : "habilitado") + " exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/libro");
    }
}
