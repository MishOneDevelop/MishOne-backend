package com.mishone.mishone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio", updatable = false, nullable = false)
    private Integer idServicio;

    @Column(nullable = false, unique = true, length = 255)
    private String nombre;

    @Column(name = "descripcion_min", nullable = false, length = 1000)
    private String descripcionMin;

    @Column(name = "descripcion_max", nullable = false, length = 45)
    private String descripcionMax;

    @Column(name = "precio_min", nullable = false)
    private Double precioMin;

    @Column(name = "precio_max", nullable = false)
    private Double precioMax;

    @ManyToOne
    @JoinColumn(name = "id_categoria_servicio", nullable = false)
    private ValorParametro categoria;

    @Column(name = "duracion_estimada", nullable = false)
    private Integer duracionEstimada;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private LocalDateTime fechaModificacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}

