package com.ruwan.csvtest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruwan.csvtest.services.CsvAnalyzerService;

@RestController
@RequestMapping("/api/csv")
public class CsvAnalyzerController {

    private final CsvAnalyzerService csvAnalyzerService;

    public CsvAnalyzerController(CsvAnalyzerService csvAnalyzerService) {
        this.csvAnalyzerService = csvAnalyzerService;
    }

    @GetMapping("/analyze")
    public ResponseEntity<String> analyzeCsv() {
        try {
            long count = csvAnalyzerService.countTaGreaterThan20();
            return ResponseEntity.ok("Rows where 'ta' > 20: " + count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
