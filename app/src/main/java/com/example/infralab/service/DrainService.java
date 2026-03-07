package com.example.infralab.service;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

@Service
public class DrainService {

    private final AtomicBoolean draining = new AtomicBoolean(false);

    public boolean isDraining() {
        return draining.get();
    }

    public void enableDraining() {
        draining.set(true);
    }

    public void disableDraining() {
        draining.set(false);
    }
}