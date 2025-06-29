package com.mishone.mishone.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AutoPingTask {

    private final RestTemplate restTemplate = new RestTemplate();

    // Cambia esta URL por la de tu API en Render (NO uses localhost)
    private final String pingUrl = "https://mishone-backend.onrender.com/api/ping";

    @Scheduled(fixedRate = 600000) // Ejecuta cada 10 minutos (600,000 ms)
    public void autoPing() {
        try {
            String response = restTemplate.getForObject(pingUrl, String.class);
            System.out.println("✅ Auto-ping OK: " + response);
        } catch (Exception e) {
            System.err.println("⚠️ Error en auto-ping: " + e.getMessage());
        }
    }
}
