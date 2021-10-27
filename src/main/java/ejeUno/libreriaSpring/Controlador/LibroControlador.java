package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Autor;
import ejeUno.libreriaSpring.Entidad.Editorial;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.AutorServicio;
import ejeUno.libreriaSpring.Servicio.EditorialServicio;
import ejeUno.libreriaSpring.Servicio.LibroServicio;
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
            List<Libro> libros = libroServicio.obtenerLibros();
            List<Autor> autores = autorServicio.obtenerAutores();
            List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
            mav.addObject("libros", libros);
            mav.addObject("autores", autores);
            mav.addObject("editoriales", editoriales);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam Long isbn, @RequestParam String nombre, @RequestParam Integer anio, @RequestParam Integer Ejemplares, @RequestParam String autorId, @RequestParam String editorialId) throws Exception {
        try {
            Autor autor = autorServicio.obtenerAutor(autorId);
            Editorial editorial = editorialServicio.obteneEditorial(editorialId);
            libroServicio.crearLibro(isbn, nombre, anio, Ejemplares, autor, editorial);
            return new RedirectView("/libro");
        } catch (Exception e) {
            throw e;
        }
    }
}
