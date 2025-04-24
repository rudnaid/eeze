package com.codecool.eeze.service;

import com.codecool.eeze.model.dto.TransactionCategoryDTO;
import com.codecool.eeze.model.entity.TransactionCategory;
import com.codecool.eeze.repository.TransactionCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionCategoryServiceTest {

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;

    @InjectMocks
    private TransactionCategoryService transactionCategoryService;

    private TransactionCategory category1;
    private TransactionCategory category2;

    @BeforeEach
    void setUp() {
        category1 = new TransactionCategory();
        category1.setId(1L);
        category1.setName("Food");

        category2 = new TransactionCategory();
        category2.setId(2L);
        category2.setName("Transport");
    }

    @DisplayName("JUnit test for TransactionCategoryService - getAllTransactionCategoryDTOs()")
    @Test
    void givenTransactionCategories_whenGetAllTransactionCategoryDTOs_thenReturnDTOList() {
        // GIVEN
        given(transactionCategoryRepository.findAll()).willReturn(List.of(category1, category2));

        // WHEN
        List<TransactionCategoryDTO> categoryDTOs = transactionCategoryService.getAllTransactionCategoryDTOs();

        // THEN
        List<TransactionCategoryDTO> expectedCategoryDTOs = List.of(
                new TransactionCategoryDTO("Food"),
                new TransactionCategoryDTO("Transport")
        );

        assertThat(categoryDTOs).isNotEmpty();
        assertThat(categoryDTOs.size()).isEqualTo(2);
        assertIterableEquals(expectedCategoryDTOs, categoryDTOs);

        // Verify output
        verify(transactionCategoryRepository, times(1)).findAll();
    }

    @DisplayName("JUnit test for TransactionCategoryService - getAllTransactionCategoryDTOs() should return empty list")
    @Test
    void givenNoTransactionCategories_whenGetAllTransactionCategoryDTOs_thenReturnEmptyList() {
        // GIVEN
        given(transactionCategoryRepository.findAll()).willReturn(List.of());

        // WHEN
        List<TransactionCategoryDTO> categoryDTOs = transactionCategoryService.getAllTransactionCategoryDTOs();

        // THEN
        assertThat(categoryDTOs).isEmpty();
        assertIterableEquals(List.of(), categoryDTOs);

        // Verify output
        verify(transactionCategoryRepository, times(1)).findAll();
    }
}
