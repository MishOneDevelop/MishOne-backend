package com.mishone.mishone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Builder // Permite construir objetos usando el patrón Builder
@Entity
@Table(name = "valores_parametros")
public class ValorParametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor_parametro", updatable = false, nullable = false)
    private Integer idValorParametro;

    @ManyToOne
    @JoinColumn(name = "id_parametro", nullable = false)
    private Parametro parametro; // Relación con la tabla `Parametros`

    @Column(nullable = false, length = 255)
    private String valor;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private Integer prioridad = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
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