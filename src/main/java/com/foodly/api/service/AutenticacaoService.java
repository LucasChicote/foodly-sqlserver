package com.foodly.api.service;

import com.foodly.api.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails usuario = repository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
        }
        return usuario;
    }
}
