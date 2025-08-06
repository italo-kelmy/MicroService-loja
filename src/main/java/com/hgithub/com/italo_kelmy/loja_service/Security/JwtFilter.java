package com.hgithub.com.italo_kelmy.loja_service.Security;

import com.hgithub.com.italo_kelmy.loja_service.Repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UsuarioRepository repository;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UsuarioRepository repository) {
        this.jwtUtil = jwtUtil;
        this.repository = repository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .ifPresent(token -> {

                    String usuario = jwtUtil.extrairClaims(token, Claims::getSubject);

                    if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        repository.findByUsuario(usuario).ifPresent(usuario1 -> {
                            UserDetails userDetails = User.builder()
                                    .username(usuario1.getUsuario())
                                    .password(usuario1.getSenha())
                                    .roles("USER")
                                    .build();

                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            authenticationToken.setDetails(
                                    new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                        });
                    }

                });
        filterChain.doFilter(request, response);
    }
}
