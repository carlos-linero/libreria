package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Servicio.ClienteServicio;
import ejeUno.libreriaSpring.Servicio.RolServicio;
import ejeUno.libreriaSpring.Servicio.UsuarioServicio;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RolServicio rolServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @PostMapping("/guardar-usuario")
    public RedirectView guardar(Principal principal, @RequestParam String correo, @RequestParam String clave, RedirectAttributes attributes) throws Exception {
        try {
            if (principal != null) {
                return new RedirectView("/login");
            }
            clienteServicio.guardarCliente(usuarioServicio.crearUsuario(rolServicio.obtenerRol("CLIENTE"), correo, clave));
            attributes.addFlashAttribute("exito-name", "Usuario registrado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/login");
    }

    @PostMapping("/guardar-admin")
    public RedirectView guardar(Principal principal, @RequestParam String adminPass, @RequestParam String correo, @RequestParam String clave, RedirectAttributes attributes) throws Exception {
        try {
            if (principal != null) {
                return new RedirectView("/login");
            }
            clienteServicio.guardarCliente(usuarioServicio.crearUsuario(adminPass, rolServicio.obtenerRol("ADMIN"), correo, clave));
            attributes.addFlashAttribute("exito-name", "Administrador registrado exitosamente");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/login");
    }
}
