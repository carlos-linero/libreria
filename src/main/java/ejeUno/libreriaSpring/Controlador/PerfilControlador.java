package ejeUno.libreriaSpring.Controlador;

import ejeUno.libreriaSpring.Entidad.Cliente;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Servicio.ClienteServicio;
import ejeUno.libreriaSpring.Servicio.UsuarioServicio;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/perfil")
public class PerfilControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private UsuarioServicio UsuarioServicio;

    @GetMapping
    public ModelAndView mostrarAutores(HttpSession sesion, HttpServletRequest request) throws MiExcepcion, Exception {

        ModelAndView mav = new ModelAndView("perfil");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        //Usuario usuario = UsuarioServicio.obteneUsuario((String) sesion.getAttribute("idUsuario"));
   
        Cliente cliente = clienteServicio.obtenerCliente("25b74573-77a5-4a33-b304-c7ac8d8a4e6d");
        mav.addObject("cliente", cliente);
        return mav;
    }
}
