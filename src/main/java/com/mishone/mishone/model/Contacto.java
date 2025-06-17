package com.mishone.mishone.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contactos")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContacto;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 2000)
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "estado", nullable = false)
    private ValorParametro estado; // Relación con `Valores_Parametros` para manejar los estados del contacto

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

}
