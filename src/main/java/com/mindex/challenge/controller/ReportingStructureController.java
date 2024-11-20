package com.mindex.challenge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    private final ReportingStructureService reportingStructureService;

    @Autowired
    public ReportingStructureController(final ReportingStructureService reportingStructureService) {
        this.reportingStructureService = reportingStructureService;
    }

    @GetMapping("/reporting/{employeeId}")
    public ResponseEntity<ReportingStructure> getReportingStructure(@PathVariable String employeeId) {

        LOG.debug("Retrieved request to receive Reporting Structure for employee: {}", employeeId);
        ReportingStructure response = reportingStructureService.create(employeeId);

        return ResponseEntity.ok(response);
    }
    
}
