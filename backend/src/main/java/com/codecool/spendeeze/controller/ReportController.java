package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{memberPublicId}")
    public ReportDTO getSummaryReport(@PathVariable UUID memberPublicId) {
        return reportService.generateReport(memberPublicId);
    }

    @GetMapping("/by-category/{memberPublicId}")
    public List<TotalExpenseByTransactionCategoryDTO> getExpensesByCategory(@PathVariable UUID memberPublicId) {
        return reportService.getTotalExpenseByTransactionCategory(memberPublicId);
    }
}
