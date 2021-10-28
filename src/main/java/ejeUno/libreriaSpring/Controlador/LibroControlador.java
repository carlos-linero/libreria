package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.AutorServicio;
import ejeUno.libreriaSpring.Servicio.EditorialServicio;
import ejeUno.libreriaSpring.Servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping
    public ModelAndView mostrarLibros() throws MiExcepcion, Exception {
        try {
            ModelAndView mav = new ModelAndView("libro");
            mav.addObject("libros", libroServicio.obtenerLibros());
            mav.addObject("autores", autorServicio.obtenerAutores());
            mav.addObject("editoriales", editorialServicio.obtenerEditoriales());
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer Ejemplares, @RequestParam String autor, @RequestParam String editorial) throws Exception {
        try {
            Autor autorAux = autorServicio.obtenerAutor(autor);
            Editorial editorialAux = editorialServicio.obteneEditorial(editorial);
            libroServicio.guardarLibro(isbn, nombre, anio, Ejemplares, autorAux, editorialAux);
            return new RedirectView("/libro");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/editar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer Ejemplares, @RequestParam String id, @RequestParam Boolean estado) throws Exception {
        try {
            libroServicio.guardarLibro(isbn, nombre, anio, Ejemplares, id, estado);
            return new RedirectView("/libro");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id) throws Exception {
        try {
            libroServicio.modificarLibro(id, estado);
            return new RedirectView("/libro");
        } catch (Exception e) {
            throw e;
        }
    }
}
