package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Libro;
import ejeUno.libreriaSpring.Entidad.Prestamo;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.ClienteServicio;
import ejeUno.libreriaSpring.Servicio.LibroServicio;
import ejeUno.libreriaSpring.Servicio.PrestamoServicio;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/prestamo")
public class PrestamoControlador {

    @Autowired
    private PrestamoServicio prestamoServicio;
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private LibroServicio libroServicio;

    @GetMapping
    public ModelAndView mostrarPrestamos(HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("prestamo");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        List<Prestamo> prestamos = prestamoServicio.obtenerPrestamo(true);
        prestamos.sort(Prestamo.compararNombre);
        mav.addObject("prestamos", prestamos);
        mav.addObject("libros", libroServicio.obtenerLibro());
        mav.addObject("clientes", clienteServicio.obtenerCliente());

        return mav;

    }

    @GetMapping("/historial/{estado}/{id}/{name}/{lastn}")
    public ModelAndView historialPrestamo(@PathVariable Boolean estado, @PathVariable String id, @PathVariable String name, @PathVariable String lastn) throws MiExcepcion, Exception {

        try {
            ModelAndView mav = new ModelAndView("historial-prestamo");
            List<Prestamo> prestamos = prestamoServicio.obtenerPrestamo(estado, id);
            prestamos.sort(Prestamo.compararNombre);
            mav.addObject("name", name);
            mav.addObject("lastn", lastn);
            mav.addObject("estado", estado);
            mav.addObject("prestamos", prestamos);
            return mav;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam LocalDate fechaDevolucion, @RequestParam Integer cantidad, @RequestParam String cliente, @RequestParam String libro, RedirectAttributes attributes) throws Exception {
        try {
            Cliente clienteAux = clienteServicio.obtenerCliente(cliente);
            Libro libroAux = libroServicio.obtenerLibro(libro);
            prestamoServicio.guardarTransaccion(fechaDevolucion, cantidad, clienteAux, libroAux);
            attributes.addFlashAttribute("exito-name", "El prestamo ha sido registrado exitosamente");
        } catch (Exception e) {

            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/prestamo");
    }

    @PostMapping("/devolucion")
    public RedirectView guardar(@RequestParam String prestamo, @RequestParam Integer cantidad, @RequestParam String cliente, @RequestParam String libro, RedirectAttributes attributes) throws Exception {
        try {
            Cliente clienteAux = clienteServicio.obtenerCliente(cliente);
            Libro libroAux = libroServicio.obtenerLibro(libro);
            Prestamo prestamoAux = prestamoServicio.obtenerPrestamo(prestamo);
            prestamoServicio.guardarTransaccion(prestamoAux, cantidad, clienteAux, libroAux);
            attributes.addFlashAttribute("exito-name", "La devolución fue realizada exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/prestamo");
    }
}
