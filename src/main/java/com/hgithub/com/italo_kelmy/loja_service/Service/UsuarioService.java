package com.hgithub.com.italo_kelmy.loja_service.Service;

import com.hgithub.com.italo_kelmy.loja_service.Entity.Usuario;
import com.hgithub.com.italo_kelmy.loja_service.Repository.UsuarioRepository;
import com.hgithub.com.italo_kelmy.loja_service.Security.JwtUtil;
import com.hgithub.com.italo_kelmy.loja_service.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository repository;
    private final SecurityConfig securityConfig;
    private final JwtUtil jwtUtil;

    @Autowired
    public UsuarioService(UsuarioRepository repository, SecurityConfig securityConfig, JwtUtil jwtUtil) {
        this.repository = repository;
        this.securityConfig = securityConfig;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> cadastro(Usuario usuario) {
        if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado");
        } else if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Emáil já cadastrado");
        }

        usuario.setSenha(securityConfig.bCryptPasswordEncoder().encode(usuario.getSenha()));
        repository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }


    public ResponseEntity<?> login(Usuario usuario) throws Exception {

        securityConfig.manager(new AuthenticationConfiguration()).authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsuario(), usuario.getSenha())
        );

        Optional<Usuario> usuarioOptional = repository.findByUsuario(usuario.getUsuario());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não cadastrado");
        }

        Usuario usuario1 = usuarioOptional.get();

        UserDetails user = User.builder()
                .username(usuario1.getUsuario())
                .password(usuario1.getSenha())
                .roles("USER")
                .build();


        return ResponseEntity.ok(jwtUtil.generateKey(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = repository.findByUsuario(username);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        Usuario usuario1 = usuario.get();

        return User.builder()
                .username(usuario1.getUsuario())
                .password(usuario1.getSenha())
                .roles("USER")
                .build();
    }
}
