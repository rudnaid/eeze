package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.repository.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{memberPublicId}")
    public ReportDTO getReport(@PathVariable UUID memberPublicId) {
        return reportService.generateReport(memberPublicId);
    }
}
