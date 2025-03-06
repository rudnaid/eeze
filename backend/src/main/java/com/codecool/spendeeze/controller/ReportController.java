package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.ReportDTO;
import com.codecool.spendeeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.spendeeze.model.dto.reports.CategoryReport;
import com.codecool.spendeeze.model.dto.reports.MonthlyIncomeExpenseReportDTO;
import com.codecool.spendeeze.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ReportDTO getSummaryReport() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberUsername = authentication.getName();
        return reportService.generateReport(memberUsername);
    }

    @GetMapping("/by-category")
    public List<TotalExpenseByTransactionCategoryDTO> getExpensesByCategory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberUsername = authentication.getName();
        return reportService.getTotalExpenseByTransactionCategory(memberUsername);
    }

    @GetMapping("/monthly")
    public List<CategoryReport> getMonthlyReport(
            @RequestParam int month,
            @RequestParam int year,
            Principal principal) {

        return reportService.getMonthlyReport(principal.getName(), month, year);
    }

    @GetMapping("/yearly")
    public List<MonthlyIncomeExpenseReportDTO> getMonthlyIncomeExpenseReport(
            @RequestParam int year,
            Principal principal) {

        return reportService.getYearlyReport(principal.getName(), year);
    }
}
