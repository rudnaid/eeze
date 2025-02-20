//package com.codecool.spendeeze.util;
//
//import com.codecool.spendeeze.model.entity.Expense;
//import com.codecool.spendeeze.model.entity.Income;
//import com.codecool.spendeeze.model.entity.Member;
//import com.codecool.spendeeze.model.entity.TransactionCategory;
//import com.codecool.spendeeze.repository.ExpenseRepository;
//import com.codecool.spendeeze.repository.IncomeRepository;
//import com.codecool.spendeeze.repository.MemberRepository;
//import com.codecool.spendeeze.repository.TransactionCategoryRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class DummyDataGenerator {
//
//    private final MemberRepository memberRepository;
//    private final IncomeRepository incomeRepository;
//    private final ExpenseRepository expenseRepository;
//    private final TransactionCategoryRepository transactionCategoryRepository;
//
//    private Random random = new Random();
//
//    @Autowired
//    public DummyDataGenerator(MemberRepository memberRepository, IncomeRepository incomeRepository, ExpenseRepository expenseRepository, TransactionCategoryRepository transactionCategoryRepository) {
//        this.memberRepository = memberRepository;
//        this.incomeRepository = incomeRepository;
//        this.expenseRepository = expenseRepository;
//        this.transactionCategoryRepository = transactionCategoryRepository;
//    }
//
//    String[] firstNames = {"John", "Jane", "Bob", "Mary", "Vica", "Erika", "David"};
//    String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
//            "Rodriguez", "Martinez", "Hernandez", "Anderson",};
//
//    @PostConstruct
//    public void generateDummyData() {
//        if (memberRepository.count() == 0) {
//            for (int i = 0; i < 10; i++) {
//                Member member = createMember();
//                memberRepository.save(member);
//
//                generateIncome(member, 5, 2001, 1000);
//                generateExpenses(member, 5);
//            }
//        }
//    }
//
//    private Member createMember() {
//        Member member = new Member();
//        member.setFirstName(firstNames[random.nextInt(firstNames.length)]);
//        member.setLastName(lastNames[random.nextInt(lastNames.length)]);
//        member.setCountry("Hungary");
//        member.setEmail(member.getFirstName() + member.getLastName() + random.nextInt(50) + "@example.com");
//        member.setPassword("password");
//        return member;
//    }
//
//
//    private void generateIncome(Member member, int months, int incomeUpperBound, int minimumIncome) {
//        LocalDate startDate = LocalDate.now().minusMonths(months);
//
//        for (int j = 0; j < months + 1; j++) {
//            LocalDate incomeDate = startDate.plusMonths(j);
//            try {
//                Income income = new Income();
//                income.setMember(member);
//                income.setAmount(random.nextInt(incomeUpperBound) + minimumIncome);
//                income.setDate(incomeDate);
//                incomeRepository.save(income);
//            } catch (Exception e) {
//                System.out.println("Error generating incomes: " + e.getMessage());
//            }
//        }
//    }
//
//    private void generateExpenses(Member member, int months) {
//        LocalDate startDate = LocalDate.now().minusMonths(months);
//
//        for (int j = 0; j < months + 1; j++) {
//            LocalDate currentDate = startDate.plusMonths(j);
//            int numExpenses = random.nextInt(20) + 10;
//
//            for (int k = 0; k < numExpenses; k++) {
//                try {
//                    Expense expense = new Expense();
//                    expense.setMember(member);
//                    expense.setAmount(random.nextDouble() * 100 + 10);
//                    expense.setTransactionDate(currentDate.plusDays(random.nextInt(28)));
//                    expense.setTransactionCategory(getRandomTransactionCategory());
//                    expenseRepository.save(expense);
//                } catch (Exception e) {
//                    System.out.println("Error generating expenses: " + e.getMessage());
//                }
//            }
//        }
//    }
//
//    private TransactionCategory getRandomTransactionCategory() {
//        List<TransactionCategory> allTransactionCategories = transactionCategoryRepository.findAll();
//        return allTransactionCategories.get(random.nextInt(allTransactionCategories.size()));
//    }
//}