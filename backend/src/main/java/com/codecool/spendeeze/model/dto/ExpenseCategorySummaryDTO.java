package com.codecool.spendeeze.model.dto;

public class ExpenseCategorySummaryDTO {
    private long categoryPublicId;
    private String categoryName;
    private double totalByCategory;

    public ExpenseCategorySummaryDTO() {
    }

    public ExpenseCategorySummaryDTO(long categoryPublicId, String categoryName, double totalByCategory) {
        this.categoryPublicId = categoryPublicId;
        this.categoryName = categoryName;
        this.totalByCategory = totalByCategory;
    }

    public long getCategoryPublicId() {
        return categoryPublicId;
    }

    public void setCategoryPublicId(long categoryPublicId) {
        this.categoryPublicId = categoryPublicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getTotalByCategory() {
        return totalByCategory;
    }

    public void setTotalByCategory(double totalByCategory) {
        this.totalByCategory = totalByCategory;
    }
}
