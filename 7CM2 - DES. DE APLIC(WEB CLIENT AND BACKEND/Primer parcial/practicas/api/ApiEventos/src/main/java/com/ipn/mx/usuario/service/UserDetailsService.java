package com.ipn.mx.usuario.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    @Transactional(readOnly = true)
    UserDetails loadByUsername(String username)
        throws UsernameNotFoundException
}
