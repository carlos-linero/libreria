package ejeUno.libreriaSpring.Servicio;

import ejeUno.libreriaSpring.Entidad.Rol;
import ejeUno.libreriaSpring.Entidad.Usuario;
import ejeUno.libreriaSpring.Repositorio.UsuarioRepositorio;
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
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Transactional
    public void crearUsuario(Rol rol, String correo, String clave, String claveValida) throws Exception{
        try {
            Usuario usuario = new Usuario();
            
            usuario.setRol(rol);
            usuario.setCorreo(correo);
            usuario.setClave(encoder.encode(clave));
            usuarioRepositorio.save(usuario);
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
