package com.codecool.eeze.controller;

import com.codecool.eeze.model.dto.ReportDTO;
import com.codecool.eeze.model.dto.TotalExpenseByTransactionCategoryDTO;
import com.codecool.eeze.model.dto.reports.CategoryReport;
import com.codecool.eeze.model.dto.reports.MonthlyIncomeExpenseReportDTO;
import com.codecool.eeze.service.ReportService;
import com.codecool.eeze.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*")
public class ReportController {
    private final ReportService reportService;
    private final AuthUtil authUtil;

    @Autowired
    public ReportController(ReportService reportService, AuthUtil authUtil) {
        this.reportService = reportService;
        this.authUtil = authUtil;
    }

    @GetMapping
    public ReportDTO getSummaryReport() {
        String memberUsername = authUtil.getUsername();
        return reportService.generateReport(memberUsername);
    }

    @GetMapping("/by-category")
    public List<TotalExpenseByTransactionCategoryDTO> getExpensesByCategory() {
        String memberUsername = authUtil.getUsername();
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
