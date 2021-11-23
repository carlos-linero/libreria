package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.UsuarioRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService, ValidacionInterface {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public Usuario crearUsuario(Rol rol, String correo, String clave) throws Exception {
        try {
            validacionPresencia(rol, "Rol");
            validacionNombre(correo, "Correo");
            if (usuarioRepositorio.existsByCorreo(correo)) {
                throw new MiExcepcion("Ya existe una cuenta asociada al correo: " + correo);
            }
            validacionClave(clave);

            Usuario usuario = new Usuario();

            usuario.setEstado(true);
            usuario.setRol(rol);
            usuario.setCorreo(correo);
            usuario.setClave(encoder.encode(clave));
            usuarioRepositorio.save(usuario);
            return usuario;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public Usuario crearUsuario(String adminPass, Rol rol, String correo, String clave) throws Exception, MiExcepcion {
        try {
            validacionPresencia(rol, "Rol");
            validacionNombre(correo, "Correo");
            validacionNombre(clave, "Clave");

            if (usuarioRepositorio.existsByCorreo(correo)) {
                throw new MiExcepcion("Ya existe una cuenta asociada al correo: " + correo);
            }
            if (!adminPass.equalsIgnoreCase("admin123")) {
                throw new MiExcepcion("Autenticación de administrador invalida");
            }
            Usuario usuario = new Usuario();

            usuario.setEstado(true);
            usuario.setRol(rol);
            usuario.setCorreo(correo);
            usuario.setClave(encoder.encode(clave));
            usuarioRepositorio.save(usuario);
            return usuario;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Usuario obteneUsuario(String id) throws Exception, MiExcepcion {
        try {
            //return autorRepositorio.obtenerAutores(true);
            Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(() -> new MiExcepcion("Error al extraer informacion del usuario"));
            return usuario;
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("Corre no se encuentra asociado a una cuenta"));

            if (usuario.getEstado() == false) {
                throw new UsernameNotFoundException("La cuenta no se encuentra habilitada");
            }

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession sesion = attr.getRequest().getSession(true);

            sesion.setAttribute("idUsuario", usuario.getId());

            return new User(usuario.getCorreo(), usuario.getClave(), Collections.singletonList(authority));
        } catch (UsernameNotFoundException es) {
            throw es;
        }
    }
}
