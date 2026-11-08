package com.ipn.mx.usuario.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService{



    @Transactional(readOnly = true)
    @Override
    public UserDetails loadByUsername(String username)
        throws UsernameNotFoundException

}
