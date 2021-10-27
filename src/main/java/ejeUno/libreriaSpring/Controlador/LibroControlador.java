package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping
    public ModelAndView mostrarLibros() throws MiExcepcion, Exception {
        try {
            ModelAndView mav = new ModelAndView("libro");
            List<Libro> libros = libroServicio.obtenerLibros();
            mav.addObject("autores", libros);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
}
