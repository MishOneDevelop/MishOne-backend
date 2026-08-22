package com.mishone.mishone.controller;

import com.mishone.mishone.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * El AutoPingTask golpea este endpoint cada 10 minutos para mantener despierta
 * la app en Render. Se aprovecha para tocar tambien la base: el plan gratuito
 * de Supabase pausa el proyecto tras ~1 semana sin actividad.
 */
@RestController
@RequestMapping("/api/ping")
@RequiredArgsConstructor
public class PingController {

    private final ProyectoRepository proyectoRepository;

    @GetMapping
    public String ping() {
        proyectoRepository.count();
        return "Pong";
    }
}
