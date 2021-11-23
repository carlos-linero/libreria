package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.ClienteServicio;
import ejeUno.libreriaSpring.Servicio.PrestamoServicio;
import ejeUno.libreriaSpring.Servicio.UsuarioServicio;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/perfil")
public class PerfilControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PrestamoServicio prestamoServicio;

    @GetMapping
    public ModelAndView mostrarAutores(HttpSession sesion, HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("perfil");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }

        mav.addObject("cliente", clienteServicio.obtenerPerfil((String) sesion.getAttribute("idUsuario")));
        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView guardar(@RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam String telefono, @RequestParam String id, @RequestParam Boolean estado, RedirectAttributes attributes) throws Exception {
        try {
            clienteServicio.modificarCliente(nombre, apellido, documento, telefono, id, estado);
            attributes.addFlashAttribute("exito-name", "Los datos del cliente han sido actualizados exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }

    @PostMapping("/modificar-estado")
    public RedirectView guardar(@RequestParam Boolean estado, @RequestParam String id, RedirectAttributes attributes) throws Exception {
        try {
            clienteServicio.modificarCliente(id, estado, prestamoServicio.obtenerCantidadPrestamo(id));
            //attributes.addFlashAttribute("exito-name", "El cliente ha sido " + ((estado) ? "deshabilitado" : "habilitado") + " exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/perfil");
    }
}
