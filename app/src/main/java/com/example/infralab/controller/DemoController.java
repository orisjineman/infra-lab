package com.example.infralab.controller;

import com.example.infralab.service.DrainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class DemoController {

    private final DrainService drainService;

    @Value("${APP_NAME:unknown}")
    private String appName;

    public DemoController(DrainService drainService) {
        this.drainService = drainService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        if (drainService.isDraining()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(body("DRAINING", "점검 모드라 일반 트래픽을 받지 않음"));
        }

        return ResponseEntity.ok(body("UP", "정상 응답"));
    }

    @GetMapping("/health/check")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        if (drainService.isDraining()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(body("DOWN", "health check 실패 상태"));
        }

        return ResponseEntity.ok(body("UP", "health check 성공 상태"));
    }

    @PostMapping("/admin/drain/on")
    public Map<String, Object> drainOn() {
        drainService.enableDraining();
        return body("DRAINING", "drain mode enabled");
    }

    @PostMapping("/admin/drain/off")
    public Map<String, Object> drainOff() {
        drainService.disableDraining();
        return body("UP", "drain mode disabled");
    }

    @GetMapping("/admin/status")
    public Map<String, Object> status() {
        return body(drainService.isDraining() ? "DRAINING" : "UP", "current app status");
    }

    private Map<String, Object> body(String status, String message) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("appName", appName);
        result.put("status", status);
        result.put("message", message);
        result.put("time", LocalDateTime.now().toString());
        return result;
    }
}