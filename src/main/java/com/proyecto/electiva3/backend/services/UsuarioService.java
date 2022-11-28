package com.proyecto.electiva3.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("admin")) throw new UsernameNotFoundException("Username incorrecto");
        return new User("admin", passwordEncoder.encode("12345"), mapearAutoridadesRoles("conectarse"));
    }
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(String permiso) {
        Set<SimpleGrantedAuthority> permisos = new HashSet<>();
        permisos.add(new SimpleGrantedAuthority(permiso));
        return permisos;
    }

}
