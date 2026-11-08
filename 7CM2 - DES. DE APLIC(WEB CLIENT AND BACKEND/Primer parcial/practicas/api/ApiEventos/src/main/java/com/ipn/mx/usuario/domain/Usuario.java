package com.ipn.mx.usuario.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Usuario")

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name = "nombreDeUsuario",
            unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "claveDeUsuario",
            nullable = false, length = 200)
    private String password;

    @Column(name = "email",
            nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "habilitado",
            nullable = false, unique = true)
    private Boolean habilitado;

    @Column(name = "bloqueado",
            nullable = false, unique = true)
    private Boolean bloqueado;

    @ManyToMany
    @JoinTable(name = "UsuarioRol",
            joinColumns = @JoinColumn(name = "idUsuario",
                    referencedColumnName = "IdUsuario"),
                    inverseJoinColumns = @JoinColumn(name = "idRol",
                    referencedColumnName = "idRol")
    )
    private List<Rol> roles;


}
