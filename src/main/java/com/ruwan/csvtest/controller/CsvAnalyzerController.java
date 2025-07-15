package com.ruwan.csvtest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> analyzeCsv() {
        try {
            List<Long> count = csvAnalyzerService.countTaGreaterThan20();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("count", count));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
