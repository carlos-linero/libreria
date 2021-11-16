package ejeUno.libreriaSpring.Seguridad;

import ejeUno.libreriaSpring.Servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    auth.userDetailsService(usuarioServicio).passwordEncoder(encoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
    http
            .authorizeRequests()
            .antMatchers("/usuario/guardar-admin","/usuario/guardar-usuario","/rol/guardar","/css/*", "/img/*").permitAll()
            .antMatchers("/**").authenticated()  //authenticated() permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/logincheck")
            .usernameParameter("correo")
            .passwordParameter("clave")
            .defaultSuccessUrl("/inicio", true)
            .failureUrl("/login?error=true")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true")
            .permitAll()
            .deleteCookies("JSESSIONID")
            .and()
            .csrf()
            .disable();
    }
}
