package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.ClienteServicio;
import ejeUno.libreriaSpring.Servicio.PrestamoServicio;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

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
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PrestamoServicio prestamoServicio;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER')")
    public ModelAndView mostrarClientes(HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("cliente");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }

        List<Cliente> clientes = clienteServicio.obtenerClientexRol("CLIENTE");
        clientes.sort(Cliente.compararNombre);
        mav.addObject("clientes", clientes);
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam String telefono, RedirectAttributes attributes) throws Exception {
        try {
            clienteServicio.guardarCliente(nombre, apellido, documento, telefono);
            attributes.addFlashAttribute("exito-name", "El cliente ha sido registrado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/cliente");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam String telefono, @RequestParam String id, @RequestParam Boolean estado, RedirectAttributes attributes) throws Exception {
        try {
            clienteServicio.modificarCliente(nombre, apellido, documento, telefono, id, estado);
            attributes.addFlashAttribute("exito-name", "Los datos del cliente han sido actualizados exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/cliente");
    }

    @PostMapping("/modificar-estado")
    @PreAuthorize("hasRole('SUPER')")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id, RedirectAttributes attributes) throws Exception {
        try {
            clienteServicio.modificarCliente(id, estado, prestamoServicio.obtenerCantidadPrestamo(id));
            attributes.addFlashAttribute("exito-name", "El cliente ha sido " + ((estado) ? "deshabilitado" : "habilitado") + " exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/cliente");
    } 
    
}
