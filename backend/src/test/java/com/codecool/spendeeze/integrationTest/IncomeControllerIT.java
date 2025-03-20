package com.codecool.spendeeze.integrationTest;

import com.codecool.spendeeze.model.entity.Income;
import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.model.entity.MemberRole;
import com.codecool.spendeeze.repository.IncomeRepository;
import com.codecool.spendeeze.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class IncomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member testMember;
    private Income testIncome;
    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        testMember = new Member();
        testMember.setFirstName("John");
        testMember.setLastName("Doe");
        testMember.setCountry("Country");
        testMember.setEmail("john@gmail.com");
        testMember.setUsername("john");
        testMember.setPassword(passwordEncoder.encode("password"));
        testMember.setRoles(Set.of(MemberRole.ROLE_USER));
        memberRepository.save(testMember);

        testIncome = new Income();
        testIncome.setPublicId(UUID.randomUUID());
        testIncome.setAmount(1500.00);
        testIncome.setDate(LocalDate.of(2025, 3, 10));
        testIncome.setMember(testMember);
        incomeRepository.save(testIncome);

        String userCredentials = """
                {
                "username": "john",
                "password": "password"
                }
                """;
        jwtToken = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredentials))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .replaceAll(".*\"jwt\":\"([^\"]+)\".*", "$1");

        System.out.println("Generated JWT Token: " + jwtToken);

    }

    @AfterEach
    void cleanUp() {
        incomeRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @DisplayName("Integration test for IncomeController - addIncome()")
    @Test
    void givenIncomeDTO_whenAddIncome_thenReturnSavedIncome() throws Exception {
        String incomeJson = """
            {
                "amount": 2000.00,
                "date": "2025-04-15"
            }
        """;

        mockMvc.perform(post("/api/incomes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incomeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(2000.00))
                .andExpect(jsonPath("$.date").value("2025-04-15"));

        assertEquals(2, incomeRepository.count());
    }

    @DisplayName("Integration test for IncomeController - findIncomeById()")
    @Test
    void givenIncomeId_whenFindIncomeById_thenReturnIncomeDTO() throws Exception {
        mockMvc.perform(get("/api/incomes/" + testIncome.getPublicId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1500.00))
                .andExpect(jsonPath("$.date").value("2025-03-10"));
    }

    @DisplayName("Integration test for IncomeController - findIncomesByMember()")
    @Test
    void givenExistingIncomes_whenFindIncomesByMember_thenReturnIncomeList() throws Exception {
        mockMvc.perform(get("/api/incomes")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].amount").value(1500.00))
                .andExpect(jsonPath("$[0].date").value("2025-03-10"));
    }

    @DisplayName("Integration test for IncomeController - updateIncome()")
    @Test
    void givenUpdatedIncomeDTO_whenUpdateIncome_thenReturnUpdatedIncome() throws Exception {
        String updatedIncomeJson = """
        {
            "amount": 3000.00,
            "date": "2025-06-20"
        }
    """;

        mockMvc.perform(put("/api/incomes/" + testIncome.getPublicId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedIncomeJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(3000.00))
                .andExpect(jsonPath("$.date").value("2025-06-20"));
    }


    @DisplayName("Integration test for IncomeController - deleteIncome()")
    @Test
    void givenIncomeId_whenDeleteIncome_thenIncomeIsRemoved() throws Exception {
        mockMvc.perform(delete("/api/incomes/" + testIncome.getPublicId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        assertEquals(0, incomeRepository.count());
    }

}
