package com.ipn.mx.usuario.dto;

import java.util.List;

public record UserSessionDTO(
        String username,
        List<String>roles,
        List<String>authorities
) {

}
