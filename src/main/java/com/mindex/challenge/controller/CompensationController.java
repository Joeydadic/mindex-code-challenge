package com.mindex.challenge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    private final CompensationService compensationService;

    @Autowired
    public CompensationController(final CompensationService compensationService) {
        this.compensationService = compensationService;
    }

    @PostMapping("/compensation")
    public ResponseEntity<Compensation> createCompensation(@RequestBody Compensation compensation) {
        LOG.debug("Request received to create Compensation with the following details: [{}]", compensation);
        Compensation response = compensationService.create(compensation);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/compensation/{employeeId}")
    public ResponseEntity<Compensation> getCompensation(@PathVariable String employeeId) {
        LOG.debug("Request received to get Compensation for the following Employee: {}", employeeId);
        Compensation response = compensationService.read(employeeId);
        return ResponseEntity.ok(response);
    }
    
}
