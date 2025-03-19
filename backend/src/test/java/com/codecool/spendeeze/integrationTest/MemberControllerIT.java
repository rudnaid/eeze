package com.codecool.spendeeze.integrationTest;

import com.codecool.spendeeze.model.entity.Member;
import com.codecool.spendeeze.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String userCredentials = """
            {
                "firstName": "Jon",
                "lastName": "Doe",
                "country": "country",
                "email": "email@email.com",
                "username": "jon",
                "password": "password"
            }
            """;

    String loginCredentials = """
            {
                "username": "jon",
                "password": "password"
            }
            """;


    @AfterEach
    public void cleanUp(){
        memberRepository.deleteAll();
    }

    @Test
    public void testRegistration() throws Exception {
        mockMvc.perform(post("/api/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jon"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.country").value("country"))
                .andExpect(jsonPath("$.email").value("email@email.com"));

        assertTrue(memberRepository.findMemberByUsername("jon").isPresent());
        assertEquals(1, memberRepository.count());
    }

    @Test
    public void testLogin_ReturnJwtToken() throws Exception {
        Member member = new Member();
        member.setFirstName("Jon");
        member.setLastName("Doe");
        member.setCountry("country");
        member.setEmail("email@email.com");
        member.setUsername("jon");
        member.setPassword(passwordEncoder.encode("password"));
        memberRepository.save(member);

        mockMvc.perform(post("/api/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(loginCredentials))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists())
                .andExpect(jsonPath("$.username").value("jon"));
    }
}
