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
@Table(name = "portafolio")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto", updatable = false, nullable = false)
    private Integer idProyecto;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(length = 255)
    private String urlImagen;

    @Column(length = 255)
    private String linkDemo;

    @Column(nullable = false)
    private LocalDateTime fechaPublicacion;

    @Column(nullable = false)
    private Boolean visible = true;

    @Column(nullable = false)
    private Integer prioridad = 0;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio; // Relación con la tabla `Servicios`


    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaPublicacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
