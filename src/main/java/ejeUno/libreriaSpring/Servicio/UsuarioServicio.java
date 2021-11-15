package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import ejeUno.libreriaSpring.Repositorio.UsuarioRepositorio;
import ejeUno.libreriaSpring.Validacion.ValidacionInterface;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            } else if (!adminPass.equalsIgnoreCase("admin123")) {
                throw new MiExcepcion("Autenticacion de administrador invalida");
            }
            Usuario usuario = new Usuario();

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

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("Usuario no registrado"));

        return new User(usuario.getCorreo(), usuario.getClave(), Collections.emptyList());
    }
}
